package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.services.CamionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {
    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping
    public List<Camion> listarCamiones() {
        return camionService.obtenerCamiones();
    }

    @PostMapping
    public Camion registrarCamion(@RequestBody Camion camion) {
        return camionService.guardar(camion);
    }
}
