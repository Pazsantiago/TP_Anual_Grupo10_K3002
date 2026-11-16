package org.example.donacionesfrontend.controllers.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.EntidadBeneficiariaRequestDTO;
import org.example.donacionesfrontend.dtos.request.NecesidadRequestDTO;
import org.example.donacionesfrontend.dtos.request.SubcategoriaRequestDTO;
import org.example.donacionesfrontend.dtos.response.EntidadBeneficiariaResponseDTO;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.KeycloakAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entidadesBeneficiarias")
@RequiredArgsConstructor
public class EntidadController {

    private final KeycloakAdminService  keycloakAdminService;
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

    @PostMapping("/nueva")
    public String guardarEntidad(
            @ModelAttribute("entidad") EntidadBeneficiariaRequestDTO entidadDTO,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Alta en Keycloak con ROL_ENTIDAD
            keycloakAdminService.crearUsuarioEnKeycloak(
                    entidadDTO.getCorreoRepresentante(),
                    entidadDTO.getTelefono(),
                    password,
                    "ROLE_ENTIDAD"
            );

            // 2. Alta de la Entidad en la Base de Datos Local
            entidadService.create(entidadDTO);

            redirectAttributes.addFlashAttribute("exito", "¡Entidad Beneficiaria y usuario creados con éxito! Por favor inicie sesión.");
            return "redirect:/oauth2/authorization/keycloak";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la entidad/usuario: " + e.getMessage());
            return "redirect:/entidadesBeneficiarias/nueva";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        entidadService.delete(id);
        return "redirect:/entidadesBeneficiarias";
    }
}