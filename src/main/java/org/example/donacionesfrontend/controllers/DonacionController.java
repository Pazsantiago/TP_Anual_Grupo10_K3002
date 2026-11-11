package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.bien.Bien;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.donacion.Donacion;
import org.example.donacionesfrontend.services.DonacionService;
import org.example.donacionesfrontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;
    private final DonanteService donanteService;

    @GetMapping
    public String listarPorDonante(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        model.addAttribute("donantes", donanteService.getAll());
        if (idDonante != null) {
            model.addAttribute("donaciones", donacionService.getByDonante(idDonante));
            model.addAttribute("idDonanteSeleccionado", idDonante);
        }
        return "donaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        Donacion donacion = new Donacion();
        Bien bien = new Bien();
        Subcategoria subcat = new Subcategoria();
        subcat.setCategoria(new Categoria());
        bien.setSubcategoria(subcat);
        donacion.setBienesDeEntrada(new ArrayList<>(List.of(bien)));

        model.addAttribute("donacion", donacion);
        model.addAttribute("donantes", donanteService.getAll());
        return "donaciones/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("donacion") Donacion donacion, HttpSession session) {
        donacionService.create(donacion.getId(), donacion);
        return "redirect:/donaciones?idDonante=" + donacion.getId();
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idDonante, @RequestParam Long idDonacion, HttpSession session) {
        donacionService.delete(idDonante, idDonacion);
        return "redirect:/donaciones?idDonante=" + idDonante;
    }
}