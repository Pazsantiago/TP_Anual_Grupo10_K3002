package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonanteRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.MedioContactoRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.MedioNotificacion;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.PersonaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;
import utn.donatrack.donatrack_frontend.services.DonanteService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.services.KeycloakAdminService;

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
        List<DonanteResponseDTO> donantes = donanteService.getAll();
        model.addAttribute("donantes", donantes);
        return "donantes/lista";
    }

    @ModelAttribute("donante")
    public DonanteRequestDTO prepararDonante(@RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo) {
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

    @GetMapping("/nuevo")
    public String formularioNuevo(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {
        model.addAttribute("tipoActual", tipo);
        return "donantes/formulario";
    }

    @PostMapping("/nuevo")
    public String guardarDonante(
            @ModelAttribute("donante") DonanteRequestDTO donanteDTO,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {
        try {
            if (donanteDTO.getMediosDeContacto() != null && !donanteDTO.getMediosDeContacto().isEmpty()) {
                MedioContactoRequestDTO contacto = donanteDTO.getMediosDeContacto().get(0);
                contacto.setCorreoElectronico(email);
                contacto.setEsPredeterminado(true);
            }
            donanteService.create(donanteDTO);
            keycloakAdminService.crearUsuarioEnKeycloak(
                    email,
                    password,
                    "DONANTE"
            );

            redirectAttributes.addFlashAttribute("exito", "¡Donante y usuario creados con éxito!");

            // 3. Redirigir a la lista de donantes para verificar que aparezca
            return "redirect:/donantes";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar donante: " + e.getMessage());
            return "redirect:/donantes/nuevo";
        }
    }

    @GetMapping("/nuevo/admin")
    public String formularioNuevoByAdmin(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {
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

            Object rol = (session != null) ? session.getAttribute("userRol") : null;
            if("ADMIN".equals(rol) || "ROLE_ADMIN".equals(rol)){
                donanteService.createByAdmin(donanteDTO, email);
                redirectAttributes.addFlashAttribute("Exito", "El usuario ya ha sido creado");
                return "redirect:/panel";
            }

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error inesperado: " + e.getMessage());
            return "redirect:/donantes/nuevo/admin";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        donanteService.delete(id);
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
            redirectAttributes.addFlashAttribute("error", "Error al procesar el archivo CSV: " + e.getMessage());
        }
        return "redirect:/donantes/importador";
    }
}