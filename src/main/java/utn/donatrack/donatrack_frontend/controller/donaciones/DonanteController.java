package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonanteRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.MedioContactoRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.MedioNotificacion;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.PersonaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;
import utn.donatrack.donatrack_frontend.services.DonanteService;
import utn.donatrack.donatrack_frontend.services.KeycloakAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/donantes")
@RequiredArgsConstructor
public class DonanteController {

    private final KeycloakAdminService keycloakAdminService;
    private final DonanteService donanteService;

    @GetMapping
    public String listar(Model model) {
        try {
            List<DonanteResponseDTO> donantes = donanteService.getAll();
            model.addAttribute("donantes", donantes != null ? donantes : List.of());
        } catch (Exception e) {
            model.addAttribute("error", "Error al conectar con el servicio de donantes: " + e.getMessage());
            model.addAttribute("donantes", List.of());
        }
        return "donantes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {

        DonanteRequestDTO donanteDTO = crearDonanteDTOBase(tipo);
        model.addAttribute("donante", donanteDTO);
        model.addAttribute("tipoActual", tipo);
        return "donantes/formulario";
    }

    @PostMapping("/nuevo")
    public String guardarDonante(
            @ModelAttribute("donante") DonanteRequestDTO donanteDTO,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            RedirectAttributes redirectAttributes) {
        try {
            configurarMedioContactoDefault(donanteDTO, email);
            donanteService.create(donanteDTO);

            if (password != null && !password.isBlank()) {
                keycloakAdminService.crearUsuarioEnKeycloak(email, password, "DONANTE");
            }

            redirectAttributes.addFlashAttribute("exito", "¡Donante y usuario creados con éxito!");
            return "redirect:/donantes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar donante: " + e.getMessage());
            return "redirect:/donantes/nuevo";
        }
    }

    @GetMapping("/nuevo/admin")
    public String formularioNuevoByAdmin(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {

        DonanteRequestDTO donanteDTO = crearDonanteDTOBase(tipo);
        model.addAttribute("donante", donanteDTO);
        model.addAttribute("tipoActual", tipo);
        return "donantes/formulario-no-key";
    }

    @PostMapping("/nuevo/admin")
    public String guardarDonanteByAdmin(
            @ModelAttribute("donante") DonanteRequestDTO donanteDTO,
            @RequestParam("email") String email,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            configurarMedioContactoDefault(donanteDTO, email);
            donanteService.createByAdmin(donanteDTO, email);
            redirectAttributes.addFlashAttribute("exito", "Donante registrado por administrador con éxito.");
            return "redirect:/donantes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado: " + e.getMessage());
            return "redirect:/donantes/nuevo/admin";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            donanteService.delete(id);
            redirectAttributes.addFlashAttribute("exito", "Donante eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar donante: " + e.getMessage());
        }
        return "redirect:/donantes";
    }

    @GetMapping("/importador")
    public String vistaImportar() {
        return "donantes/importar";
    }

    @PostMapping("/importador")
    public String importarCSV(@RequestParam("archivo") MultipartFile archivo, RedirectAttributes redirectAttributes) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("archivo", archivo.getResource());
            donanteService.importarMasivoCsv(body);
            redirectAttributes.addFlashAttribute("exito", "¡Archivo CSV importado con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar archivo CSV: " + e.getMessage());
        }
        return "redirect:/donantes/importador";
    }

    // Métodos auxiliares reutilizables
    private DonanteRequestDTO crearDonanteDTOBase(String tipo) {
        DonanteRequestDTO donanteDTO = new DonanteRequestDTO();
        PersonaRequestDTO personaDTO = new PersonaRequestDTO();
        personaDTO.setTipo(tipo);
        donanteDTO.setPersona(personaDTO);

        MedioContactoRequestDTO contactoDTO = new MedioContactoRequestDTO();
        contactoDTO.setEsPredeterminado(true);
        contactoDTO.setTipo(MedioNotificacion.EMAIL);
        donanteDTO.setMediosDeContacto(new ArrayList<>(List.of(contactoDTO)));
        return donanteDTO;
    }

    private void configurarMedioContactoDefault(DonanteRequestDTO donanteDTO, String email) {
        if (donanteDTO.getMediosDeContacto() == null || donanteDTO.getMediosDeContacto().isEmpty()) {
            donanteDTO.setMediosDeContacto(new ArrayList<>(List.of(new MedioContactoRequestDTO())));
        }

        MedioContactoRequestDTO contacto = donanteDTO.getMediosDeContacto().get(0);
        if (contacto.getCorreoElectronico() == null || contacto.getCorreoElectronico().isBlank()) {
            contacto.setCorreoElectronico(email);
        }
        contacto.setEsPredeterminado(true);
        if (contacto.getTipo() == null) {
            contacto.setTipo(MedioNotificacion.EMAIL);
        }
    }
}