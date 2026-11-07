package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.dto.LoteNotificacionRequest;
import utn.donatrack.donatrack_frontend.client.dto.NotificacionRequest;
import utn.donatrack.donatrack_frontend.client.dto.NotificacionResponse;

/** Servicio de Notificaciones. El manejo de errores vive en ClienteServicio. */
@Component
public class NotificacionesClient extends ClienteServicio {

    private static final String BASE = "/api/notificaciones";

    public NotificacionesClient(@Qualifier("notificacionesRestClient") RestClient restClient) {
        super(restClient, "notificaciones");
    }

    /** GET /api/notificaciones */
    public List<NotificacionResponse> listar() {
        return listaDe(() -> http().get()
            .uri(BASE)
            .retrieve()
            .body(new ParameterizedTypeReference<List<NotificacionResponse>>() {}));
    }

    /** POST /api/notificaciones */
    public NotificacionResponse enviar(NotificacionRequest request) {
        return call(() -> http().post()
            .uri(BASE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(NotificacionResponse.class));
    }

    /** POST /api/notificaciones/lotes */
    public List<NotificacionResponse> enviarLote(List<NotificacionRequest> notificaciones) {
        return listaDe(() -> http().post()
            .uri(BASE + "/lotes")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new LoteNotificacionRequest(notificaciones))
            .retrieve()
            .body(new ParameterizedTypeReference<List<NotificacionResponse>>() {}));
    }
}
