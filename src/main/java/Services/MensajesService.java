package Services;

import Repository.ColaDeMensajes;
import domain.MensajeNotificacion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MensajesService {
    private final ColaDeMensajes cola_de_mensajes;
    private final RestClient restClientNotificaciones;

    public MensajesService(ColaDeMensajes cola_de_mensajes, @Qualifier("restClientNotificaciones") RestClient restClientNotificaciones) {
        this.cola_de_mensajes = cola_de_mensajes;
        this.restClientNotificaciones = restClientNotificaciones;
    }


    public Integer guardarMensaje(MensajeNotificacion mensaje){
        cola_de_mensajes.agregarMensaje(mensaje);
        return 200;
    }

    public MensajeNotificacion obtenerPrimerMensaje(){
        return cola_de_mensajes.obtenerUltimoMensajeSinEliminarlo();
    }

    public MensajeNotificacion quitarPrimerMensaje(){
        return cola_de_mensajes.eliminarMensajeDeLaCola();
    }

    @Scheduled(fixedDelay = 30000) //30 segundos
    public void reintentarMensajes() {
        enviarMensajes();
    }

    public void enviarMensajes(){
        while(cola_de_mensajes.listarCantidadMensajes() > 0) {
            MensajeNotificacion mensajePorEnviar = quitarPrimerMensaje();
            try {
                restClientNotificaciones
                        .post()
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
