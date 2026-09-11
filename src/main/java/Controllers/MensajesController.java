package Controllers;

import Services.MensajesService;
import domain.MensajeNotificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/mensajes")
public class MensajesController {
    private final MensajesService mensajesService;
    public MensajesController(MensajesService mensajesService) {
        this.mensajesService = mensajesService;
    }

    @PostMapping("")
    public ResponseEntity<Void> capturarMensaje(@RequestBody MensajeNotificacion mensaje){
        mensajesService.guardarMensaje(mensaje);
        mensajesService.enviarMensajes();
        return ResponseEntity.accepted().build();
    }


}
