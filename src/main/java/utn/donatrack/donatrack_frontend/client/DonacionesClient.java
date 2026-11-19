package utn.donatrack.donatrack_frontend.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.*;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.*;

import java.util.List;

@Component
public class DonacionesClient extends ClienteServicio {

    public DonacionesClient(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    // DONANTES
    public List<DonanteResponseDTO> listarDonantes() {
        return listaDe(() -> http().get().uri("/api/donantes").retrieve().body(new ParameterizedTypeReference<List<DonanteResponseDTO>>() {}));
    }

    public DonanteResponseDTO obtenerDonante(long id) {
        return call(() -> http().get().uri("/api/donantes/{id}", id).retrieve().body(DonanteResponseDTO.class));
    }

    public void crearDonante(DonanteRequestDTO request) {
        ejecutar(() -> http().post().uri("/api/donantes").contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void crearDonanteByAdmin(DonanteRequestDTO request) {
        ejecutar(() -> http().post().uri("/api/donantes/admin").contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void eliminarDonante(long id) {
        ejecutar(() -> http().delete().uri("/api/donantes/{id}", id).retrieve().toBodilessEntity());
    }

    public void importarDonantes(MultiValueMap<String, Object> partes) {
        ejecutar(() -> http().post().uri("/api/donantes/importador").contentType(MediaType.MULTIPART_FORM_DATA).body(partes).retrieve().toBodilessEntity());
    }

    // DONACIONES
    public List<DonacionResponseDTO> listarDonaciones(long idDonante) {
        return listaDe(() -> http().get().uri("/api/donantes/{idDonante}/donaciones", idDonante).retrieve().body(new ParameterizedTypeReference<List<DonacionResponseDTO>>() {}));
    }

    public List<DonacionSegmentadaResponseDTO> listarDonacionesSegmentadas(long idDonante) {
        return listaDe(() -> http().get().uri("/api/donantes/{idDonante}/donaciones/segmentadas", idDonante).retrieve().body(new ParameterizedTypeReference<List<DonacionSegmentadaResponseDTO>>() {}));
    }

    public void crearDonacion(long idDonante, DonacionRequestDTO request) {
        ejecutar(() -> http().post().uri("/api/donantes/{idDonante}/donaciones", idDonante).contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void cambiarEstadoDonacion(long idDonante, long idDonacion, long idSegmentada, EstadoDonacionRequestDTO request) {
        ejecutar(() -> http().patch().uri("/api/donantes/{idDonante}/donaciones/{idDonacion}/segmentadas/{idSegmentada}", idDonante, idDonacion, idSegmentada).contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void eliminarDonacion(long idDonante, long idDonacion) {
        ejecutar(() -> http().delete().uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion).retrieve().toBodilessEntity());
    }

    // ENTIDADES BENEFICIARIAS
    public List<EntidadBeneficiariaResponseDTO> listarEntidades() {
        return listaDe(() -> http().get().uri("/api/entidadesBeneficiarias").retrieve().body(new ParameterizedTypeReference<List<EntidadBeneficiariaResponseDTO>>() {}));
    }

    public EntidadBeneficiariaResponseDTO obtenerEntidad(long id) {
        return call(() -> http().get().uri("/api/entidadesBeneficiarias/{id}", id).retrieve().body(EntidadBeneficiariaResponseDTO.class));
    }

    public void crearEntidad(EntidadBeneficiariaRequestDTO request) {
        ejecutar(() -> http().post().uri("/api/entidadesBeneficiarias").contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void eliminarEntidad(long id) {
        ejecutar(() -> http().delete().uri("/api/entidadesBeneficiarias/{id}", id).retrieve().toBodilessEntity());
    }

    public List<DonacionAsignadaResponseDTO> listarDonacionesAsignadasPorEntidad(long idEntidad) {
        return listaDe(() -> http().get().uri("/api/entidadesBeneficiarias/{idEntidad}/donaciones-asignadas", idEntidad).retrieve().body(new ParameterizedTypeReference<List<DonacionAsignadaResponseDTO>>() {}));
    }

    // NECESIDADES
    public List<NecesidadResponseDTO> listarNecesidades(long idEntidad) {
        return listaDe(() -> http().get().uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad).retrieve().body(new ParameterizedTypeReference<List<NecesidadResponseDTO>>() {}));
    }

    public void crearNecesidad(long idEntidad, NecesidadRequestDTO request) {
        ejecutar(() -> http().post().uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad).contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    public void eliminarNecesidad(long idEntidad, long idNecesidad) {
        ejecutar(() -> http().delete().uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}", idEntidad, idNecesidad).retrieve().toBodilessEntity());
    }

    // ASIGNACIONES Y RANKINGS
    public void asignarDonacionANecesidad(long idEntidad, long idNecesidad, long idSegmentada) {
        ejecutar(() -> http().post().uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}", idEntidad, idNecesidad, idSegmentada).retrieve().toBodilessEntity());
    }

    public void agregarSegmentadaAAsignada(long idAsignada, long idSegmentada) {
        ejecutar(() -> http().post().uri("/api/donacionesAsignadas/{idAsignada}/segmentadas/{idSegmentada}", idAsignada, idSegmentada).retrieve().toBodilessEntity());
    }

    public List<SegmentadaRankingsResponseDTO> listarRankings() {
        return listaDe(() -> http().get().uri("/api/rankings").retrieve().body(new ParameterizedTypeReference<List<SegmentadaRankingsResponseDTO>>() {}));
    }

    public void generarRankings() {
        ejecutar(() -> http().post().uri("/api/rankings").retrieve().toBodilessEntity());
    }

    public List<RankingEntidadBeneficiariaResponseDTO> obtenerEntidadesRepetidas(long idDonacion) {
        String uri = (idDonacion != 0) ? "/api/rankings/" + idDonacion : "/api/rankings/1";
        return listaDe(() -> http().get().uri(uri).retrieve().body(new ParameterizedTypeReference<List<RankingEntidadBeneficiariaResponseDTO>>() {}));
    }
}