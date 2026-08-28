package Servicio_notificaciones.controllers;

import Servicio_notificaciones.DTOs.request.LoteNotificacionRequestDTO;
import Servicio_notificaciones.DTOs.request.NotificacionRequestDTO;
import Servicio_notificaciones.DTOs.response.NotificacionResponse;
import Servicio_notificaciones.dominio.Notificacion;
import Servicio_notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public ResponseEntity<NotificacionResponse> enviar(
            @Valid @RequestBody NotificacionRequestDTO request
    ) {
        return ResponseEntity.ok(
                NotificacionResponse.from(notificacionService.enviar(request))
        );
    }
    @GetMapping("tests/{pos}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable int pos) {
        return ResponseEntity.ok(
                NotificacionResponse.from(notificacionService.obtenerTodas().get(pos))
        );
    }
    @PostMapping("/lotes")
    public ResponseEntity<List<NotificacionResponse>> enviarLote(
            @Valid @RequestBody LoteNotificacionRequestDTO request
    ) {
        List<NotificacionResponse> response = notificacionService.enviarLote(request)
                .stream()
                .map(NotificacionResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/tests")
    public ResponseEntity<List<Notificacion>> obtenerTodasTests() {
        List<Notificacion> response = notificacionService.obtenerTodas()
                .stream().toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> obtenerTodas() {
        List<NotificacionResponse> response = notificacionService.obtenerTodas()
                .stream()
                .map(NotificacionResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(
                NotificacionResponse.from(notificacionService.obtenerPorId(id))
        );
    }

}
