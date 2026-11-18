package utn.donatrack.donatrack_frontend.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EntidadResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.RankingEntidadResponse;
import utn.donatrack.donatrack_frontend.viewmodel.CoincidenciaAsignacion;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

/** Rankings que proponen los algoritmos de asignacion para cada donacion segmentada. */
@Controller
@RequestMapping("/asignaciones")
public class AsignacionesController {

    private static final String SEMANTICA = "COMPATIBILIDAD_SEMANTICA";
    private static final String SUB_ATENDIDOS = "PRIORIDAD_SUB_ATENDIDOS";

    private final DonacionesClient client;

    public AsignacionesController(DonacionesClient client) {
        this.client = client;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("rankings", Seccion.cargar(client::listarRankings));
        model.addAttribute("nombres", nombresDeEntidades());
        return "asignaciones/lista";
    }

    /** Propuestas de ambos algoritmos para una donacion; destaca las entidades que aparecen en los dos. */
    @GetMapping("/{idSegmentada}")
    public String detalle(@PathVariable("idSegmentada") long idSegmentada, Model model) {
        List<RankingEntidadResponse> propuestas = List.of();
        try {
            propuestas = client.listarRankings().stream()
                .filter(s -> s.idSegmentada() != null && s.idSegmentada() == idSegmentada)
                .flatMap(s -> s.ranking().stream())
                .toList();
        } catch (ServicioExternoException e) {
            model.addAttribute("errorServicio", e.getMessage());
        }
        List<RankingEntidadResponse> semantica = delAlgoritmo(propuestas, SEMANTICA);
        List<RankingEntidadResponse> prioridad = delAlgoritmo(propuestas, SUB_ATENDIDOS);
        List<CoincidenciaAsignacion> coincidencias = semantica.stream()
            .flatMap(s -> prioridad.stream()
                .filter(p -> p.idEntidad() != null && p.idEntidad().equals(s.idEntidad()))
                .map(p -> new CoincidenciaAsignacion(s.idEntidad(), s.posicion(), p.posicion())))
            .toList();

        model.addAttribute("idSegmentada", idSegmentada);
        model.addAttribute("semantica", semantica);
        model.addAttribute("prioridad", prioridad);
        model.addAttribute("coincidencias", coincidencias);
        model.addAttribute("nombres", nombresDeEntidades());
        return "asignaciones/detalle";
    }

    /** Ejecuta los algoritmos a demanda. Post/Redirect/Get: recargar la pagina no vuelve a ejecutarlos. */
    @PostMapping("/ejecutar")
    public String ejecutar(RedirectAttributes flash) {
        try {
            String resultado = client.generarRankings();
            flash.addFlashAttribute("exito",
                resultado == null || resultado.isBlank() ? "Algoritmos ejecutados." : resultado);
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/asignaciones";
    }

    /** La persona administradora fija la entidad destino de la donacion. */
    @PostMapping("/{idSegmentada}/confirmar")
    public String confirmar(@PathVariable("idSegmentada") long idSegmentada, @RequestParam("idEntidad") long idEntidad,
                            RedirectAttributes flash) {
        try {
            client.confirmarAsignacion(idSegmentada, idEntidad);
            flash.addFlashAttribute("exito", "Donación asignada. Avisamos a la entidad y a la persona donante.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/asignaciones/" + idSegmentada;
        }
        return "redirect:/asignaciones";
    }

    private static List<RankingEntidadResponse> delAlgoritmo(List<RankingEntidadResponse> todas, String algoritmo) {
        return todas.stream()
            .filter(r -> algoritmo.equals(r.algoritmoUtilizado()))
            .sorted(Comparator.comparing(RankingEntidadResponse::posicion, Comparator.nullsLast(Comparator.naturalOrder())))
            .toList();
    }

    /** id -> razon social, para mostrar nombres en vez de numeros. Si falla, las vistas caen en "Entidad N.o id". */
    private Map<Long, String> nombresDeEntidades() {
        try {
            return client.listarEntidades().stream()
                .filter(e -> e.id() != null && e.razonSocial() != null)
                .collect(Collectors.toMap(EntidadResponse::id, EntidadResponse::razonSocial, (a, b) -> a));
        } catch (ServicioExternoException e) {
            return Map.of();
        }
    }
}
