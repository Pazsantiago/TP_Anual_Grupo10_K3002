package utn.donatrack.donatrack_frontend.controller.donaciones;

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

    @PostMapping("/nueva")
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