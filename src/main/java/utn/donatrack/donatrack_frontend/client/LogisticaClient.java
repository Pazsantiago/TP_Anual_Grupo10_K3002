package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.dto.logistica.BultoResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.CamionResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.DonacionPendienteResponse;

/**
 * Servicio de Logistica: flota, bultos y donaciones pendientes de entrega.
 * Pendiente (cuando el servicio lo exponga): seguimiento de rutas y ubicacion en tiempo real para los mapas.
 */
@Component
public class LogisticaClient extends ClienteServicio {

    public LogisticaClient(@Qualifier("logisticaRestClient") RestClient restClient) {
        super(restClient, "logística");
    }

    /** GET /api/camiones */
    public List<CamionResponse> listarCamiones() {
        return listaDe(() -> http().get()
            .uri("/api/camiones")
            .retrieve()
            .body(new ParameterizedTypeReference<List<CamionResponse>>() {}));
    }

    /** GET /api/bultos/pendientes */
    public List<BultoResponse> listarBultosPendientes() {
        return listaDe(() -> http().get()
            .uri("/api/bultos/pendientes")
            .retrieve()
            .body(new ParameterizedTypeReference<List<BultoResponse>>() {}));
    }

    /** GET /api/donaciones/pendientes */
    public List<DonacionPendienteResponse> listarDonacionesPendientes() {
        return listaDe(() -> http().get()
            .uri("/api/donaciones/pendientes")
            .retrieve()
            .body(new ParameterizedTypeReference<List<DonacionPendienteResponse>>() {}));
    }
}
