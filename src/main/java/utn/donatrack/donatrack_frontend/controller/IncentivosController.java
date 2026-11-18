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

    private static final String MENSAJE_ID_INVALIDO = "Ingresá el número de un donante (un entero mayor a cero).";

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

    /** Guia de categorias y misiones (contenido informativo). */
    @GetMapping("/misiones")
    public String guiaDeMisiones() {
        return "incentivos/misiones";
    }

    /** Administracion de perfiles: alta y baja por id de donante. */
    @GetMapping("/gestion")
    public String gestion() {
        return "incentivos/gestion";
    }

    /** Destino del buscador: valida el id y lleva al perfil. */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "donanteId", required = false) String texto, RedirectAttributes flash) {
        Long id = idValido(texto);
        if (id != null) {
            return "redirect:/incentivos/donantes/" + id;
        }
        flash.addFlashAttribute("errorServicio", MENSAJE_ID_INVALIDO);
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

    /** Alta desde el perfil de un donante sin perfil (el id viene en la URL). */
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

    @PostMapping("/donantes/{id}/nueva-mision")
    public String asignarMisionRefuerzo(@PathVariable("id") long id, RedirectAttributes flash) {
        try {
            client.asignarMisionRefuerzo(id);
            flash.addFlashAttribute("exito", "Se asignó una nueva misión de refuerzo.");
        } catch (RecursoNoEncontradoException e) {
            flash.addFlashAttribute("errorServicio", "El donante N.º " + id + " no tiene un perfil de incentivos.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/incentivos/donantes/" + id;
    }

    /** Alta desde la pagina de gestion (el id viene del formulario). */
    @PostMapping("/gestion/crear")
    public String crearPerfilDesdeGestion(@RequestParam(name = "donanteId", required = false) String texto,
                                          RedirectAttributes flash) {
        Long id = idValido(texto);
        if (id == null) {
            flash.addFlashAttribute("errorServicio", MENSAJE_ID_INVALIDO);
            return "redirect:/incentivos/gestion";
        }
        try {
            client.crearPerfil(id);
            flash.addFlashAttribute("exito", "Perfil de incentivos creado para el donante N.º " + id + ".");
            return "redirect:/incentivos/donantes/" + id;
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/incentivos/gestion";
        }
    }

    @PostMapping("/gestion/eliminar")
    public String eliminarPerfil(@RequestParam(name = "donanteId", required = false) String texto,
                                 RedirectAttributes flash) {
        Long id = idValido(texto);
        if (id == null) {
            flash.addFlashAttribute("errorServicio", MENSAJE_ID_INVALIDO);
            return "redirect:/incentivos/gestion";
        }
        try {
            client.eliminarPerfil(id);
            flash.addFlashAttribute("exito", "Perfil del donante N.º " + id + " eliminado.");
        } catch (RecursoNoEncontradoException e) {
            flash.addFlashAttribute("errorServicio", "No encontramos un perfil para el donante N.º " + id + ".");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/incentivos/gestion";
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

    /** Id de donante valido (entero > 0) o null si el texto no lo es. */
    private static Long idValido(String texto) {
        try {
            long id = Long.parseLong(texto == null ? "" : texto.strip());
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
