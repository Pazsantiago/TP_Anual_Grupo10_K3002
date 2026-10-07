package com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SegmentadaRankings {
    private DonacionSegmentada segmentada;
    private List<RankingEntidadBeneficiaria> rankings;

    public SegmentadaRankings(DonacionSegmentada segmentada, List<RankingEntidadBeneficiaria> rankings) {
        this.segmentada = segmentada;
        this.rankings = rankings;
    }
}

