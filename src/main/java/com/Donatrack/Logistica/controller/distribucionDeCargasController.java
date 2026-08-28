package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.services.DistribucionDeCargasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distribucion")
public class distribucionDeCargasController {

    private final DistribucionDeCargasService distribucionDeCargasService;

    public distribucionDeCargasController(DistribucionDeCargasService distribucionDeCargasService) {
        this.distribucionDeCargasService = distribucionDeCargasService;
    }

    @PostMapping("/asignar")
    public ResponseEntity<Map<Camion, List<Bulto>>> asignarBultos(@RequestBody DistribucionRequest request) {
        Map<Camion, List<Bulto>> resultado = distribucionDeCargasService.asignarBultos(request.getCamiones(), request.getBultos());
        return ResponseEntity.ok(resultado);
    }
}
