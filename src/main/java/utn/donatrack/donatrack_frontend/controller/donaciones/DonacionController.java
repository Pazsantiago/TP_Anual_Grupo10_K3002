package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.BienRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.SubcategoriaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;
import utn.donatrack.donatrack_frontend.services.CategoriaService;
import utn.donatrack.donatrack_frontend.services.DonacionService;
import utn.donatrack.donatrack_frontend.services.DonanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;
    private final DonanteService donanteService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String listarPorDonante(
            @RequestParam(value = "idDonante", required = false) Long idDonanteParam,
            HttpSession session,
            Model model) {
        Object rolObj = (session != null) ? session.getAttribute("userRol") : null;
        boolean esAdmin = rolObj != null && rolObj.toString().contains("ADMIN");
        Long idDonanteFinal = null;
        if (esAdmin) {
            if (idDonanteParam != null) {
                idDonanteFinal = idDonanteParam;
                session.setAttribute("idDonante", idDonanteFinal);
            }
            else if (session != null && session.getAttribute("idDonante") != null) {
                idDonanteFinal = (Long) session.getAttribute("idDonante");
            }

            try {
                List<DonanteResponseDTO> todosLosDonantes = donanteService.getAll();
                model.addAttribute("donantes", todosLosDonantes != null ? todosLosDonantes : List.of());
            } catch (Exception e) {
                model.addAttribute("donantes", List.of());
                model.addAttribute("error", "Error al cargar la lista de donantes.");
            }

        } else {
            String email = (session != null) ? (String) session.getAttribute("userEmail") : null;
            if (email != null) {
                try {
                    DonanteResponseDTO miDonante = donanteService.getByEmail(email);
                    if (miDonante != null) {
                        idDonanteFinal = miDonante.getId();
                        session.setAttribute("idDonante", idDonanteFinal);

                        model.addAttribute("donantes", List.of(miDonante));
                    }
                } catch (Exception e) {
                    model.addAttribute("error", "Error al obtener tus datos de donante.");
                }
            }
        }
        List<DonacionResponseDTO> donaciones = List.of();
        if (idDonanteFinal != null) {
            try {
                donaciones = donacionService.getByDonante(idDonanteFinal);
            } catch (Exception e) {
                model.addAttribute("error", "Error al cargar las donaciones.");
            }
            model.addAttribute("idDonanteSeleccionado", idDonanteFinal);
        }

        model.addAttribute("donaciones", donaciones != null ? donaciones : List.of());
        return "donaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        DonacionRequestDTO donacionDTO = new DonacionRequestDTO();

        // Inicializamos el primer bien vacio para binding dinámico
        BienRequestDTO primerBien = new BienRequestDTO();
        primerBien.setTipo("CON_ESTADO");
        primerBien.setSubcategoria(new SubcategoriaRequestDTO());
        donacionDTO.setBienesDeEntrada(new ArrayList<>(List.of(primerBien)));

        model.addAttribute("donacion", donacionDTO);
        model.addAttribute("idDonanteSeleccionado", idDonante);

        try {
            model.addAttribute("donantes", donanteService.getAll());
        } catch (Exception e) {
            model.addAttribute("donantes", List.of());
        }

        try {
            model.addAttribute("categorias", categoriaService.getAll());
        } catch (Exception e) {
            model.addAttribute("categorias", List.of());
        }

        return "donaciones/formulario";
    }

    @PostMapping("/nueva")
    public String guardar(
            @RequestParam("idDonante") Long idDonante,
            @ModelAttribute("donacion") DonacionRequestDTO donacionDTO,
            RedirectAttributes redirectAttributes) {

        try {
            if (donacionDTO.getBienesDeEntrada() != null) {
                List<BienRequestDTO> bienesValidos = donacionDTO.getBienesDeEntrada().stream()
                        .filter(b -> b.getDescripcion() != null && !b.getDescripcion().isBlank())
                        .toList();
                donacionDTO.setBienesDeEntrada(bienesValidos);
            }

            donacionService.create(idDonante, donacionDTO);
            redirectAttributes.addFlashAttribute("exito", "Donación registrada correctamente.");
            return "redirect:/donaciones?idDonante=" + idDonante;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la donación: " + e.getMessage());
            return "redirect:/donaciones/nueva?idDonante=" + idDonante;
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam("idDonante") Long idDonante,
            @RequestParam("idDonacion") Long idDonacion,
            RedirectAttributes redirectAttributes) {
        try {
            donacionService.delete(idDonante, idDonacion);
            redirectAttributes.addFlashAttribute("exito", "Donación eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar donación: " + e.getMessage());
        }
        return "redirect:/donaciones?idDonante=" + idDonante;
    }
}