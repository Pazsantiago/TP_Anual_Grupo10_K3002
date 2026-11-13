package utn.donatrack.donatrack_frontend.controller.privados;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EstadoDonacionRequest;
import utn.donatrack.donatrack_frontend.viewmodel.DonacionForm;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

/**
 * Carga de donaciones en el deposito y acceso a las donaciones de cada donante.
 * Pendiente del servicio: listado general de donaciones en deposito (atributo "deposito") y catalogo de categorias ("categorias").
 */
@Controller
@RequestMapping("/donaciones")
public class DonacionesController {

    private final DonacionesClient client;

    public DonacionesController(DonacionesClient client) {
        this.client = client;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("donantes", Seccion.cargar(client::listarDonantes));
        return "donaciones/lista";
    }

    /** Destino del buscador por donante. */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "donante", required = false) Long idDonante) {
        return idDonante == null ? "redirect:/donaciones" : "redirect:/donantes/" + idDonante + "/donaciones";
    }

    @GetMapping("/nueva")
    public String nueva(@RequestParam(name = "donante", required = false) Long donante, Model model) {
        model.addAttribute("donantes", Seccion.cargar(client::listarDonantes));
        model.addAttribute("donanteElegido", donante);
        return "donaciones/form";
    }

    /** Registra la carga completa; tras el alta se lleva a las donaciones del donante, donde se ve el resultado. */
    @PostMapping("/nueva")
    public String crear(@ModelAttribute DonacionForm form, RedirectAttributes flash) {
        String error = form.validar();
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return volverAlForm(form.getIdDonante());
        }
        try {
            client.crearDonacion(form.toRequest());
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return volverAlForm(form.getIdDonante());
        }
        flash.addFlashAttribute("exito", "Donación registrada. El sistema la separó por subcategoría.");
        return "redirect:/donantes/" + form.getIdDonante() + "/donaciones";
    }

    /** Una donacion vencida deja de estar disponible para asignarse. */
    @PostMapping("/{id}/vencida")
    public String marcarVencida(@PathVariable("id") long id,
                                @RequestParam(name = "idDonante", required = false) Long idDonante, RedirectAttributes flash) {
        try {
            client.cambiarEstadoDonacion(id, new EstadoDonacionRequest("VENCIDA", null));
            flash.addFlashAttribute("exito", "La donación N.º " + id + " quedó como vencida.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return idDonante == null ? "redirect:/donaciones" : "redirect:/donantes/" + idDonante + "/donaciones";
    }

    private static String volverAlForm(Long idDonante) {
        return idDonante == null ? "redirect:/donaciones/nueva" : "redirect:/donaciones/nueva?donante=" + idDonante;
    }
}
