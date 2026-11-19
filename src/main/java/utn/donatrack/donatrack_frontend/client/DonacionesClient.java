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

@Component
public class DonacionesClient extends ClienteServicio {

    private static final String DONANTES = "/api/donantes";
    private static final String ENTIDADES = "/api/entidadesBeneficiarias";
    private static final String RANKINGS = "/api/rankings";

    public DonacionesClient(@Qualifier("donacionesRestClient") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<DonanteResponse> listarDonantes() {
        return listaDe(() -> http().get()
                .uri(DONANTES)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonanteResponse>>() {}));
    }

    public DonanteResponse obtenerDonante(long id) {
        return call(() -> http().get()
                .uri(DONANTES + "/{id}", id)
                .retrieve()
                .body(DonanteResponse.class));
    }

    public List<DonacionResponse> listarDonaciones(long idDonante) {
        return listaDe(() -> http().get()
                .uri(DONANTES + "/{id}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionResponse>>() {}));
    }

    public List<EntidadResponse> listarEntidades() {
        return listaDe(() -> http().get()
                .uri(ENTIDADES)
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadResponse>>() {}));
    }

    public EntidadResponse obtenerEntidad(long id) {
        return call(() -> http().get()
                .uri(ENTIDADES + "/{id}", id)
                .retrieve()
                .body(EntidadResponse.class));
    }

    public List<NecesidadResponse> listarNecesidades(long idEntidad) {
        return listaDe(() -> http().get()
                .uri(ENTIDADES + "/{id}/necesidades", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NecesidadResponse>>() {}));
    }

    public List<RankingSegmentadaResponse> listarRankings() {
        return listaDe(() -> http().get()
                .uri(RANKINGS)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RankingSegmentadaResponse>>() {}));
    }

    public String generarRankings() {
        return call(() -> http().post()
                .uri(RANKINGS)
                .retrieve()
                .body(String.class));
    }

    public void crearDonante(DonanteRequest request) {
        ejecutar(() -> http().post().uri(DONANTES)
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void crearDonanteByAdmin(DonanteRequest request) {
        ejecutar(() -> http().post().uri(DONANTES + "/admin")
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public ImportacionResponse importarDonantes(MultipartFile archivo) {
        MultiValueMap<String, Object> partes = new LinkedMultiValueMap<>();
        partes.add("archivo", parteMultipart(archivo));
        return call(() -> http().post().uri(DONANTES + "/importador")
                .contentType(MediaType.MULTIPART_FORM_DATA).body(partes).retrieve().body(ImportacionResponse.class));
    }

    public void crearDonacion(long idDonante, DonacionRequest request) {
        ejecutar(() -> http().post().uri(DONANTES + "/{idDonante}/donaciones", idDonante)
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void cambiarEstadoDonacion(long idDonante, long idDonacion, long idSegmentada, EstadoDonacionRequest request) {
        ejecutar(() -> http().patch().uri(DONANTES + "/{idDonante}/donaciones/{idDonacion}/segmentadas/{idSegmentada}", idDonante, idDonacion, idSegmentada)
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void crearEntidad(EntidadRequest request) {
        ejecutar(() -> http().post().uri(ENTIDADES)
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void crearNecesidad(long idEntidad, NecesidadRequest request) {
        ejecutar(() -> http().post().uri(ENTIDADES + "/{id}/necesidades", idEntidad)
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void confirmarAsignacion(long idEntidad, long idNecesidad, long idSegmentada) {
        ejecutar(() -> http().post().uri(ENTIDADES + "/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}", idEntidad, idNecesidad, idSegmentada)
                .contentType(MediaType.APPLICATION_JSON).retrieve().toBodilessEntity());
    }

    public void eliminarDonante(long id) {
        ejecutar(() -> http().delete().uri(DONANTES + "/{id}", id).retrieve().toBodilessEntity());
    }

    public void eliminarEntidad(long id) {
        ejecutar(() -> http().delete().uri(ENTIDADES + "/{id}", id).retrieve().toBodilessEntity());
    }

    public void eliminarNecesidad(long idEntidad, long idNecesidad) {
        ejecutar(() -> http().delete().uri(ENTIDADES + "/{idEntidad}/necesidades/{idNecesidad}", idEntidad, idNecesidad).retrieve().toBodilessEntity());
    }

    public void eliminarDonacion(long idDonante, long idDonacion) {
        ejecutar(() -> http().delete().uri(DONANTES + "/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion).retrieve().toBodilessEntity());
    }

    public List<DonacionSegmentadaResponseDTO> listarDonacionesSegmentadas(long idDonante) {
        return listaDe(() -> http().get()
                .uri(DONANTES + "/{idDonante}/donaciones/segmentadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionSegmentadaResponseDTO>>() {}));
    }

    public List<DonacionAsignadaResponseDTO> listarDonacionesAsignadasPorEntidad(long idEntidad) {
        return listaDe(() -> http().get()
                .uri(ENTIDADES + "/{idEntidad}/donaciones-asignadas", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionAsignadaResponseDTO>>() {}));
    }

    public void agregarSegmentadaAAsignada(long idAsignada, long idSegmentada) {
        ejecutar(() -> http().post()
                .uri(ENTIDADES + "/donacionesAsignadas/{idAsignada}/donaciones/{idSegmentada}", idAsignada, idSegmentada)
                .retrieve().toBodilessEntity());
    }

    public List<RankingEntidadResponse> obtenerEntidadesRepetidas(long idDonacion) {
        return listaDe(() -> http().get()
                .uri(RANKINGS + "/{idDonacion}", idDonacion)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RankingEntidadResponse>>() {}));
    }
}