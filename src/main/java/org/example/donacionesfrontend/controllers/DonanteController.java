package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.donante.*;
import org.example.donacionesfrontend.services.DonanteService;
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

    private final DonanteService donanteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("donantes", donanteService.getAll());
        return "donantes/lista";
    }

    @ModelAttribute("donante")
    public Donante prepararDonante(@RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo) {
        Donante donante = new Donante();

        if ("JURIDICA".equalsIgnoreCase(tipo)) {
            PersonaJuridica pj = new PersonaJuridica();
            pj.setDireccion(new Direccion());
            pj.setRepresentantes(new ArrayList<>());
            donante.setPersona(pj);
        } else {
            PersonaHumana ph = new PersonaHumana();
            ph.setDocumento(new Documento());
            ph.setDireccion(new Direccion());
            donante.setPersona(ph);
        }

        MedioContacto contacto = new MedioContacto();
        contacto.setEsPredeterminado(true);
        donante.setMediosDeContacto(new ArrayList<>(List.of(contacto)));

        return donante;
    }

    // 2. El GET solo agrega la lista de personas humanas para los representantes si hiciera falta
    @GetMapping("/nuevo")
    public String formularioNuevo(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {

        List<Donante> donantes = donanteService.getAll();
        List<PersonaHumana> personasHumanas = donantes.stream()
                .filter(d -> d.getPersona() instanceof PersonaHumana)
                .map(d -> (PersonaHumana) d.getPersona())
                .toList();

        model.addAttribute("personasHumanas", personasHumanas);
        model.addAttribute("tipoActual", tipo);
        return "donantes/formulario";
    }

    // 3. El POST recibe el objeto 'donante' ya instanciado y bindeado con todos sus datos
    @PostMapping("/nuevo")
    public String guardarDonante(
            @ModelAttribute("donante") Donante donante,
            RedirectAttributes redirectAttributes) {

        donanteService.create(donante);
        redirectAttributes.addFlashAttribute("exito", "Donante registrado correctamente.");
        return "redirect:/donantes";
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
            redirectAttributes.addFlashAttribute("exito", "Importación iniciada correctamente para: " + rutaArchivo + "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la importación: " + e.getMessage());
        }
        return "redirect:/donantes/importar";
    }
}