package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import java.util.List;

import com.Donatrack.Logistica.domain.Direccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rutas")
public class GPSController {
    private final GPSService gpsService;

    public GPSController(GPSService gpsService) {
        this.gpsService = gpsService;
    }

    @PostMapping("/planificar")
    public ResponseEntity<Ruta> planificarRuta(@RequestBody List<Direccion> direcciones) {
        return ResponseEntity.ok(gpsService.planificarRuta(direcciones));
    }
}
