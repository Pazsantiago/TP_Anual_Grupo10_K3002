package org.example.donacionesfrontend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegmentadaRankingsResponseDTO {
    private DonacionSegmentadaResponseDTO segmentada;
    private List<RankingEntidadBeneficiariaResponseDTO> rankings = new ArrayList<>();
}

