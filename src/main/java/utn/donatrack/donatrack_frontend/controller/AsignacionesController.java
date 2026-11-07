package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

/** Rankings que proponen los algoritmos de asignacion para cada donacion segmentada. */
@Controller
@RequestMapping("/asignaciones")
public class AsignacionesController {

    private final DonacionesClient client;

    public AsignacionesController(DonacionesClient client) {
        this.client = client;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("rankings", Seccion.cargar(client::listarRankings));
        return "asignaciones/lista";
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
}
