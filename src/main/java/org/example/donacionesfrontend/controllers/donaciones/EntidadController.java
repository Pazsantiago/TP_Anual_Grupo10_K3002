package org.example.donacionesfrontend.controllers.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.EntidadBeneficiariaRequestDTO;
import org.example.donacionesfrontend.dtos.request.NecesidadRequestDTO;
import org.example.donacionesfrontend.dtos.request.SubcategoriaRequestDTO;
import org.example.donacionesfrontend.dtos.response.EntidadBeneficiariaResponseDTO;
import org.example.donacionesfrontend.services.EntidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entidadesBeneficiarias")
@RequiredArgsConstructor
public class EntidadController {

    private final EntidadService entidadService;

    @GetMapping
    public String listar(Model model) {
        List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
        model.addAttribute("entidades", entidades);
        return "entidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        EntidadBeneficiariaRequestDTO entidadDTO = new EntidadBeneficiariaRequestDTO();
        entidadDTO.setDireccion("Ninguna");

        NecesidadRequestDTO necesidadDTO = new NecesidadRequestDTO();
        necesidadDTO.setTipo("EXTRAORDINARIA");
        necesidadDTO.setSubcategoria(new SubcategoriaRequestDTO());

        entidadDTO.setNecesidades(new ArrayList<>(List.of(necesidadDTO)));

        model.addAttribute("entidad", entidadDTO);
        return "entidades/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("entidad") EntidadBeneficiariaRequestDTO entidadDTO, HttpSession session) {
        entidadService.create(entidadDTO);
        return "redirect:/entidadesBeneficiarias";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        entidadService.delete(id);
        return "redirect:/entidadesBeneficiarias";
    }
}