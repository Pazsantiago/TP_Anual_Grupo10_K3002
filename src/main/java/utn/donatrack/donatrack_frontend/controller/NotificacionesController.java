package utn.donatrack.donatrack_frontend.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.client.NotificacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.viewmodel.MedioVista;
import utn.donatrack.donatrack_frontend.viewmodel.NotificacionForm;

@Controller
@RequestMapping("/notificaciones")
public class NotificacionesController {

    private final NotificacionesClient client;

    public NotificacionesController(NotificacionesClient client) {
        this.client = client;
    }

    @ModelAttribute("medios")
    public MedioVista[] medios() {
        return MedioVista.values();
    }

    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("notificaciones", client.listar());
        } catch (ServicioExternoException e) {
            model.addAttribute("notificaciones", List.of());
            model.addAttribute("errorServicio", e.getMessage());
        }
        return "notificaciones/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("form", new NotificacionForm());
        return "notificaciones/form";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("form", new NotificacionForm());
        return "notificaciones/home";
    }

    @PostMapping("/nueva")
    public String enviar(@Valid @ModelAttribute("form") NotificacionForm form,
                         BindingResult errores,
                         Model model,
                         RedirectAttributes flash) {
        if (errores.hasErrors()) {
            return "notificaciones/form";
        }
        try {
            client.enviar(form.toRequest());
        } catch (ServicioExternoException e) {
            model.addAttribute("errorServicio", e.getMessage());
            return "notificaciones/form";
        }
        // Post/Redirect/Get: evita reenviar el formulario al recargar
        flash.addFlashAttribute("exito", "Notificación enviada a " + form.getNombre() + ".");
        return "redirect:/notificaciones";
    }
}
