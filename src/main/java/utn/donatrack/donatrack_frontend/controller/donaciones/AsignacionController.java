package utn.donatrack.donatrack_frontend.controller.donaciones;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionAsignadaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.EntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.services.AsignacionService;
import utn.donatrack.donatrack_frontend.services.DonacionService;
import utn.donatrack.donatrack_frontend.services.EntidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/asignaciones")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;
    private final DonacionService donacionService;
    private final EntidadService entidadService;

    @GetMapping("/nueva")
    public String vistaNuevaAsignacion(Model model) {
        try {
            List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
            model.addAttribute("entidades", entidades != null ? entidades : List.of());
        } catch (Exception e) {
            model.addAttribute("entidades", List.of());
            model.addAttribute("error", "Error al cargar las entidades beneficiarias.");
        }
        return "asignaciones/formulario";
    }

    @PostMapping("/nueva")
    public String crearAsignacion(@RequestParam("idEntidad") Long idEntidad,
                                  @RequestParam("idNecesidad") Long idNecesidad,
                                  @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
                                  RedirectAttributes redirectAttributes) {
        try {
            asignacionService.asignarDonacionANecesidad(idEntidad, idNecesidad, idDonacionSegmentada);
            redirectAttributes.addFlashAttribute("exito", "¡Donación asignada con éxito a la necesidad!");
            return "redirect:/asignaciones?idEntidad=" + idEntidad;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la asignación: " + e.getMessage());
            return "redirect:/asignaciones/nueva";
        }
    }

    @GetMapping("/agregar-segmentada")
    public String vistaAgregarSegmentada() {
        return "asignaciones/agregar-segmentada";
    }

    @PostMapping("/agregar-segmentada")
    public String agregarSegmentada(@RequestParam("idDonacionAsignada") Long idDonacionAsignada,
                                    @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
                                    RedirectAttributes redirectAttributes) {
        try {
            asignacionService.agregarSegmentadaAAsignada(idDonacionAsignada, idDonacionSegmentada);
            redirectAttributes.addFlashAttribute("exito", "¡Donación segmentada agregada a la asignación existente!");
            return "redirect:/asignaciones";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar la donación: " + e.getMessage());
            return "redirect:/asignaciones/agregar-segmentada";
        }
    }

    @GetMapping
    public String listarAsignadasPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        try {
            List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
            model.addAttribute("entidades", entidades != null ? entidades : List.of());
        } catch (Exception e) {
            model.addAttribute("entidades", List.of());
            model.addAttribute("error", "Error al conectar con el servicio de entidades.");
        }

        model.addAttribute("idEntidadSeleccionada", idEntidad);
        List<DonacionAsignadaResponseDTO> asignadas = List.of();

        if (idEntidad != null) {
            try {
                asignadas = donacionService.getAsignadasPorEntidad(idEntidad);
            } catch (Exception e) {
                model.addAttribute("error", "Error al obtener asignaciones de la entidad seleccionada.");
            }
        }

        model.addAttribute("asignaciones", asignadas != null ? asignadas : List.of());
        return "donaciones/asignadas";
    }
}