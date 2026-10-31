package utn.donatrack.donatrack_frontend.client.dto;

import java.time.LocalDateTime;

/** Espejo de la respuesta del servicio. Estado y medio como texto: si el servicio agrega valores, no se rompe. */
public record NotificacionResponse(
    String medioNotificacion,
    String estadoNotificacion,
    String destinatario,
    String asunto,
    String cuerpo,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaEnvio) {}
