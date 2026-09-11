package Repository;

import domain.MensajeNotificacion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Repository
public class ColaDeMensajes {
    private Queue<MensajeNotificacion> cola_de_mensajes = new LinkedList<>();

    public Integer listarCantidadMensajes(){
        return cola_de_mensajes.size();
    }

    public void agregarMensaje(MensajeNotificacion mensaje){
        cola_de_mensajes.offer(mensaje);
    }

    public MensajeNotificacion obtenerUltimoMensajeSinEliminarlo(){
        return this.cola_de_mensajes.peek();
    }

    public MensajeNotificacion eliminarMensajeDeLaCola(){
        return this.cola_de_mensajes.poll();
    }
}
