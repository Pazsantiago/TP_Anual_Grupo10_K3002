package com.Donatrack.Logistica.controller;
import com.Donatrack.Logistica.Rutas.domain.RutaRequest;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import com.Donatrack.Logistica.services.RutaService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/rutas/planificar")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // Solicitar planificación de una ruta
    @PostMapping("/planificar")
    public RutaResponse planificarRuta(@RequestBody RutaRequest request) {
        return rutaService.planificarRuta(request);
    }

    // Obtener una ruta específica por ID
    @GetMapping("/{id}")
    public RutaResponse obtenerRuta(@PathVariable Long id) {
        return rutaService.obtenerRutaPorId(id);
    }
}
