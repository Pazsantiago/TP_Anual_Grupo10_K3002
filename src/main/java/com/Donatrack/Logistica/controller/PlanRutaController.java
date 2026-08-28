package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.PlanRuta;
import com.Donatrack.Logistica.services.PlanRutaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rutas")
public class PlanRutaController {

    private final PlanRutaService planRutaService;

    public PlanRutaController(PlanRutaService planRutaService) {
        this.planRutaService = planRutaService;
    }

    @PostMapping("/generar")
    public ResponseEntity<PlanRuta> generarRuta(@RequestBody List<Direccion> direcciones) {
        return ResponseEntity.ok(planRutaService.generarPlanRuta(direcciones));
    }
}
