package Servicio_notificaciones.DTOs.response;

import Servicio_notificaciones.dominio.EstadoNotificacion;
import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import java.time.LocalDateTime;

public class NotificacionResponse {

  MedioNotificacion medioNotificacion;
  EstadoNotificacion estadoNotificacion;
  String destinatario;
  String asunto;
  String cuerpo;
  LocalDateTime fechaCreacion;
  LocalDateTime fechaEnvio;

  public NotificacionResponse(
      MedioNotificacion medioNotificacion,
      EstadoNotificacion estadoNotificacion,
      String destinatario,
      String asunto,
      String cuerpo,
      LocalDateTime fechaCreacion,
      LocalDateTime fechaEnvio
  ) {
    this.medioNotificacion = medioNotificacion;
    this.estadoNotificacion = estadoNotificacion;
    this.destinatario = destinatario;
    this.asunto = asunto;
    this.cuerpo = cuerpo;
    this.fechaCreacion = fechaCreacion;
    this.fechaEnvio = fechaEnvio;
  }

  public static NotificacionResponse from(Notificacion notificacion) {
    return new NotificacionResponse(
        notificacion.getMedioNotificacion(),
        notificacion.getEstadoNotificacion(),
        notificacion.getDestinatario().getNombre(),
        notificacion.getAsunto(),
        notificacion.getCuerpo(),
        notificacion.getFechaCreacion(),
        notificacion.getFechaEnvio()
    );

  }

}
