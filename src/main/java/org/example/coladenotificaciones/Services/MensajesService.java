package org.example.coladenotificaciones.Services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.coladenotificaciones.Repository.ColaDeMensajes;
import org.example.coladenotificaciones.domain.NotificacionRequestDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MensajesService {
    private final ColaDeMensajes cola_de_mensajes;
    @Qualifier("restClientNotificaciones")
    private final RestClient restClientNotificaciones;

    public Integer guardarMensaje(NotificacionRequestDTO mensaje){
        cola_de_mensajes.agregarMensaje(mensaje);
        return 200;
    }

    public NotificacionRequestDTO obtenerPrimerMensaje(){
        return cola_de_mensajes.obtenerUltimoMensajeSinEliminarlo();
    }

    @Scheduled(fixedDelay = 30000) //30 segundos
    public void reintentarMensajes() {
        enviarMensajes();
    }



    public void enviarMensajes(){
        while(cola_de_mensajes.listarCantidadMensajes() > 0) {
            NotificacionRequestDTO mensajePorEnviar = cola_de_mensajes.eliminarMensajeDeLaCola();
            try {
                restClientNotificaciones
                        .post()
                        .uri("/api/notificaciones")
                        .body(mensajePorEnviar)
                        .retrieve()
                        .toBodilessEntity();

            } catch (Exception ex) {
                guardarMensaje(mensajePorEnviar);
                break;
            }

        }
    }
}
