package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.CrearPerfilRequest;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.InsigniaResponse;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.MisionResponse;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.PerfilMetricasResponse;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.PosicionRankingResponse;

/**
 * Servicio de Incentivos: metricas, mision actual, insignias y ranking mensual.
 * Ojo: este servicio responde sus 404 con texto plano ("Perfil no encontrado"), no con JSON;
 * ClienteServicio ya lo contempla y lanza RecursoNoEncontradoException.
 */
@Component
public class IncentivosClient extends ClienteServicio {

    private static final String BASE = "/incentivos";

    public IncentivosClient(@Qualifier("incentivosRestClient") RestClient restClient) {
        super(restClient, "incentivos");
    }

    /** GET /incentivos/{donanteId}/metricas (404 si el donante no tiene perfil) */
    public PerfilMetricasResponse metricas(long donanteId) {
        return call(() -> http().get()
            .uri(BASE + "/{id}/metricas", donanteId)
            .retrieve()
            .body(PerfilMetricasResponse.class));
    }

    /** GET /incentivos/{donanteId}/misiones: la mision actual con su progreso (404 si no tiene) */
    public MisionResponse misionActual(long donanteId) {
        return call(() -> http().get()
            .uri(BASE + "/{id}/misiones", donanteId)
            .retrieve()
            .body(MisionResponse.class));
    }

    /** GET /incentivos/{donanteId}/insignias */
    public List<InsigniaResponse> insignias(long donanteId) {
        return listaDe(() -> http().get()
            .uri(BASE + "/{id}/insignias", donanteId)
            .retrieve()
            .body(new ParameterizedTypeReference<List<InsigniaResponse>>() {}));
    }

    /** GET /incentivos/ranking-mensual */
    public List<PosicionRankingResponse> rankingMensual() {
        return listaDe(() -> http().get()
            .uri(BASE + "/ranking-mensual")
            .retrieve()
            .body(new ParameterizedTypeReference<List<PosicionRankingResponse>>() {}));
    }

    /** POST /incentivos/perfiles (409 si el donante ya tiene perfil) */
    public String crearPerfil(long donanteId) {
        return call(() -> http().post()
            .uri(BASE + "/perfiles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(new CrearPerfilRequest(donanteId))
            .retrieve()
            .body(String.class));
    }

    /** POST /incentivos/{donanteId}/nueva-mision: asigna una mision de refuerzo (404 si no hay perfil) */
    public void asignarMisionRefuerzo(long donanteId) {
        ejecutar(() -> http().post()
            .uri(BASE + "/{id}/nueva-mision", donanteId)
            .retrieve()
            .toBodilessEntity());
    }

    /** DELETE /incentivos/perfiles/{donanteId} (404 si el donante no tiene perfil) */
    public void eliminarPerfil(long donanteId) {
        ejecutar(() -> http().delete()
            .uri(BASE + "/perfiles/{id}", donanteId)
            .retrieve()
            .toBodilessEntity());
    }
}
