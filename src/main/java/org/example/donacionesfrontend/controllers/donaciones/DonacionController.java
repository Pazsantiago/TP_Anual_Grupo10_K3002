package org.example.donacionesfrontend.controllers.donaciones;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.BienRequestDTO;
import org.example.donacionesfrontend.dtos.request.DonacionRequestDTO;
import org.example.donacionesfrontend.dtos.request.SubcategoriaRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonacionResponseDTO;
import org.example.donacionesfrontend.dtos.response.DonanteResponseDTO;
import org.example.donacionesfrontend.services.CategoriaService;
import org.example.donacionesfrontend.services.DonacionService;
import org.example.donacionesfrontend.services.DonanteService;
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
    public String listarPorDonante(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        List<DonanteResponseDTO> donantes = donanteService.getAll();
        model.addAttribute("donantes", donantes);
        if (idDonante != null) {
            List<DonacionResponseDTO> donaciones = donacionService.getByDonante(idDonante);
            model.addAttribute("donaciones", donaciones);
            model.addAttribute("idDonanteSeleccionado", idDonante);
        }
        return "donaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        DonacionRequestDTO donacionDTO = new DonacionRequestDTO();

        // Inicializamos el primer bien con su subcategoría
        BienRequestDTO primerBien = new BienRequestDTO();
        primerBien.setTipo("CON_ESTADO");
        primerBien.setSubcategoria(new SubcategoriaRequestDTO());

        donacionDTO.setBienesDeEntrada(new ArrayList<>(List.of(primerBien)));

        model.addAttribute("donacion", donacionDTO);
        model.addAttribute("donantes", donanteService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("idDonanteSeleccionado", idDonante);

        return "donaciones/formulario";
    }

    @PostMapping
    public String guardar(
            @RequestParam("idDonante") Long idDonante,
            @ModelAttribute("donacion") DonacionRequestDTO donacionDTO,
            RedirectAttributes redirectAttributes) {

        // Filtramos cualquier bien que no tenga descripción cargada
        if (donacionDTO.getBienesDeEntrada() != null) {
            List<BienRequestDTO> bienesValidos = donacionDTO.getBienesDeEntrada().stream()
                    .filter(b -> b.getDescripcion() != null && !b.getDescripcion().isBlank())
                    .toList();
            donacionDTO.setBienesDeEntrada(bienesValidos);
        }

        donacionService.create(idDonante, donacionDTO);
        redirectAttributes.addFlashAttribute("exito", "Donación registrada correctamente.");
        return "redirect:/donaciones?idDonante=" + idDonante;
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam("idDonante") Long idDonante,
            @RequestParam("idDonacion") Long idDonacion,
            RedirectAttributes redirectAttributes) {
        donacionService.delete(idDonante, idDonacion);
        redirectAttributes.addFlashAttribute("exito", "Donación eliminada correctamente.");
        return "redirect:/donaciones?idDonante=" + idDonante;
    }
}