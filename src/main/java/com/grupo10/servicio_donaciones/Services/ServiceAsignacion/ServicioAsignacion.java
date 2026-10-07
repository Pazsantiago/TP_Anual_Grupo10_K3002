package com.grupo10.servicio_donaciones.Services.ServiceAsignacion;

import com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion.SegmentadaRankings;
import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
@Transactional(readOnly = true)
public class ServicioAsignacion {
    private final ServicioBeneficiarias servicioBeneficiarias;
    private final ServicioDonacion servicioDonacion;
    private final List<IAlgoritmoAsignacion> algoritmos;
    private List<SegmentadaRankings> rankings = new ArrayList<>();

    public List<SegmentadaRankings> obtenerRankings() {
        return rankings;
    }

    @Transactional
    @Scheduled(cron = "${horario-baja-carga.cron}")
    public void generarRanking() {
        if (!this.rankings.isEmpty()) {
            this.rankings.clear();
        }
        servicioDonacion.obtenerDonacionesSegmentadasEnDeposito().forEach(donacionSegmentada -> {
            List<RankingEntidadBeneficiaria> rankAux = new ArrayList<>();
            algoritmos.forEach(algoritmo -> {
                rankAux.addAll(algoritmo.rankear(donacionSegmentada, servicioBeneficiarias.getAllEntidades()));
            });
            rankings.add(new SegmentadaRankings(donacionSegmentada, rankAux));
        });
    }


    public List<RankingEntidadBeneficiaria> filtrarEntidades(Long idDonacion) {
        Optional<SegmentadaRankings> segmentadaRankings = rankings.stream().filter(r -> r.getSegmentada().getId().equals(idDonacion))
                .findFirst();
        return Optional.of(segmentadaRankings.get().getRankings()
                        .stream().filter(ranking ->
                                segmentadaRankings.get().getRankings().stream().anyMatch(otroRanking ->
                                        !otroRanking.getAlgoritmoUsado().equals(ranking.getAlgoritmoUsado()) &&
                                                otroRanking.getEntidadBeneficiaria().equals(ranking.getEntidadBeneficiaria())
                                )
                        ).toList())
                .filter(rankings -> !rankings.isEmpty())
                .orElseGet(() -> {
                    return segmentadaRankings.get().getRankings();
                });
    }

//    public List<RankingEntidadBeneficiaria> mostrarInformacion(DonacionSegmentada donacion) {
//        List<EntidadBeneficiaria> entidades = repositorioEntidades.getEntidadBeneficiarias();
//
//        List<List<RankingEntidadBeneficiaria>> resultadosPorAlgoritmo = algoritmos.stream()
//                .map(algoritmo -> algoritmo.rankear(entidades, donacion))
//                .toList();
//
//        Set<EntidadBeneficiaria> interseccion = null;
//        for (List<RankingEntidadBeneficiaria> resultado : resultadosPorAlgoritmo) {
//            Set<EntidadBeneficiaria> entidadesDeEsteAlgoritmo = resultado.stream()
//                    .map(RankingEntidadBeneficiaria::getEntidad)
//                    .collect(Collectors.toSet());
//
//            if (interseccion == null) {
//                interseccion = entidadesDeEsteAlgoritmo;
//            } else {
//                interseccion.retainAll(entidadesDeEsteAlgoritmo);
//            }
//        }
//
//        Set<EntidadBeneficiaria> finalInterseccion = interseccion;
//        List<RankingEntidadBeneficiaria> coincidencias = resultadosPorAlgoritmo.stream()
//                .flatMap(List::stream)
//                .filter(r -> finalInterseccion.contains(r.getEntidad()))
//                .toList();
//
//        List<RankingEntidadBeneficiaria> resultadoFinal = !coincidencias.isEmpty()
//                ? coincidencias
//                : resultadosPorAlgoritmo.stream().flatMap(List::stream).toList();
//
//        return new ArrayList<>(resultadoFinal);
//    }

}

