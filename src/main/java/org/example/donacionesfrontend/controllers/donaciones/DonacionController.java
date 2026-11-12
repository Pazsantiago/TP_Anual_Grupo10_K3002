package org.example.donacionesfrontend.controllers.donaciones;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.bien.Bien;
import org.example.donacionesfrontend.dominio.bien.BienConEstado;
import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.donacion.Donacion;
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
        model.addAttribute("donantes", donanteService.getAll());
        if (idDonante != null) {
            model.addAttribute("donaciones", donacionService.getByDonante(idDonante));
            model.addAttribute("idDonanteSeleccionado", idDonante);
        }
        return "donaciones/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevo(@RequestParam(value = "idDonante", required = false) Long idDonante, Model model) {
        Donacion donacion = new Donacion();

        // Inicializamos el primer bien con su subcategoría
        BienConEstado primerBien = new BienConEstado();
        Subcategoria subcat = new Subcategoria();
        subcat.setCategoria(new Categoria());
        primerBien.setSubcategoria(subcat);

        donacion.setBienesDeEntrada(new ArrayList<>(List.of(primerBien)));

        model.addAttribute("donacion", donacion);
        model.addAttribute("donantes", donanteService.getAll());
        model.addAttribute("categorias", categoriaService.getAll());
        model.addAttribute("idDonanteSeleccionado", idDonante);

        return "donaciones/formulario";
    }

    @PostMapping
    public String guardar(
            @RequestParam("idDonante") Long idDonante,
            @ModelAttribute("donacion") Donacion donacion,
            RedirectAttributes redirectAttributes) {

        // Filtramos cualquier bien que no tenga descripción cargada
        if (donacion.getBienesDeEntrada() != null) {
            List<Bien> bienesValidos = donacion.getBienesDeEntrada().stream()
                    .filter(b -> b.getDescripcion() != null && !b.getDescripcion().isBlank())
                    .toList();
            donacion.setBienesDeEntrada(bienesValidos);
        }

        donacionService.create(idDonante, donacion);
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