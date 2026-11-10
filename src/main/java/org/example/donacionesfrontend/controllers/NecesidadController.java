package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.necesidad.*;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.NecesidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadService;
    private final EntidadService entidadService;

    @GetMapping
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        model.addAttribute("entidades", entidadService.getAll());
        model.addAttribute("idEntidadSeleccionada", idEntidad); // Asegura que no sea indefinido
        if (idEntidad != null) {
            model.addAttribute("necesidades", necesidadService.getByEntidad(idEntidad));
        }
        return "necesidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(
            @RequestParam(value = "idEntidad", required = false) Long idEntidad,
            @RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo,
            Model model) {

        Necesidad necesidad;
        if ("RECURRENTE".equalsIgnoreCase(tipo)) {
            necesidad = new NecesidadRecurrente();
        } else {
            necesidad = new NecesidadExtraordinaria();
        }

        Subcategoria subcat = new Subcategoria();
        subcat.setCategoria(new Categoria());
        necesidad.setSubcategoria(subcat);

        model.addAttribute("necesidad", necesidad);
        model.addAttribute("entidades", entidadService.getAll());
        return "necesidades/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("necesidad") Necesidad necesidad) {
        necesidadService.create(necesidad.getId(), necesidad);
        return "redirect:/necesidades?idEntidad=" + necesidad.getId();
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idEntidad, @RequestParam Long idNecesidad) {
        necesidadService.delete(idEntidad, idNecesidad);
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }
}