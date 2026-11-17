package org.example.donacionesfrontend.controllers.donaciones;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.EstadoDonacionRequestDTO;
import org.example.donacionesfrontend.dtos.request.TipoEstadoDonacion;
import org.example.donacionesfrontend.dtos.response.DonacionSegmentadaResponseDTO;
import org.example.donacionesfrontend.services.DonacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/donaciones")
@RequiredArgsConstructor
public class DonacionSegmentadaController {

    private final DonacionService donacionService;

    @GetMapping("/segmentadas")
    public String listarSegmentadas(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {


        List<DonacionSegmentadaResponseDTO> segmentadas = donacionService.getSegmentadasByDonante(idDonante);
        model.addAttribute("segmentadas", segmentadas);

        List<String> estadosPresentes = segmentadas.stream()
                .map(DonacionSegmentadaResponseDTO::getEstadoActual)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<String> categoriasPresentes = segmentadas.stream()
                .map(DonacionSegmentadaResponseDTO::getCategoria)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<String> subcategoriasPresentes = segmentadas.stream()
                .map(DonacionSegmentadaResponseDTO::getSubcategoria)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("estados", estadosPresentes);
        model.addAttribute("categorias", categoriasPresentes);
        model.addAttribute("subcategorias", subcategoriasPresentes);

        return "donaciones/segmentadas";
    }

    @GetMapping("/vencida")
    public String formularioMarcarVencida(
            @RequestParam(value = "idDonante", required = false) Long idDonante,
            @RequestParam(value = "idDonacion", required = false) Long idDonacion,
            @RequestParam(value = "idSegmentada", required = false) Long idSegmentada,
            Model model) {

        EstadoDonacionRequestDTO estadoDTO = new EstadoDonacionRequestDTO();
        estadoDTO.setTipoEstado(TipoEstadoDonacion.VENCIDA);

        model.addAttribute("idDonante", idDonante);
        model.addAttribute("idDonacion", idDonacion);
        model.addAttribute("idDonacionSegmentada", idSegmentada);
        model.addAttribute("estadoDonacion", estadoDTO);

        return "donaciones/vencida";
    }

    @PostMapping("/vencida")
    public String registrarEstadoVencido(
            @RequestParam("idDonante") Long idDonante,
            @RequestParam("idDonacion") Long idDonacion,
            @RequestParam("idDonacionSegmentada") Long idDonacionSegmentada,
            @ModelAttribute("estadoDonacion") EstadoDonacionRequestDTO estadoDTO,
            RedirectAttributes redirectAttributes) {

        try {
            donacionService.actualizarEstadoSegmentada(idDonante, idDonacion, idDonacionSegmentada, estadoDTO);
            redirectAttributes.addFlashAttribute("exito", "Estado de la donación segmentada #" + idDonacionSegmentada + " actualizado a VENCIDA.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar estado: " + e.getMessage());
        }

        return "redirect:/donaciones?idDonante=" + idDonante;
    }

}