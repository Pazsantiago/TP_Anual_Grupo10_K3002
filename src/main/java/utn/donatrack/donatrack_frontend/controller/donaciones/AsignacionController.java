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
        model.addAttribute("entidades", entidadService.getAll());
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
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la asignación: " + e.getMessage());
            return "redirect:/asignaciones/nueva";
        }
    }

    @GetMapping("/agregar-segmentada")
    public String vistaAgregarSegmentada()
    {
        return "asignaciones/agregar-segmentada";
    }

    @PostMapping("/agregar-segmentada")
    public String agregarSegmentada(@RequestParam("idDonacionAsignada") Long idDonacionAsignada,
                                    @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
                                    RedirectAttributes redirectAttributes) {
        try {
            asignacionService.agregarSegmentadaAAsignada(idDonacionAsignada, idDonacionSegmentada);
            redirectAttributes.addFlashAttribute("exito", "¡Donación segmentada agregada correctamente a la asignación existente!");
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar la donación: " + e.getMessage());
            return "redirect:/asignaciones/agregar-segmentada";
        }
    }

    @GetMapping
    public String listarAsignadasPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {

        // Cargamos todas las entidades para el desplegable de selección
        List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
        model.addAttribute("entidades", entidades);
        model.addAttribute("idEntidadSeleccionada", idEntidad);

        if (idEntidad != null) {
            // Obtenemos la lista de DonacionAsignadaResponseDTO para esa entidad
            List<DonacionAsignadaResponseDTO> asignadas = donacionService.getAsignadasPorEntidad(idEntidad);
            model.addAttribute("asignaciones", asignadas);
        }

        return "donaciones/asignadas";
    }

}