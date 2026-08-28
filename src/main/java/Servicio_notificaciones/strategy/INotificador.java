package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;

public interface INotificador {

  MedioNotificacion medio();

  void enviar(Notificacion notificacion);

}
