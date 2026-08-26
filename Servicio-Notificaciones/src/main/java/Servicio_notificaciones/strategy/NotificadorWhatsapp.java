package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificadorWhatsapp implements INotificador {

  @Override
  public MedioNotificacion medio(){
    return MedioNotificacion.WHATSAPP;
  }

  @Override
  public void enviar(Notificacion notificacion){
    System.out.println(
        "[SIMULACION WHATSAPP] Para: " +
            notificacion.getDestinatario().getWhatsapp() +
            " | Mensaje: " +
            notificacion.getCuerpo()
    );
  }

}
