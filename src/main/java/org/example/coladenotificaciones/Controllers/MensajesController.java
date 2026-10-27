package org.example.coladenotificaciones.Controllers;

import org.example.coladenotificaciones.Services.MensajesService;
import org.example.coladenotificaciones.domain.NotificacionRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mensajes")
public class MensajesController {
    private final MensajesService mensajesService;
    public MensajesController(MensajesService mensajesService) {
        this.mensajesService = mensajesService;
    }

    @PostMapping("")
    public ResponseEntity<Void> capturarMensaje(@RequestBody NotificacionRequestDTO mensaje){
        mensajesService.guardarMensaje(mensaje);
        mensajesService.enviarMensajes();
        return ResponseEntity.ok().build();
    }


}
