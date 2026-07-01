package Servicio_notificaciones.DTOs.request;

import Servicio_notificaciones.dominio.MedioNotificacion;

public class NotificacionRequestDTO {

  MedioNotificacion medioNotificacion;
  String cuerpo;
  String asunto;
  DestinatarioRequestDTO destinatario;
  String eventoOrigen;

  public String getAsunto() {
    return asunto;
  }

  public MedioNotificacion getMedioNotificacion() {
    return medioNotificacion;
  }

  public String getCuerpo() {
    return cuerpo;
  }

  public DestinatarioRequestDTO getDestinatario() {
    return destinatario;
  }

}
