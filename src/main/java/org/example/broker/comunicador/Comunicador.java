package org.example.broker.comunicador;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.broker.dominio.donacion.*;
import org.example.broker.dominio.mensajes.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@Data
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
                    .get()
                    .uri(urlLogistica + "/api/healthCheck")
                    .retrieve()
                    .toBodilessEntity();
            if (response.getStatusCode().value() == 200) {
                System.out.println("La ruta finalmente es: " + urlLogistica);
                return;
            }
        } catch (Exception e){
            // No esta disponible logistica online
        }
        // Pasamos a la local
        this.urlLogistica = urlLogisticaLocal;
        System.out.println("No funciona el despliegue de logistica, url actual: " + urlLogistica);
    }

    public void enviarDonacionAsignada(DonacionAsignadaDTO donacionAsignada) {
        ResponseEntity<Void> result = this.restClientLogistica
                .post()
                .uri(this.urlLogistica + "/api/entregas/asignadas")
                .body(donacionAsignada)
                .retrieve()
                .toBodilessEntity();
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarInicioDeRuta(MensajeInicioDeRuta mensaje) {
        ResponseEntity<Void> result = this.restClientDonaciones
                .post()
                .uri("/iniciosDeRutas")
                .body(mensaje)
                .retrieve()
                .toBodilessEntity();
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarEntregaRealizada(MensajeConfirmacionEntrega mensaje) {
        ResponseEntity<Void> result = this.restClientDonaciones
                .post()
                .uri("/entregasConfirmadas")
                .body(mensaje)
                .retrieve()
                .toBodilessEntity();
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void comunicarEntregaIncentivos(PaqueteEntregado mensaje) {
        ResponseEntity<Void> result = this.restClientIncentivos
                .post()
                .uri("/entregasConfirmadas")
                .body(mensaje)
                .retrieve()
                .toBodilessEntity();
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarEntregaFallida(MensajeEntregaFallida mensaje) {
        ResponseEntity<Void> result = this.restClientDonaciones
                .post()
                .uri("/entregasFallidas")
                .body(mensaje)
                .retrieve()
                .toBodilessEntity();
        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
