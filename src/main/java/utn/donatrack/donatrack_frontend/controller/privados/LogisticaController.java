package utn.donatrack.donatrack_frontend.controller.privados;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utn.donatrack.donatrack_frontend.client.LogisticaClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.logistica.CamionResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.DonacionPendienteResponse;
import utn.donatrack.donatrack_frontend.viewmodel.CamionForm;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

/** Logistica: cada bloque se carga por separado, asi que si uno falla los demas se ven. */
@Controller
@RequestMapping("/logistica")
public class LogisticaController {

    private final LogisticaClient client;

    public LogisticaController(LogisticaClient client) {
        this.client = client;
    }

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("camiones", Seccion.cargar(client::listarCamiones));
        model.addAttribute("bultos", Seccion.cargar(client::listarBultosPendientes));
        model.addAttribute("donaciones", Seccion.cargar(client::listarDonacionesPendientes));
        return "logistica/panel";
    }

    @GetMapping("/camiones")
    public String camiones(Model model) {
        model.addAttribute("camiones", Seccion.cargar(client::listarCamiones));
        return "logistica/camiones";
    }

    @GetMapping("/camiones/nuevo")
    public String nuevoCamion() {
        return "logistica/camion-form";
    }

    @PostMapping("/camiones/nuevo")
    public String crearCamion(@ModelAttribute CamionForm form, RedirectAttributes flash) {
        String error = form.validar();
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return "redirect:/logistica/camiones/nuevo";
        }
        try {
            client.crearCamion(form.toRequest());
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/logistica/camiones/nuevo";
        }
        flash.addFlashAttribute("exito", "Camión registrado.");
        return "redirect:/logistica/camiones";
    }

    @PostMapping("/camiones/{id}/editar")
    public String actualizarCamion(@PathVariable("id") String id, @ModelAttribute CamionForm form, RedirectAttributes flash) {
        String error = form.validar();
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return "redirect:/logistica/camiones/" + id + "/editar";
        }
        try {
            client.actualizarCamion(id, form.toRequest());
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/logistica/camiones/" + id + "/editar";
        }
        flash.addFlashAttribute("exito", "Camión actualizado.");
        return "redirect:/logistica/camiones";
    }

    @PostMapping("/camiones/{id}/baja")
    public String darDeBaja(@PathVariable("id") String id, RedirectAttributes flash) {
        try {
            client.darDeBajaCamion(id);
            flash.addFlashAttribute("exito", "Camión dado de baja.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/logistica/camiones";
    }

    @GetMapping("/camiones/{id}/editar")
    public String editarCamion(@PathVariable("id") String id, Model model) {
        try {
            client.listarCamiones().stream()
                .filter(c -> id.equals(c.id()))
                .findFirst()
                .ifPresent(c -> model.addAttribute("camion", c));
        } catch (ServicioExternoException e) {
            model.addAttribute("errorServicio", e.getMessage());
        }
        return "logistica/camion-form";
    }

    /** Mapa de camiones: lo usan donantes y entidades para seguir sus entregas, y la administracion. */
    @GetMapping("/seguimiento")
    public String seguimiento(Model model) {
        model.addAttribute("camiones", Seccion.cargar(client::listarCamiones));
        return "logistica/seguimiento";
    }

    /** JSON para que js/mapa.js mueva los marcadores sin recargar la pagina. */
    @GetMapping("/seguimiento/ubicaciones")
    @ResponseBody
    public List<CamionResponse> ubicaciones() {
        try {
            return client.listarCamiones();
        } catch (ServicioExternoException e) {
            return List.of();
        }
    }

    /** Vista del chofer. El servicio aun no expone la ruta por camion: se listan las entregas pendientes. */
    @GetMapping("/ruta")
    public String ruta(Model model) {
        Seccion<CamionResponse> camiones = Seccion.cargar(client::listarCamiones);
        model.addAttribute("camiones", new Seccion<>(
            camiones.datos().stream().filter(c -> Boolean.TRUE.equals(c.disponible())).toList(), camiones.error()));
        model.addAttribute("paradas", Seccion.cargar(client::listarDonacionesPendientes));
        return "logistica/ruta";
    }

    /** El chofer da por iniciado su recorrido: las entregas pasan a "En traslado" y se avisa a entidades y donantes. */
    @PostMapping("/ruta/iniciar")
    public String iniciarRuta(@RequestParam("idCamion") String idCamion, RedirectAttributes flash) {
        try {
            client.iniciarRuta(idCamion);
            flash.addFlashAttribute("exito", "Ruta iniciada. Las entregas pasaron a \"En traslado\".");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/logistica/ruta";
    }

    @GetMapping("/entregas-fallidas")
    public String entregasFallidas(Model model) {
        Seccion<DonacionPendienteResponse> todas = Seccion.cargar(client::listarDonacionesPendientes);
        Predicate<DonacionPendienteResponse> noRecibida = d -> "NO_RECIBIDA".equals(d.estadoDonacion());
        model.addAttribute("fallidas", new Seccion<>(todas.datos().stream().filter(noRecibida).toList(), todas.error()));
        return "logistica/entregas-fallidas";
    }

    /** Resuelve un caso no recibido: la donacion vuelve al deposito (entrega "Pendiente") y queda el motivo registrado. */
    @PostMapping("/entregas-fallidas/{id}/devolver")
    public String devolver(@PathVariable("id") String id, @RequestParam("justificacion") String justificacion,
                           RedirectAttributes flash) {
        if (justificacion == null || justificacion.isBlank()) {
            flash.addFlashAttribute("errorServicio", "Registrá el motivo por el que la entrega no se pudo realizar.");
            return "redirect:/logistica/entregas-fallidas";
        }
        try {
            client.devolverAlDeposito(id, justificacion.strip());
            flash.addFlashAttribute("exito", "La donación volvió al depósito.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/logistica/entregas-fallidas";
    }
}
