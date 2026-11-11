package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.necesidad.*;
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
    public String listarPorEntidad(@RequestParam(value = "idEntidad", required = false) Long idEntidad, HttpSession session, Model model) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
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
            HttpSession session, Model model) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        model.addAttribute("idEntidadSeleccionada", idEntidad);
        return "necesidades/formulario";
    }

    @PostMapping
    public String guardarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @ModelAttribute("necesidad") Necesidad necesidad,
            HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        necesidadService.create(idEntidad, necesidad);
        redirectAttributes.addFlashAttribute("exito", "Necesidad registrada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }

    @PostMapping("/eliminar")
    public String eliminarNecesidad(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("idNecesidad") Long idNecesidad,
            HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        necesidadService.delete(idEntidad, idNecesidad);
        redirectAttributes.addFlashAttribute("exito", "Necesidad eliminada correctamente.");
        return "redirect:/necesidades?idEntidad=" + idEntidad;
    }

}