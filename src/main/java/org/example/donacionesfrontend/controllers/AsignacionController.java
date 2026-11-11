package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.donacionesfrontend.services.AsignacionService;
import org.example.donacionesfrontend.services.EntidadService;
import org.example.donacionesfrontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;
    private final EntidadService entidadService;

    public AsignacionController(AsignacionService asignacionService, EntidadService entidadService) {
        this.asignacionService = asignacionService;
        this.entidadService = entidadService;
    }

    @GetMapping("/nueva")
    public String vistaNuevaAsignacion(HttpSession session, Model model) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        model.addAttribute("entidades", entidadService.getAll());
        return "asignaciones/formulario";
    }

    @PostMapping("/nueva")
    public String crearAsignacion(@RequestParam("idEntidad") Long idEntidad,
                                  @RequestParam("idNecesidad") Long idNecesidad,
                                  @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }

        try {
            asignacionService.asignarDonacionANecesidad(idEntidad, idNecesidad, idDonacionSegmentada);
            redirectAttributes.addFlashAttribute("exito", "¡Donación asignada con éxito a la necesidad!");
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la asignación: " + e.getMessage());
            return "redirect:/asignaciones/nueva";
        }
    }

    @GetMapping("/agregar-segmentada")
    public String vistaAgregarSegmentada(HttpSession session)
    {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        return "asignaciones/agregar-segmentada";
    }

    @PostMapping("/agregar-segmentada")
    public String agregarSegmentada(@RequestParam("idDonacionAsignada") Long idDonacionAsignada,
                                    @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        if (session.getAttribute("jwtToken") == null) { return "redirect:/login"; }
        try {
            asignacionService.agregarSegmentadaAAsignada(idDonacionAsignada, idDonacionSegmentada);
            redirectAttributes.addFlashAttribute("exito", "¡Donación segmentada agregada correctamente a la asignación existente!");
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar la donación: " + e.getMessage());
            return "redirect:/asignaciones/agregar-segmentada";
        }
    }
}