package org.example.donacionesfrontend.dominio.algoritmosAsignacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.donacionesfrontend.dominio.donacion.DonacionSegmentada;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SegmentadaRankings {
    private DonacionSegmentada segmentada;
    private List<RankingEntidadBeneficiaria> rankings = new ArrayList<>();

    public SegmentadaRankings(DonacionSegmentada segmentada, List<RankingEntidadBeneficiaria> rankings) {
        this.segmentada = segmentada;
        this.rankings = rankings;
    }
}

