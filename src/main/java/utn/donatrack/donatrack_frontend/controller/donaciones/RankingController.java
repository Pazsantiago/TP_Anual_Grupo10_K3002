package utn.donatrack.donatrack_frontend.controller.donaciones;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.services.RankingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public String listarRankings(Model model) {
        try {
            model.addAttribute("rankings", rankingService.obtenerRankings());
        } catch (Exception e) {
            model.addAttribute("rankings", List.of());
            model.addAttribute("error", "Error al obtener los rankings: " + e.getMessage());
        }
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
            try {
                model.addAttribute("repetidas", rankingService.obtenerEntidadesRepetidas(idDonacion));
            } catch (Exception e) {
                model.addAttribute("error", "Error al consultar entidades repetidas: " + e.getMessage());
                model.addAttribute("repetidas", List.of());
            }
        }
        return "rankings/repetidas";
    }
}