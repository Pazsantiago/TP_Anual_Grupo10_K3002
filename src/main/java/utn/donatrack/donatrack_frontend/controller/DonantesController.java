package utn.donatrack.donatrack_frontend.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonacionResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonanteResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.ImportacionResponse;
import utn.donatrack.donatrack_frontend.viewmodel.DonanteForm;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

@Controller
@RequestMapping("/donantes")
public class DonantesController {

    private final DonacionesClient client;

    public DonantesController(DonacionesClient client) {
        this.client = client;
    }

    /** Listado con busqueda opcional (nombre, documento o correo): sirve para confirmar si alguien ya tiene usuario. */
    @GetMapping
    public String listar(@RequestParam(name = "q", required = false) String q, Model model) {
        String texto = q == null || q.isBlank() ? null : q.strip();
        model.addAttribute("q", texto);
        model.addAttribute("donantes", filtrar(Seccion.cargar(client::listarDonantes), texto));
        return "donantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "donantes/form";
    }

    /** Alta en el deposito: la persona recibe un correo de bienvenida para su primer acceso (sin contrasena aca). */
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute DonanteForm form, RedirectAttributes flash) {
        String error = form.validar(false);
        if (error != null) {
            flash.addFlashAttribute("errorServicio", error);
            return "redirect:/donantes/nuevo";
        }
        try {
            client.crearDonante(form.toRequest(false));
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
            return "redirect:/donantes/nuevo";
        }
        flash.addFlashAttribute("exito", "Donante registrado. Le enviamos un correo de bienvenida.");
        return "redirect:/donantes";
    }

    @GetMapping("/importar")
    public String importar() {
        return "donantes/importar";
    }

    /** Importacion masiva por CSV. El resumen viaja como flash para mostrarlo tras el redirect. */
    @PostMapping("/importar")
    public String importarCsv(@RequestParam("archivo") MultipartFile archivo, RedirectAttributes flash) {
        String nombre = archivo.getOriginalFilename();
        if (archivo.isEmpty() || nombre == null || !nombre.toLowerCase().endsWith(".csv")) {
            flash.addFlashAttribute("errorServicio", "Elegí un archivo .csv con al menos una fila.");
            return "redirect:/donantes/importar";
        }
        try {
            ImportacionResponse resultado = client.importarDonantes(archivo);
            flash.addFlashAttribute("resultado", resultado);
            flash.addFlashAttribute("exito", "Importación finalizada.");
        } catch (ServicioExternoException e) {
            flash.addFlashAttribute("errorServicio", e.getMessage());
        }
        return "redirect:/donantes/importar";
    }

    /**
     * Donaciones de una persona, con filtros opcionales: texto de categoria/subcategoria (se busca en la descripcion
     * de la donacion y de sus bienes) y estado. Si el servicio no informa el estado, ese filtro no se puede aplicar y se avisa.
     */
    @GetMapping("/{id}/donaciones")
    public String donaciones(@PathVariable("id") long id,
                             @RequestParam(name = "estado", required = false) String estado,
                             @RequestParam(name = "categoria", required = false) String categoria,
                             Model model) {
        Seccion<DonacionResponse> todas = Seccion.cargar(() -> client.listarDonaciones(id));
        String texto = categoria == null || categoria.isBlank() ? null : categoria.strip().toLowerCase();
        String estadoElegido = estado == null || estado.isBlank() ? null : estado;
        boolean conEstado = todas.datos().stream().anyMatch(d -> d.estado() != null);
        List<DonacionResponse> filtradas = todas.datos().stream()
            .filter(d -> texto == null || contieneTexto(d, texto))
            .filter(d -> estadoElegido == null || !conEstado || estadoElegido.equals(d.estado()))
            .toList();

        model.addAttribute("idDonante", id);
        model.addAttribute("donante", buscarDonante(id));
        model.addAttribute("donaciones", new Seccion<>(filtradas, todas.error()));
        model.addAttribute("estadoElegido", estadoElegido);
        model.addAttribute("categoriaElegida", categoria == null ? null : categoria.strip());
        model.addAttribute("hayFiltros", texto != null || estadoElegido != null);
        model.addAttribute("mostrarEstado", conEstado);
        model.addAttribute("estadoNoDisponible", estadoElegido != null && !conEstado);
        return "donantes/donaciones";
    }

    private static boolean contieneTexto(DonacionResponse d, String texto) {
        return (d.descripcionGeneral() != null && d.descripcionGeneral().toLowerCase().contains(texto))
            || d.bienesDeEntrada().stream().anyMatch(b -> b.descripcion() != null && b.descripcion().toLowerCase().contains(texto));
    }

    @GetMapping("/{id}/donaciones/{idDonacion}")
    public String detalleDonacion(@PathVariable("id") long id, @PathVariable("idDonacion") long idDonacion, Model model) {
        model.addAttribute("idDonante", id);
        model.addAttribute("idDonacion", idDonacion);
        try {
            client.listarDonaciones(id).stream()
                .filter(d -> d.id() != null && d.id() == idDonacion)
                .findFirst()
                .ifPresent(d -> model.addAttribute("donacion", d));
        } catch (ServicioExternoException e) {
            model.addAttribute("errorServicio", e.getMessage());
        }
        return "donaciones/detalle";
    }

    private static Seccion<DonanteResponse> filtrar(Seccion<DonanteResponse> seccion, String texto) {
        if (texto == null) {
            return seccion;
        }
        String t = texto.toLowerCase();
        List<DonanteResponse> filtrados = seccion.datos().stream().filter(d -> Stream
            .of(d.nombre(), d.apellido(), d.razonSocial(), d.documento(), d.correoElectronico())
            .filter(Objects::nonNull)
            .anyMatch(v -> v.toLowerCase().contains(t))).toList();
        return new Seccion<>(filtrados, seccion.error());
    }

    /** El nombre es un adorno del titulo: si falla, la seccion de donaciones ya muestra el error del servicio. */
    private DonanteResponse buscarDonante(long id) {
        try {
            return client.obtenerDonante(id);
        } catch (ServicioExternoException e) {
            return null;
        }
    }
}
