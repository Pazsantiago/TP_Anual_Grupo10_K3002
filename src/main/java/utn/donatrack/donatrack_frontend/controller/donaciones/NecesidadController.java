package utn.donatrack.donatrack_frontend.controller.donaciones;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.NecesidadRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.PeriodoRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.SubcategoriaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.EntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.NecesidadResponseDTO;
import utn.donatrack.donatrack_frontend.services.CategoriaService;
import utn.donatrack.donatrack_frontend.services.EntidadService;
import utn.donatrack.donatrack_frontend.services.NecesidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadService;
    private final EntidadService entidadService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        try {
            List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
            model.addAttribute("entidades", entidades != null ? entidades : List.of());
        } catch (Exception e) {
            model.addAttribute("entidades", List.of());
            model.addAttribute("error", "Error al cargar entidades.");
        }

        model.addAttribute("idEntidadSeleccionada", idEntidad);

        if (idEntidad != null) {
            try {
                List<NecesidadResponseDTO> necesidades = necesidadService.getByEntidad(idEntidad);
                model.addAttribute("necesidades", necesidades != null ? necesidades : List.of());
            } catch (Exception e) {
                model.addAttribute("error", "Error al obtener las necesidades de la entidad.");
                model.addAttribute("necesidades", List.of());
            }
        }
        return "necesidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(
            @RequestParam(value = "idEntidad", required = false) Long idEntidad,
            @RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo,
            Model model) {

        NecesidadRequestDTO necesidadDTO = new NecesidadRequestDTO();
        necesidadDTO.setTipo(tipo);

        if ("RECURRENTE".equalsIgnoreCase(tipo)) {
            necesidadDTO.setPeriodoRequestDTO(new PeriodoRequestDTO());
        }
        necesidadDTO.setSubcategoria(new SubcategoriaRequestDTO());

        model.addAttribute("necesidad", necesidadDTO);
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        model.addAttribute("tipoActual", tipo);

        try {
            model.addAttribute("entidades", entidadService.getAll());
        } catch (Exception e) {
            model.addAttribute("entidades", List.of());
        }

        try {
            model.addAttribute("categorias", categoriaService.getAll());
        } catch (Exception e) {
            model.addAttribute("categorias", List.of());
        }

        return "necesidades/formulario";
    }

    @PostMapping
    public String guardarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @ModelAttribute("necesidad") NecesidadRequestDTO necesidadDTO,
            RedirectAttributes redirectAttributes) {
        try {
            necesidadService.create(idEntidad, necesidadDTO);
            redirectAttributes.addFlashAttribute("exito", "Necesidad registrada correctamente.");
            return "redirect:/necesidades?idEntidad=" + idEntidad;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la necesidad: " + e.getMessage());
            return "redirect:/necesidades/nueva?idEntidad=" + idEntidad + "&tipo=" + (necesidadDTO != null ? necesidadDTO.getTipo() : "EXTRAORDINARIA");
        }
    }

    @PostMapping("/eliminar")
    public String eliminarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("idNecesidad") Long idNecesidad,
            RedirectAttributes redirectAttributes) {
        try {
            necesidadService.delete(idEntidad, idNecesidad);
            redirectAttributes.addFlashAttribute("exito", "Necesidad eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la necesidad: " + e.getMessage());
        }
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }
}