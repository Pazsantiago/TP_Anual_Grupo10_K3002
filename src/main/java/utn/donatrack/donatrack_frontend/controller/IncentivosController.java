package utn.donatrack.donatrack_frontend.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utn.donatrack.donatrack_frontend.client.IncentivosClient;
import utn.donatrack.donatrack_frontend.client.RecursoNoEncontradoException;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.MisionResponse;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

@Controller
@RequestMapping("/incentivos")
public class IncentivosController {

    private final IncentivosClient client;

    public IncentivosController(IncentivosClient client) {
        this.client = client;
    }

    /** Ranking mensual + buscador de donante por id. */
    @GetMapping
    public String ranking(Model model) {
        model.addAttribute("ranking", Seccion.cargar(client::rankingMensual));
        return "incentivos/ranking";
    }

    /** Destino del buscador: valida el id y lleva al perfil. */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "donanteId", required = false) String texto, RedirectAttributes flash) {
        try {
            long id = Long.parseLong(texto == null ? "" : texto.strip());
            if (id > 0) {
                return "redirect:/incentivos/donantes/" + id;
            }
        } catch (NumberFormatException ignorada) {
            // cae en el mensaje de abajo
        }
        flash.addFlashAttribute("errorServicio", "Ingresá el número de un donante (un entero mayor a cero).");
        return "redirect:/incentivos";
    }

    @GetMapping("/donantes/{id}")
    public String perfil(@PathVariable("id") long id, Model model) {
        model.addAttribute("idDonante", id);
        try {
            model.addAttribute("metricas", client.metricas(id));
        } catch (RecursoNoEncontradoException e) {
            model.addAttribute("sinPerfil", true);
            return "incentivos/perfil";
        } catch (ServicioExternoException e) {
            model.addAttribute("errorServicio", e.getMessage());
            return "incentivos/perfil";
        }
        model.addAttribute("mision", misionActual(id));
        model.addAttribute("insignias", Seccion.cargar(() -> client.insignias(id)));
        return "incentivos/perfil";
    }

    @PostMapping("/donantes/{id}/perfil")
    public String crearPerfil(@PathVariable("id") long id, RedirectAttributes flash) {
        try {
            client.crearPerfil(id);
            flash.addFlashAttribute("exito", "Perfil de incentivos creado.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/incentivos/donantes/" + id;
    }

    /** "Sin mision activa" (404) es un estado normal, no un error: la seccion queda vacia y sin aviso. */
    private Seccion<MisionResponse> misionActual(long id) {
        try {
            return new Seccion<>(List.of(client.misionActual(id)), null);
        } catch (RecursoNoEncontradoException e) {
            return Seccion.vacia();
        } catch (ServicioExternoException e) {
            return new Seccion<>(List.of(), e.getMessage());
        }
    }
}
