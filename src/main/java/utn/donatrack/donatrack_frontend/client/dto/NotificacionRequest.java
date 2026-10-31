package utn.donatrack.donatrack_frontend.client.dto;

/** Espejo del JSON que espera POST /api/notificaciones. El medio viaja como texto. */
public record NotificacionRequest(
    String medioNotificacion,
    String asunto,
    String cuerpo,
    DestinatarioRequest destinatario,
    String eventoOrigen) {}
