package org.example.donacionesfrontend.controllers.donaciones;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.NecesidadRequestDTO;
import org.example.donacionesfrontend.dtos.request.PeriodoRequestDTO;
import org.example.donacionesfrontend.dtos.request.SubcategoriaRequestDTO;
import org.example.donacionesfrontend.dtos.response.EntidadBeneficiariaResponseDTO;
import org.example.donacionesfrontend.dtos.response.NecesidadResponseDTO;
import org.example.donacionesfrontend.services.CategoriaService;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.NecesidadService;
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

    @ModelAttribute("necesidad")
    public NecesidadRequestDTO prepararNecesidad(@RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo) {
        NecesidadRequestDTO necesidadDTO = new NecesidadRequestDTO();
        necesidadDTO.setTipo(tipo); // "RECURRENTE" o "EXTRAORDINARIA"

        if ("RECURRENTE".equalsIgnoreCase(tipo)) {
            necesidadDTO.setPeriodoRequestDTO(new PeriodoRequestDTO());
        }

        necesidadDTO.setSubcategoria(new SubcategoriaRequestDTO());
        return necesidadDTO;
    }

    @GetMapping
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
        model.addAttribute("entidades", entidades);
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        if (idEntidad != null) {
            List<NecesidadResponseDTO> necesidades = necesidadService.getByEntidad(idEntidad);
            model.addAttribute("necesidades", necesidades);
        }
        return "necesidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(
            @RequestParam(value = "idEntidad", required = false) Long idEntidad,
            @RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo,
            Model model) {
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        model.addAttribute("tipoActual", tipo);
        model.addAttribute("entidades", entidadService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        return "necesidades/formulario";
    }

    @PostMapping
    public String guardarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @ModelAttribute("necesidad") NecesidadRequestDTO necesidadDTO,
            RedirectAttributes redirectAttributes) {
        necesidadService.create(idEntidad, necesidadDTO);
        redirectAttributes.addFlashAttribute("exito", "Necesidad registrada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }

    @PostMapping("/eliminar")
    public String eliminarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("idNecesidad") Long idNecesidad,
            RedirectAttributes redirectAttributes) {
        necesidadService.delete(idEntidad, idNecesidad);
        redirectAttributes.addFlashAttribute("exito", "Necesidad eliminada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }
}