package com.Donatrack.Logistica.controller;


import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.services.CamionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthCheck")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }


}

