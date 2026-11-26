package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EstadoDonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.TipoEstadoDonacion;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionSegmentadaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;
import utn.donatrack.donatrack_frontend.services.DonacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.services.DonanteService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/donaciones")
@RequiredArgsConstructor
public class DonacionSegmentadaController {
    private final DonanteService donanteService;
    private final DonacionService donacionService;

    @GetMapping("/segmentadas")
    public String listarSegmentadas(@RequestParam(value = "idDonante", required = false) Long idDonante, HttpSession session, Model model) {
        List<DonacionSegmentadaResponseDTO> segmentadas = List.of();
        try {
            if (idDonante == null || session.getAttribute("idDonante") == null) {
                String email = (String) session.getAttribute("userEmail");
                if (email != null) {
                    idDonante = donanteService.getByEmail(email).getId();
                    if (idDonante != null) {
                        session.setAttribute("idDonante", idDonante);
                    }
                }
            }
            segmentadas = donacionService.getSegmentadasByDonante(idDonante);
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener las donaciones segmentadas.");
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

    @GetMapping("/vencidas")
    public String formularioMarcarVencida(
            @RequestParam(value = "idDonante", required = false) Long idDonante,
            Model model) {

        List<DonanteResponseDTO> donantes = donanteService.getAll();
        model.addAttribute("donantes", donantes);

        List<DonacionSegmentadaResponseDTO> segmentadas = List.of();
        if (idDonante != null) {
            segmentadas = donacionService.getSegmentadasByDonante(idDonante);
        }

        EstadoDonacionRequestDTO estadoDTO = new EstadoDonacionRequestDTO();
        estadoDTO.setTipoEstado(TipoEstadoDonacion.VENCIDA);

        model.addAttribute("idDonanteSeleccionado", idDonante);
        model.addAttribute("segmentadas", segmentadas);
        model.addAttribute("estadoDonacion", estadoDTO);

        return "donaciones/vencidas";
    }

    @PostMapping("/vencidas")
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

        return "redirect:/donaciones/segmentadas?idDonante=" + idDonante;
    }
}
