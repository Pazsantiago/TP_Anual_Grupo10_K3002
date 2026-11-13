package org.example.donacionesfrontend.controllers.logistica;

import java.time.LocalDate;

import org.example.donacionesfrontend.services.LogisticaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/logistica")
@RequiredArgsConstructor
public class LogisticaController {

    private final LogisticaService logistica;

    @GetMapping
    public String home() { return "redirect:/logistica/camiones"; }

    // ---------- camiones ----------
    @GetMapping("/camiones")
    public String camiones(Model model) {
        try { model.addAttribute("camiones", logistica.camiones()); }
        catch (Exception e) { model.addAttribute("error", "No se pudo consultar Logística. Reintentá en unos segundos."); }
        return "logistica/camiones";
    }

    @PostMapping("/camiones")
    public String crear(@RequestParam String patente, @RequestParam double capacidadCarga,
                        @RequestParam double capacidadVolumenM3, @RequestParam double alturaM,
                        RedirectAttributes ra) {
        try {
            logistica.crearCamion(patente.trim().toUpperCase(), capacidadCarga, capacidadVolumenM3, alturaM);
            ra.addFlashAttribute("ok", "Camión " + patente + " creado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se pudo crear el camión (¿patente repetida o datos inválidos?).");
        }
        return "redirect:/logistica/camiones";
    }

    @PostMapping("/camiones/disponible")
    public String disponible(@RequestParam String patente, @RequestParam boolean disponible, RedirectAttributes ra) {
        try {
            logistica.setDisponible(patente, disponible);
            ra.addFlashAttribute("ok", "Camión " + patente + (disponible ? " habilitado." : " deshabilitado."));
        } catch (Exception e) { ra.addFlashAttribute("error", "No se pudo actualizar el camión."); }
        return "redirect:/logistica/camiones";
    }

    @PostMapping("/camiones/ubicacion")
    public String ubicacion(@RequestParam String patente, @RequestParam double latitud,
                            @RequestParam double longitud, RedirectAttributes ra) {
        try {
            logistica.actualizarUbicacion(patente, latitud, longitud);
            ra.addFlashAttribute("ok", "Ubicación actualizada.");
        } catch (Exception e) { ra.addFlashAttribute("error", "Coordenadas inválidas."); }
        return "redirect:/logistica/camiones";
    }

    // ---------- rutas ----------
    @GetMapping("/rutas")
    public String rutas(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        Model model) {
        LocalDate f = fecha != null ? fecha : LocalDate.now().plusDays(1);
        try { model.addAttribute("rutas", logistica.rutas(f)); }
        catch (Exception e) { model.addAttribute("error", "No se pudieron consultar las rutas."); }
        model.addAttribute("fecha", f);
        return "logistica/rutas";
    }

    @PostMapping("/rutas/iniciar")
    public String iniciar(@RequestParam String id, RedirectAttributes ra) {
        try {
            logistica.iniciarRuta(id);
            ra.addFlashAttribute("ok", "Ruta iniciada. Se notificó a las entidades.");
        } catch (Exception e) { ra.addFlashAttribute("error", "No se pudo iniciar la ruta (¿ya fue iniciada?)."); }
        return "redirect:/logistica/rutas";
    }

    // ---------- seguimiento ----------
    @GetMapping("/seguimiento")
    public String seguimiento(@RequestParam(defaultValue = "1") Long entidad, Model model) {
        try { model.addAttribute("items", logistica.seguimientoEntidad(entidad)); }
        catch (Exception e) { model.addAttribute("error", "No se pudo consultar el seguimiento."); }
        model.addAttribute("entidad", entidad);
        return "logistica/seguimiento";
    }

    // ---------- entregas (recepción) ----------
    @GetMapping("/entregas")
    public String entregas(@RequestParam(defaultValue = "1") Long entidad, Model model) {
        try { model.addAttribute("entregas", logistica.entregasEntidad(entidad)); }
        catch (Exception e) { model.addAttribute("error", "No se pudieron consultar las entregas."); }
        model.addAttribute("entidad", entidad);
        return "logistica/entregas";
    }

    @PostMapping("/entregas/confirmar")
    public String confirmar(@RequestParam String id, @RequestParam(required = false) String foto,
                            @RequestParam Long entidad, RedirectAttributes ra) {
        try { logistica.confirmar(id, foto); ra.addFlashAttribute("ok", "Recepción confirmada."); }
        catch (Exception e) { ra.addFlashAttribute("error", "No se pudo confirmar la entrega."); }
        return "redirect:/logistica/entregas?entidad=" + entidad;
    }

    @PostMapping("/entregas/no-recibida")
    public String noRecibida(@RequestParam String id, @RequestParam String motivo,
                             @RequestParam Long entidad, RedirectAttributes ra) {
        try { logistica.noRecibida(id, motivo); ra.addFlashAttribute("ok", "Se informó que no se recibió la entrega."); }
        catch (Exception e) { ra.addFlashAttribute("error", "No se pudo registrar."); }
        return "redirect:/logistica/entregas?entidad=" + entidad;
    }
}