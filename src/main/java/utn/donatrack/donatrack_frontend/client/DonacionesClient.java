package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.*;
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
 * Lecturas, altas, cambios de estado y asignacion final. Los cuerpos de las altas son los *Request de client.dto.donaciones:
 * si el servicio usa otros nombres de campo, se ajustan ahi (un solo lugar).
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

    /** POST /api/donantes */
    public void crearDonante(DonanteRequest request) {
        ejecutar(() -> http().post().uri(DONANTES)
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** POST /api/donantes/importar (multipart, parte "archivo"): alta/actualizacion masiva desde CSV. */
    public ImportacionResponse importarDonantes(MultipartFile archivo) {
        MultiValueMap<String, Object> partes = new LinkedMultiValueMap<>();
        partes.add("archivo", parteMultipart(archivo));
        return call(() -> http().post().uri(DONANTES + "/importar")
            .contentType(MediaType.MULTIPART_FORM_DATA).body(partes).retrieve().body(ImportacionResponse.class));
    }

    /** POST /api/donaciones: carga unica de bienes (el servicio genera las donaciones segmentadas). */
    public void crearDonacion(DonacionRequest request) {
        ejecutar(() -> http().post().uri("/api/donaciones")
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** PATCH /api/donaciones/{id}/estado */
    public void cambiarEstadoDonacion(long idDonacion, EstadoDonacionRequest request) {
        ejecutar(() -> http().patch().uri("/api/donaciones/{id}/estado", idDonacion)
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** POST /api/entidadesBeneficiarias */
    public void crearEntidad(EntidadRequest request) {
        ejecutar(() -> http().post().uri(ENTIDADES)
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** POST /api/entidadesBeneficiarias/{id}/necesidades */
    public void crearNecesidad(long idEntidad, NecesidadRequest request) {
        ejecutar(() -> http().post().uri(ENTIDADES + "/{id}/necesidades", idEntidad)
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** POST /api/rankings/{idSegmentada}/asignacion: confirma la entidad final de una donacion segmentada. */
    public void confirmarAsignacion(long idSegmentada, long idEntidad) {
        ejecutar(() -> http().post().uri(RANKINGS + "/{id}/asignacion", idSegmentada)
            .contentType(MediaType.APPLICATION_JSON).body(new AsignacionRequest(idEntidad)).retrieve().toBodilessEntity());
    }
}
