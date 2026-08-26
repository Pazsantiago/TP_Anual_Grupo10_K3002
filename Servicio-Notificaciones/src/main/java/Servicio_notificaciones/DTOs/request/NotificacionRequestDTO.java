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

  public String getEventoOrigen() {
    return eventoOrigen;
  }

  public void setMedioNotificacion(MedioNotificacion medioNotificacion) {
    this.medioNotificacion = medioNotificacion;
  }

  public void setCuerpo(String cuerpo) {
    this.cuerpo = cuerpo;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public void setDestinatario(DestinatarioRequestDTO destinatario) {
    this.destinatario = destinatario;
  }

  public void setEventoOrigen(String eventoOrigen) {
    this.eventoOrigen = eventoOrigen;
  }

}
