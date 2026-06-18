package Servicio_donaciones.asignacion.algoritmosAsignacion;

import Servicio_donaciones.dominio.donacion.DonacionSegmentada;
import Servicio_donaciones.dominio.entidad.EntidadBeneficiaria;
import Servicio_donaciones.dominio.necesidad.Necesidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgoritmoPrioridadSubAtendidos implements IAlgoritmoAsignacion {

    @Override
    public List<Ranking_Entidad_Beneficiaria> rankEntidad(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion) {
        LocalDate hoy = LocalDate.now();
        LocalDate haceTresMeses = hoy.minusMonths(3);

        List<EntidadBeneficiaria> entidadesOrdenadas = entidades.stream()
                .sorted(Comparator.comparingLong(e -> contarNecesidadesSatisfechasUltimoTrimestre(e, haceTresMeses, hoy)))
                .toList();

        List<Ranking_Entidad_Beneficiaria> rankings = new ArrayList<>();
        for (int i = 0; i < entidadesOrdenadas.size() && i < 10; i++) {
            rankings.add(new Ranking_Entidad_Beneficiaria(entidadesOrdenadas.get(i), i));
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