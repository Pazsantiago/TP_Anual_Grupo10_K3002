package org.example.broker.comunicador;


import lombok.RequiredArgsConstructor;
import org.example.broker.dominio.donacion.DonacionAsignadaDTO;
import org.example.broker.dominio.mensajes.MensajeConfirmacionEntrega;
import org.example.broker.dominio.mensajes.MensajeEntregaFallida;
import org.example.broker.dominio.mensajes.MensajeInicioDeRuta;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class Comunicador {
    @Qualifier("restClientDonaciones")
    private final RestClient restClientDonaciones;
    @Qualifier("restClientIncentivos")
    private final RestClient restClientIncentivos;
    @Qualifier("restClientLogistica")
    private final RestClient restClientLogistica;
    @Value("${conexiones.servicio-logistica-online}")
    private String urlLogistica;
    @Value("${conexiones.servicio-logistica-local}")
    private String urlLogisticaLocal;

    @EventListener(ApplicationReadyEvent.class)
    public void iniciar() {
        verificarConexionConLogisticaEnNube();
    }


    public void verificarConexionConLogisticaEnNube(){
        try {
            ResponseEntity<Void> response = this.restClientLogistica
                    .post()
                    .uri(urlLogistica + "/healthcheck")
                    .retrieve()
                    .toBodilessEntity();
            if (response.getStatusCode().value() == 200) {
                return;
            }
        } catch (Exception e){
            // No esta disponible logistica online
        }
        // Pasamos a la local
        this.urlLogistica = urlLogisticaLocal;
    }

    public void enviarDonacionAsignada(DonacionAsignadaDTO donacionAsignada) {
        Integer result = this.restClientLogistica
                .post()
                .uri("/donacionesAsignadas")
                .body(donacionAsignada)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void enviarInicioDeRuta(MensajeInicioDeRuta mensaje) {
        Integer result = this.restClientDonaciones
                .post()
                .uri("/iniciosDeRutas")
                .body(mensaje)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void enviarEntregaRealizada(MensajeConfirmacionEntrega mensaje) {
        Integer result = this.restClientDonaciones
                .post()
                .uri("/entregasConfirmadas")
                .body(mensaje)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void enviarEntregaFallida(MensajeEntregaFallida mensaje) {
        Integer result = this.restClientDonaciones
                .post()
                .uri("/entregasFallidas")
                .body(mensaje)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
