package org.example.broker.comunicador;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Comunicador {
    private final RestClient restClientDonaciones;
    private final RestClient restClientIncentivos;
    private final RestClient restClientLogistica;
    private String urlLogistica;
    private final String urlLogisticaLocal;

    public Comunicador(
            @Qualifier("restClientDonaciones") RestClient restClientDonaciones,
            @Qualifier("restClientIncentivos") RestClient restClientIncentivos,
            @Qualifier("restClientLogistica") RestClient restClientLogistica,
            @Value("${conexiones.servicio-logistica-online}") String urlLogistica,
            @Value("${conexiones.servicio-logistica-local}") String urlLogisticaLocal
    ) {
        this.restClientDonaciones = restClientDonaciones;
        this.restClientIncentivos = restClientIncentivos;
        this.restClientLogistica = restClientLogistica;
        this.urlLogistica = urlLogistica;
        this.urlLogisticaLocal = urlLogisticaLocal;
    }

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
                System.out.println("La ruta finalmente es: " + urlLogistica + " :)");
                return;
            }
        } catch (Exception e){
            // No esta disponible logistica online
        }
        // Pasamos a la local
        this.urlLogistica = urlLogisticaLocal;
        System.out.println("No funciona el despliegue de logistica, url actual: " + urlLogistica + " :(");
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
