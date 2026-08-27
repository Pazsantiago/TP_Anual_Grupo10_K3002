package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgoritmoCompatibilidadSemantica implements IAlgoritmoAsignacion {
    @Override
    public List<RankingEntidadBeneficiaria> rankear(DonacionSegmentada donacion, List<EntidadBeneficiaria> entidades) {
        List<EntidadBeneficiaria> entidadesRankeadas = entidades.stream().filter(e -> e.getNecesidadesActuales().stream().map(n -> n.getSubcategoria().getCategoria()).anyMatch(c -> c.getTipo() ==
                donacion.getSubcategoria().getCategoria().getTipo())).limit(10).toList();
        List<RankingEntidadBeneficiaria> rankings = entidadesRankeadas.stream().map(e -> new RankingEntidadBeneficiaria(e, entidadesRankeadas.indexOf(e), TipoAlgoritmo.COMPATIBILIDAD_SEMANTICA)).toList();
        return rankings;
    }

}