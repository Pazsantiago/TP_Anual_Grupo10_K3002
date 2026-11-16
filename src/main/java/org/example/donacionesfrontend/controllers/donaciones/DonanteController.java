package org.example.donacionesfrontend.controllers.donaciones;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.DonanteRequestDTO;
import org.example.donacionesfrontend.dtos.request.MedioContactoRequestDTO;
import org.example.donacionesfrontend.dtos.request.PersonaRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonanteResponseDTO;
import org.example.donacionesfrontend.services.DonanteService;
import org.example.donacionesfrontend.services.KeycloakAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

            keycloakAdminService.crearUsuarioEnKeycloak(
                    email,
                    password,
                    "ROLE_DONANTE"
            );

            // 3. Guardamos la entidad Donante en la base de datos de nuestro sistema
            donanteService.create(donanteDTO);

            redirectAttributes.addFlashAttribute("exito", "¡Donante y usuario creados con éxito! Por favor inicie sesión.");
            return "redirect:/oauth2/authorization/keycloak";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el donante/usuario: " + e.getMessage());
            return "redirect:/donantes/nuevo";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        donanteService.delete(id);
        return "redirect:/donantes";
    }

    @GetMapping("/importar")
    public String vistaImportar() {
        return "donantes/importar";
    }

    @PostMapping("/importar")
    public String importarCsv(@RequestParam("rutaArchivo") String rutaArchivo, RedirectAttributes redirectAttributes) {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Por favor ingresá la ruta o nombre del archivo CSV.");
            return "redirect:/donantes/importar";
        }
        try {
            donanteService.importarMasivoCsv(rutaArchivo);
            redirectAttributes.addFlashAttribute("exito", "Importación iniciada correctamente para: " + rutaArchivo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la importación: " + e.getMessage());
        }
        return "redirect:/donantes/importar";
    }
}