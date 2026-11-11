package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.donante.Direccion;
import org.example.donacionesfrontend.dominio.entidad.EntidadBeneficiaria;
import org.example.donacionesfrontend.dominio.necesidad.NecesidadExtraordinaria;
import org.example.donacionesfrontend.services.EntidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entidadesBeneficiarias")
@RequiredArgsConstructor
public class EntidadController {

    private final EntidadService entidadService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entidades", entidadService.getAll());
        return "entidades/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        EntidadBeneficiaria entidad = new EntidadBeneficiaria();
        entidad.setDireccion("Ninguna");

        NecesidadExtraordinaria necesidad = new NecesidadExtraordinaria();
        Subcategoria subcat = new Subcategoria();
        subcat.setCategoria(new Categoria());
        necesidad.setSubcategoria(subcat);

        entidad.setNecesidades(new ArrayList<>(List.of(necesidad)));

        model.addAttribute("entidad", entidad);
        return "entidades/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("entidad") EntidadBeneficiaria entidad, HttpSession session) {
        entidadService.create(entidad);
        return "redirect:/entidadesBeneficiarias";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        entidadService.delete(id);
        return "redirect:/entidadesBeneficiarias";
    }
}