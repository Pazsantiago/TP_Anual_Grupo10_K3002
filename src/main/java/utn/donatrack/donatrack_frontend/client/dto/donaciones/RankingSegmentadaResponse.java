package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/** Ranking de entidades candidatas para una donacion segmentada. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RankingSegmentadaResponse(Long idSegmentada, List<RankingEntidadResponse> ranking) {
    public RankingSegmentadaResponse {
        if (ranking == null) ranking = List.of();
    }
}
