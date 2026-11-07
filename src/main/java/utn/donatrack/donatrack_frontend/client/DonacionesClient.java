package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonacionResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonanteResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EntidadResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.NecesidadResponse;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.RankingSegmentadaResponse;

/**
 * Servicio de Donaciones: donantes, donaciones, entidades beneficiarias, necesidades y rankings de asignacion.
 * Hoy solo expone lecturas (y ejecutar los algoritmos). Las altas/modificaciones piden en el servicio el JSON
 * completo del dominio (Donante, Donacion, EntidadBeneficiaria, Necesidad): se suman cuando haya formularios.
 */
@Component
public class DonacionesClient extends ClienteServicio {

    private static final String DONANTES = "/api/donantes";
    private static final String ENTIDADES = "/api/entidadesBeneficiarias";
    private static final String RANKINGS = "/api/rankings";

    public DonacionesClient(@Qualifier("donacionesRestClient") RestClient restClient) {
        super(restClient, "donaciones");
    }

    /** GET /api/donantes */
    public List<DonanteResponse> listarDonantes() {
        return listaDe(() -> http().get()
            .uri(DONANTES)
            .retrieve()
            .body(new ParameterizedTypeReference<List<DonanteResponse>>() {}));
    }

    /** GET /api/donantes/{id} */
    public DonanteResponse obtenerDonante(long id) {
        return call(() -> http().get()
            .uri(DONANTES + "/{id}", id)
            .retrieve()
            .body(DonanteResponse.class));
    }

    /** GET /api/donantes/{id}/donaciones */
    public List<DonacionResponse> listarDonaciones(long idDonante) {
        return listaDe(() -> http().get()
            .uri(DONANTES + "/{id}/donaciones", idDonante)
            .retrieve()
            .body(new ParameterizedTypeReference<List<DonacionResponse>>() {}));
    }

    /** GET /api/entidadesBeneficiarias */
    public List<EntidadResponse> listarEntidades() {
        return listaDe(() -> http().get()
            .uri(ENTIDADES)
            .retrieve()
            .body(new ParameterizedTypeReference<List<EntidadResponse>>() {}));
    }

    /** GET /api/entidadesBeneficiarias/{id} */
    public EntidadResponse obtenerEntidad(long id) {
        return call(() -> http().get()
            .uri(ENTIDADES + "/{id}", id)
            .retrieve()
            .body(EntidadResponse.class));
    }

    /** GET /api/entidadesBeneficiarias/{id}/necesidades */
    public List<NecesidadResponse> listarNecesidades(long idEntidad) {
        return listaDe(() -> http().get()
            .uri(ENTIDADES + "/{id}/necesidades", idEntidad)
            .retrieve()
            .body(new ParameterizedTypeReference<List<NecesidadResponse>>() {}));
    }

    /** GET /api/rankings */
    public List<RankingSegmentadaResponse> listarRankings() {
        return listaDe(() -> http().get()
            .uri(RANKINGS)
            .retrieve()
            .body(new ParameterizedTypeReference<List<RankingSegmentadaResponse>>() {}));
    }

    /** POST /api/rankings: ejecuta a demanda los algoritmos de asignacion. Devuelve el mensaje del servicio. */
    public String generarRankings() {
        return call(() -> http().post()
            .uri(RANKINGS)
            .retrieve()
            .body(String.class));
    }
}
