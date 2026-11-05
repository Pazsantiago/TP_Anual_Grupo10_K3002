package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.NecesidadDTO;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.NecesidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadRestService;
    private final EntidadService entidadRestService;

    @GetMapping
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        model.addAttribute("entidades", entidadRestService.getAll());
        if (idEntidad != null) {
            model.addAttribute("necesidades", necesidadRestService.getByEntidad(idEntidad));
            model.addAttribute("idEntidadSeleccionada", idEntidad);
        }
        return "necesidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        model.addAttribute("necesidad", new NecesidadDTO());
        model.addAttribute("entidades", entidadRestService.getAll());
        return "necesidades/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("necesidad") NecesidadDTO dto) {
        necesidadRestService.create(dto.getIdEntidadBeneficiaria(), dto);
        return "redirect:/necesidades?idEntidad=" + dto.getIdEntidadBeneficiaria();
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idEntidad, @RequestParam Long idNecesidad) {
        necesidadRestService.delete(idEntidad, idNecesidad);
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }
}