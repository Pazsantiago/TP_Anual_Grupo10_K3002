package org.example.donacionesfrontend.controllers;

import org.example.donacionesfrontend.services.RankingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rankings")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public String listarRankings(Model model) {
        model.addAttribute("rankings", rankingService.obtenerRankings());
        return "rankings/lista";
    }

    @PostMapping("/generar")
    public String generarRankings(RedirectAttributes redirectAttributes) {
        try {
            rankingService.generarRankings();
            redirectAttributes.addFlashAttribute("exito", "Ranking generado y actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al generar ranking: " + e.getMessage());
        }
        return "redirect:/rankings";
    }

    @GetMapping("/repetidas")
    public String entidadesRepetidas(@RequestParam(value = "idDonacion", required = false) Long idDonacion, Model model) {
        model.addAttribute("idDonacionSeleccionada", idDonacion);
        if (idDonacion != null) {
            model.addAttribute("repetidas", rankingService.obtenerEntidadesRepetidas(idDonacion));
        }
        return "rankings/repetidas";
    }
}