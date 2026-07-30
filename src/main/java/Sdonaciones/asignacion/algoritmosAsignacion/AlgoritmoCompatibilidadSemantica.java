package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public class AlgoritmoCompatibilidadSemantica implements IAlgoritmoAsignacion {
    @Override
    public List<RankingEntidadBeneficiaria> rankear(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion) {
        List<EntidadBeneficiaria> entidadesRankeadas = entidades.stream().filter(e -> e.getNecesidades().stream().map(n -> n.getSubcategoria().getCategoria()).anyMatch(c -> c == donacion.getSubcategoria().getCategoria())).limit(10).toList();
        List<RankingEntidadBeneficiaria> rankings = entidadesRankeadas.stream().map(e -> new RankingEntidadBeneficiaria(e, entidadesRankeadas.indexOf(e))).toList();
        return rankings;
    }

}