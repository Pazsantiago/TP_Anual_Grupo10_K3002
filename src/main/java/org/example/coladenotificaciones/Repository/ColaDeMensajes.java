package org.example.coladenotificaciones.Repository;

import org.example.coladenotificaciones.domain.NotificacionRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;

@Repository
public class ColaDeMensajes {
    private Queue<NotificacionRequestDTO> cola_de_mensajes = new LinkedList<>();

    public Integer listarCantidadMensajes(){
        return cola_de_mensajes.size();
    }

    public void agregarMensaje(NotificacionRequestDTO mensaje){
        cola_de_mensajes.offer(mensaje);
    }

    public NotificacionRequestDTO obtenerUltimoMensajeSinEliminarlo(){
        return this.cola_de_mensajes.peek();
    }

    public NotificacionRequestDTO eliminarMensajeDeLaCola(){
        return this.cola_de_mensajes.poll();
    }
}
