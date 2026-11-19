package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.RankingEntidadBeneficiariaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.SegmentadaRankingsResponseDTO;

import java.util.List;

@Service
public class RankingService extends ClienteServicio {

    public RankingService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<SegmentadaRankingsResponseDTO> obtenerRankings() {
        return listaDe(() -> http().get()
                .uri("/api/rankings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<SegmentadaRankingsResponseDTO>>() {}));
    }

    public void generarRankings() {
        ejecutar(() -> http().post()
                .uri("/api/rankings")
                .retrieve()
                .toBodilessEntity());
    }

    public List<RankingEntidadBeneficiariaResponseDTO> obtenerEntidadesRepetidas(Long idDonacion) {
        String uri = (idDonacion != null) ? "/api/rankings/" + idDonacion : "/api/rankings/1";
        return listaDe(() -> http().get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RankingEntidadBeneficiariaResponseDTO>>() {}));
    }
}