package com.Donatrack.Logistica.Rutas.controller;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/callback")
public class gpsCallbackController {

    private final GPSService gpsService;

    public gpsCallbackController(GPSService gpsService) {
        this.gpsService = gpsService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardar(@RequestBody RutaResponse rutaResponse) {
        gpsService.guardar(rutaResponse); // 👈 usar la instancia, no la interfaz
        return ResponseEntity.ok("Ruta recibida y registrada correctamente");
    }

}
