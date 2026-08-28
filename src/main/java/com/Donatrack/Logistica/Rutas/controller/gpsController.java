package com.Donatrack.Logistica.Rutas.controller;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class gpsController {
    @RestController
    @RequestMapping("/api/rutas")
    public class GPSController {

        private final GPSService gpsService;

        public GPSController(GPSService gpsService) {
            this.gpsService = gpsService;
        }

        @PostMapping("/planificar")
        public ResponseEntity<Ruta> planificarRuta(@RequestBody List<String> direcciones) {
            Ruta ruta = gpsService.generarRuta(direcciones);
            return ResponseEntity.ok(ruta);
        }
    }

}
