package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.services.CamionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    // Endpoint para guardar un camión
    @PostMapping
    public Camion guardar(@RequestBody Camion camion) {
        return camionService.guardar(camion);
    }

    // Endpoint para obtener todos los camiones
    @GetMapping
    public List<Camion> obtenerCamiones() {
        return camionService.obtenerCamiones();
    }
}
