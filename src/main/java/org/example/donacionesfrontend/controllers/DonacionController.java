package org.example.donacionesfrontend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.BienDTO;
import org.example.donacionesfrontend.dtos.DonacionDTO;
import org.example.donacionesfrontend.services.DonacionService;
import org.example.donacionesfrontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionRestService;
    private final DonanteService donanteRestService;

    @GetMapping
    public String listarPorDonante(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        model.addAttribute("donantes", donanteRestService.getAll());
        if (idDonante != null) {
            model.addAttribute("donaciones", donacionRestService.getByDonante(idDonante));
            model.addAttribute("idDonanteSeleccionado", idDonante);
        }
        return "donaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(Model model) {
        DonacionDTO donacion = new DonacionDTO();
        donacion.getBienesDeEntrada().add(new BienDTO()); // Bien por defecto
        model.addAttribute("donacion", donacion);
        model.addAttribute("donantes", donanteRestService.getAll());
        return "donaciones/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("donacion") DonacionDTO donacionDTO) {
        donacionRestService.create(donacionDTO.getIdDonante(), donacionDTO);
        return "redirect:/donaciones?idDonante=" + donacionDTO.getIdDonante();
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idDonante, @RequestParam Long idDonacion) {
        donacionRestService.delete(idDonante, idDonacion);
        return "redirect:/donaciones?idDonante=" + idDonante;
    }
}