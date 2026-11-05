package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.DonanteDTO;
import org.example.donacionesfrontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donantes")
@RequiredArgsConstructor
public class DonanteController {

    private final DonanteService donanteRestService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("donantes", donanteRestService.getAll());
        return "donantes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("donante", new DonanteDTO());
        return "donantes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("donante", donanteRestService.getById(id));
        return "donantes/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("donante") DonanteDTO donanteDTO) {
        if (donanteDTO.getId() == null) {
            donanteRestService.create(donanteDTO);
        } else {
            donanteRestService.update(donanteDTO.getId(), donanteDTO);
        }
        return "redirect:/donantes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        donanteRestService.delete(id);
        return "redirect:/donantes";
    }
}