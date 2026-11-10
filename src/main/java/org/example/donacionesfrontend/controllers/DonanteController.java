package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.donante.*;
import org.example.donacionesfrontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/nuevo")
    public String formularioNuevo(
            @RequestParam(value = "tipo", defaultValue = "HUMANA") String tipo,
            Model model) {

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

        // Traemos las Personas Humanas registradas para usarlas como representantes
        List<Donante> donantes = donanteService.getAll();
        List<PersonaHumana> personasHumanas = donantes.stream()
                .filter(d -> d.getPersona() instanceof PersonaHumana)
                .map(d -> (PersonaHumana) d.getPersona())
                .toList();

        model.addAttribute("donante", donante);
        model.addAttribute("personasHumanas", personasHumanas);
        return "donantes/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("donante") Donante donante) {
        donanteService.create(donante);
        return "redirect:/donantes";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        donanteService.delete(id);
        return "redirect:/donantes";
    }
}