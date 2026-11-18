package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.LogisticaClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EntidadResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.DonacionPendienteResponse;
import utn.donatrack.donatrack_frontend.viewmodel.EntidadForm;
import utn.donatrack.donatrack_frontend.viewmodel.NecesidadForm;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;
import java.util.List;

@Controller
@RequestMapping("/entidades")
public class EntidadesController {

    private final DonacionesClient client;
    private final LogisticaClient logistica;

    public EntidadesController(DonacionesClient client, LogisticaClient logistica) {
        this.client = client;
        this.logistica = logistica;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entidades", Seccion.cargar(client::listarEntidades));
        return "entidades/lista";
    }

    @GetMapping("/nueva")
    public String nueva() {
        return "entidades/form";
    }

    @PostMapping("/nueva")
    public String crear(@ModelAttribute EntidadForm form, RedirectAttributes flash) {
        String error = form.validar(false);
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return "redirect:/entidades/nueva";
        }
        try {
            client.crearEntidad(form.toRequest(false));
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/entidades/nueva";
        }
        flash.addFlashAttribute("exito", "Entidad registrada.");
        return "redirect:/entidades";
    }

    @GetMapping("/{id}/necesidades")
    public String necesidades(@PathVariable("id") long id, Model model) {
        model.addAttribute("idEntidad", id);
        model.addAttribute("entidad", buscarEntidad(id));
        model.addAttribute("necesidades", Seccion.cargar(() -> client.listarNecesidades(id)));
        return "entidades/necesidades";
    }

    @GetMapping("/{id}/necesidades/nueva")
    public String nuevaNecesidad(@PathVariable("id") long id, Model model) {
        model.addAttribute("idEntidad", id);
        return "entidades/necesidad-form";
    }

    @PostMapping("/{id}/necesidades/nueva")
    public String crearNecesidad(@PathVariable("id") long id, @ModelAttribute NecesidadForm form, RedirectAttributes flash) {
        String error = form.validar();
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return "redirect:/entidades/" + id + "/necesidades/nueva";
        }
        try {
            client.crearNecesidad(id, form.toRequest());
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/entidades/" + id + "/necesidades/nueva";
        }
        flash.addFlashAttribute("exito", "Necesidad registrada.");
        return "redirect:/entidades/" + id + "/necesidades";
    }

    /**
     * Donaciones asignadas a la entidad. Logistica las identifica por el nombre del destinatario,
     * asi que se cruzan por razon social hasta que el servicio exponga el id de la entidad.
     */
    @GetMapping("/{id}/donaciones")
    public String donaciones(@PathVariable("id") long id, Model model) {
        EntidadResponse entidad = buscarEntidad(id);
        model.addAttribute("idEntidad", id);
        model.addAttribute("entidad", entidad);
        model.addAttribute("donaciones", Seccion.cargar(() -> donacionesDe(entidad)));
        return "entidades/donaciones";
    }

    @GetMapping("/{id}/donaciones/{idDonacion}/recepcion")
    public String recepcion(@PathVariable("id") long id, @PathVariable("idDonacion") String idDonacion, Model model) {
        model.addAttribute("idEntidad", id);
        model.addAttribute("idDonacion", idDonacion);
        return "entidades/recepcion";
    }

    /** La entidad confirma la recepcion (con fotos) o informa que no llego. */
    @PostMapping("/{id}/donaciones/{idDonacion}/recepcion")
    public String registrarRecepcion(@PathVariable("id") long id, @PathVariable("idDonacion") String idDonacion,
                                     @RequestParam(name = "resultado", defaultValue = "RECIBIDA") String resultado,
                                     @RequestParam(name = "observaciones", required = false) String observaciones,
                                     @RequestParam(name = "fotos", required = false) List<MultipartFile> fotos,
                                     RedirectAttributes flash) {
        List<MultipartFile> archivos = fotos == null ? List.of() : fotos.stream().filter(f -> !f.isEmpty()).toList();
        boolean recibida = "RECIBIDA".equals(resultado);
        if (recibida && archivos.isEmpty()) {
            flash.addFlashAttribute("errorServicio", "Para confirmar la recepción cargá al menos una foto.");
            return "redirect:/entidades/" + id + "/donaciones/" + idDonacion + "/recepcion";
        }
        try {
            logistica.registrarRecepcion(idDonacion, recibida ? "RECIBIDA" : "NO_RECIBIDA", observaciones, archivos);
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/entidades/" + id + "/donaciones/" + idDonacion + "/recepcion";
        }
        flash.addFlashAttribute("exito", recibida
            ? "Recepción confirmada. Avisamos a la persona donante."
            : "Registramos que la entrega no llegó. Una persona administradora va a revisar el caso.");
        return "redirect:/entidades/" + id + "/donaciones";
    }

    private List<DonacionPendienteResponse> donacionesDe(EntidadResponse entidad) {
        if (entidad == null || entidad.razonSocial() == null) {
            return List.of();
        }
        return logistica.listarDonacionesPendientes().stream()
            .filter(d -> entidad.razonSocial().equalsIgnoreCase(d.destinatario()))
            .toList();
    }

    private EntidadResponse buscarEntidad(long id) {
        try {
            return client.obtenerEntidad(id);
        } catch (ServicioExternoException e) {
            return null;
        }
    }
}
