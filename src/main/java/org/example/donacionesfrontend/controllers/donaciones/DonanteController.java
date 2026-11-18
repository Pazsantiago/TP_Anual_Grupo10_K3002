package org.example.donacionesfrontend.controllers.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.DonanteRequestDTO;
import org.example.donacionesfrontend.dtos.request.MedioContactoRequestDTO;
import org.example.donacionesfrontend.dtos.request.MedioNotificacion;
import org.example.donacionesfrontend.dtos.request.PersonaRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonanteResponseDTO;
import org.example.donacionesfrontend.services.DonanteService;
import org.example.donacionesfrontend.services.KeycloakAdminService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
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
        List<DonanteResponseDTO> donantes = donanteService.getAll();
        model.addAttribute("donantes", donantes);
        return "donantes/lista";
    }

    @ModelAttribute("donante")
    public DonanteRequestDTO prepararDonante(@RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo) {
        DonanteRequestDTO donanteDTO = new DonanteRequestDTO();

        PersonaRequestDTO personaDTO = new PersonaRequestDTO();
        personaDTO.setTipo(tipo); // "HUMANA" o "JURIDICA"
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
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {

            donanteService.create(donanteDTO);

            keycloakAdminService.crearUsuarioEnKeycloak(
                    email,
                    password,
                    "DONANTE"
            );


//            String accessToken = keycloakAdminService.obtenerTokenUsuario(email, password);
//            session.setAttribute("jwtToken", accessToken);
//            session.setAttribute("userEmail", email);
//            session.setAttribute("userRol", "ROLE_DONANTE");
            Object rol = (session != null) ? session.getAttribute("userRol") : null;
            if("ADMIN".equals(rol) || "ROLE_ADMIN".equals(rol)){
                redirectAttributes.addFlashAttribute("Exito", "El usuario ya ha sido creado");
                return "redirect:/donaciones/home";
            }
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error inesperado: " + e.getMessage());
            return "redirect:/donantes/nuevo";
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