package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmail implements INotificador {

  private final JavaMailSender mailSender;

  public NotificadorEmail(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public MedioNotificacion medio(){
    return MedioNotificacion.EMAIL;
  }

  @Override
  public void enviar(Notificacion notificacion){

    SimpleMailMessage mensaje = new SimpleMailMessage();

    mensaje.setTo(notificacion.getDestinatario().getEmail());
    mensaje.setSubject(notificacion.getAsunto());
    mensaje.setText(notificacion.getCuerpo());

    mailSender.send(mensaje);
  }

}
