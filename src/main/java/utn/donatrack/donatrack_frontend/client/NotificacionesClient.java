package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import utn.donatrack.donatrack_frontend.client.dto.ErrorResponse;
import utn.donatrack.donatrack_frontend.client.dto.LoteNotificacionRequest;
import utn.donatrack.donatrack_frontend.client.dto.NotificacionRequest;
import utn.donatrack.donatrack_frontend.client.dto.NotificacionResponse;

@Component
public class NotificacionesClient {

    private static final String BASE = "/api/notificaciones";

    private final RestClient restClient;

    public NotificacionesClient(@Qualifier("notificacionesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    /** GET /api/notificaciones */
    public List<NotificacionResponse> listar() {
        List<NotificacionResponse> res = call(() -> restClient.get()
            .uri(BASE+"")
            .retrieve()
            .body(new ParameterizedTypeReference<List<NotificacionResponse>>() {}));
        return res == null ? List.of() : res;
    }

    /** POST /api/notificaciones */
    public NotificacionResponse enviar(NotificacionRequest request) {
        return call(() -> restClient.post()
            .uri(BASE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(NotificacionResponse.class));
    }

    /** POST /api/notificaciones/lotes */
    public List<NotificacionResponse> enviarLote(List<NotificacionRequest> notificaciones) {
        List<NotificacionResponse> res = call(() -> restClient.post()
            .uri(BASE + "/lotes")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new LoteNotificacionRequest(notificaciones))
            .retrieve()
            .body(new ParameterizedTypeReference<List<NotificacionResponse>>() {}));
        return res == null ? List.of() : res;
    }

    // ---- manejo de errores en un solo lugar ----
    private <T> T call(Supplier<T> operacion) {
        try {
            return operacion.get();
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new PeticionInvalidaException(mensajeDelServicio(e), e);
            }
            throw new ServicioNoDisponibleException(
                "El servicio de notificaciones tuvo un problema (" + e.getStatusCode().value() + "). Probá de nuevo en unos minutos.", e);
        } catch (ResourceAccessException e) {
            throw new ServicioNoDisponibleException(
                "No pudimos comunicarnos con el servicio de notificaciones. Probá de nuevo en unos minutos.", e);
        }
    }

    private String mensajeDelServicio(RestClientResponseException e) {
        try {
            ErrorResponse body = e.getResponseBodyAs(ErrorResponse.class);
            if (body != null && body.error() != null && !body.error().isBlank()) {
                return body.error();
            }
        } catch (RuntimeException ignorada) {
            // el cuerpo no tenia el formato esperado
        }
        return "El servicio rechazó la solicitud (" + e.getStatusCode().value() + ").";
    }
}
