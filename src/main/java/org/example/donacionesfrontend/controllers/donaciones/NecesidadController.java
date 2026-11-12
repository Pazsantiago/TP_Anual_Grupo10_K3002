package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.necesidad.*;
import org.example.donacionesfrontend.services.CategoriaService;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.NecesidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/necesidades")
@RequiredArgsConstructor
public class NecesidadController {

    private final NecesidadService necesidadService;
    private final EntidadService entidadService;
    private final CategoriaService categoriaService; // Inyectamos CategoriaService igual que en Donaciones

    @ModelAttribute("necesidad")
    public Necesidad prepararNecesidad(@RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo) {
        Necesidad necesidad;
        if ("RECURRENTE".equalsIgnoreCase(tipo)) {
            NecesidadRecurrente nr = new NecesidadRecurrente();
            Periodo periodo = new Periodo();
            nr.setPeriodo(periodo);
            necesidad = nr;
        } else {
            necesidad = new NecesidadExtraordinaria();
        }

        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setCategoria(new Categoria());
        necesidad.setSubcategoria(subcategoria);

        return necesidad;
    }

    @GetMapping
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, Model model) {
        model.addAttribute("entidades", entidadService.getAll());
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        if (idEntidad != null) {
            model.addAttribute("necesidades", necesidadService.getByEntidad(idEntidad));
        }
        return "necesidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNueva(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam(value = "tipo", defaultValue = "EXTRAORDINARIA") String tipo,
            Model model) {
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        model.addAttribute("tipoActual", tipo);
        model.addAttribute("categorias", categoriaService.getAll()); // Cargamos las categorías para el select
        return "necesidades/formulario";
    }

    @PostMapping
    public String guardarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @ModelAttribute("necesidad") Necesidad necesidad,
            RedirectAttributes redirectAttributes) {
        necesidadService.create(idEntidad, necesidad);
        redirectAttributes.addFlashAttribute("exito", "Necesidad registrada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }

    @PostMapping("/eliminar")
    public String eliminarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("idNecesidad") Long idNecesidad,
            RedirectAttributes redirectAttributes) {
        necesidadService.delete(idEntidad, idNecesidad);
        redirectAttributes.addFlashAttribute("exito", "Necesidad eliminada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }
}