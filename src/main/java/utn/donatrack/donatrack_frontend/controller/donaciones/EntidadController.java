package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EntidadBeneficiariaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.NecesidadRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.SubcategoriaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.EntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.services.KeycloakAdminService;
import utn.donatrack.donatrack_frontend.services.EntidadService;
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

    private final KeycloakAdminService keycloakAdminService;
    private final EntidadService entidadService;

    @GetMapping
    public String listar(Model model) {
        try {
            List<EntidadBeneficiariaResponseDTO> entidades = entidadService.getAll();
            model.addAttribute("entidades", entidades != null ? entidades : List.of());
        } catch (Exception e) {
            model.addAttribute("entidades", List.of());
            model.addAttribute("error", "Error al conectar con el servicio de entidades.");
        }
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
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            entidadService.create(entidadDTO);

            if (password != null && !password.isBlank()) {
                keycloakAdminService.crearUsuarioEnKeycloak(email, password, "ENTIDAD");
            }

            redirectAttributes.addFlashAttribute("exito", "¡Entidad registrada con éxito!");
            return "redirect:/entidadesBeneficiarias";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la entidad: " + e.getMessage());
            return "redirect:/entidadesBeneficiarias/nueva";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            entidadService.delete(id);
            redirectAttributes.addFlashAttribute("exito", "Entidad eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar entidad: " + e.getMessage());
        }
        return "redirect:/entidadesBeneficiarias";
    }
}