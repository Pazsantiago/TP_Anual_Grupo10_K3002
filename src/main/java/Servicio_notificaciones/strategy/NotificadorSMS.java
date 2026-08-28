package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificadorSMS implements INotificador{


  @Override
  public MedioNotificacion medio(){
    return MedioNotificacion.SMS;
  }

  @Override
  public void enviar(Notificacion notificacion){
    System.out.println(
        "[SIMULACION SMS] Para: " +
            notificacion.getDestinatario().getTelefono() +
            " | Mensaje: " +
            notificacion.getCuerpo()
    );
  }


}
