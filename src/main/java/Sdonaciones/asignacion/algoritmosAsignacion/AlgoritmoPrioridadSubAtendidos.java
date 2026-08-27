package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class AlgoritmoPrioridadSubAtendidos implements IAlgoritmoAsignacion {

    @Override
    public List<RankingEntidadBeneficiaria> rankear(DonacionSegmentada donacionList, List<EntidadBeneficiaria> entidades) {
        LocalDate hoy = LocalDate.now();
        LocalDate haceTresMeses = hoy.minusMonths(3);

        List<EntidadBeneficiaria> entidadesOrdenadas = entidades.stream()
                .sorted(Comparator.comparingLong(e -> contarNecesidadesSatisfechasUltimoTrimestre(e, haceTresMeses, hoy)))
                .toList();

        List<RankingEntidadBeneficiaria> rankings = new ArrayList<>();
        for (Integer i = 0; i < entidadesOrdenadas.size() && i < 10; i++) {
            rankings.add(new RankingEntidadBeneficiaria(entidadesOrdenadas.get(i), i, TipoAlgoritmo.PRIORIDAD_SUB_ATENDIDOS));
        }
        return rankings;
    }

    private long contarNecesidadesSatisfechasUltimoTrimestre(EntidadBeneficiaria entidad, LocalDate desde, LocalDate hasta) {
        return entidad.getNecesidadesHistoricas().stream()
                .filter(Necesidad::estaSatisfecha)
                .filter(n -> !n.getSatisfechaEn().isBefore(desde) && !n.getSatisfechaEn().isAfter(hasta))
                .count();
    }
}