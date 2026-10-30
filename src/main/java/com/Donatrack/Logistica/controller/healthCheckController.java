package com.Donatrack.Logistica.controller;
import com.Donatrack.Logistica.Rutas.domain.RutaRequest;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import com.Donatrack.Logistica.services.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/healthCheck")
public class healthCheckController {

    @GetMapping("")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

}
