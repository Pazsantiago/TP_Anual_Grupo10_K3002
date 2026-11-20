package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.RankingEntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.SegmentadaRankingsResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RestClient donacionesRestClient;

    public List<SegmentadaRankingsResponseDTO> obtenerRankings() {
        return donacionesRestClient.get()
                .uri("/api/rankings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<SegmentadaRankingsResponseDTO>>() {});
    }

    public void generarRankings() {
        donacionesRestClient.post()
                .uri("/api/rankings")
                .retrieve()
                .toBodilessEntity();
    }

    public List<RankingEntidadBeneficiariaResponseDTO> obtenerEntidadesRepetidas(Long idDonacion) {
        String uri = (idDonacion != null) ? "/api/rankings/" + idDonacion : "/api/rankings/1";
        return donacionesRestClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RankingEntidadBeneficiariaResponseDTO>>() {});
    }
}