package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.EntidadBeneficiariaDTO;
import org.example.donacionesfrontend.services.EntidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entidades")
@RequiredArgsConstructor
public class EntidadController {

    private final EntidadService entidadRestService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entidades", entidadRestService.getAll());
        return "entidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        model.addAttribute("entidad", new EntidadBeneficiariaDTO());
        return "entidades/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("entidad", entidadRestService.getById(id));
        return "entidades/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("entidad") EntidadBeneficiariaDTO dto) {
        if (dto.getId() == null) {
            entidadRestService.create(dto);
        } else {
            entidadRestService.update(dto.getId(), dto);
        }
        return "redirect:/entidades";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        entidadRestService.delete(id);
        return "redirect:/entidades";
    }
}