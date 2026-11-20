package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.RankingEntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.SegmentadaRankingsResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public List<SegmentadaRankingsResponseDTO> obtenerRankings() {
        return restClient.get()
                .uri("/api/rankings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<SegmentadaRankingsResponseDTO>>() {});
    }

    public void generarRankings() {
        restClient.post()
                .uri("/api/rankings")
                .retrieve()
                .toBodilessEntity();
    }

    public List<RankingEntidadBeneficiariaResponseDTO> obtenerEntidadesRepetidas(Long idDonacion) {
        String uri = (idDonacion != null) ? "/api/rankings/" + idDonacion : "/api/rankings/1";
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RankingEntidadBeneficiariaResponseDTO>>() {});
    }
}