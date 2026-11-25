package utn.donatrack.donatrack_frontend.controller.donaciones;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EstadoDonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.TipoEstadoDonacion;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionSegmentadaResponseDTO;
import utn.donatrack.donatrack_frontend.services.DonacionService;
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
        List<DonacionSegmentadaResponseDTO> segmentadas = List.of();
        if (idDonante != null) {
            try {
                segmentadas = donacionService.getSegmentadasByDonante(idDonante);
            } catch (Exception e) {
                model.addAttribute("error", "Error al obtener las donaciones segmentadas.");
            }
        }

        segmentadas = segmentadas != null ? segmentadas : List.of();
        model.addAttribute("segmentadas", segmentadas);
        model.addAttribute("idDonanteSeleccionado", idDonante);

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
            redirectAttributes.addFlashAttribute("exito", "Estado de donación segmentada #" + idDonacionSegmentada + " actualizado a VENCIDA.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar estado: " + e.getMessage());
        }

        return "redirect:/donaciones?idDonante=" + idDonante;
    }
}
