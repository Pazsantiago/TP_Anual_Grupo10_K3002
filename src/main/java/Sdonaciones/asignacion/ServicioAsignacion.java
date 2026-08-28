package Sdonaciones.asignacion;

import Sdonaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.donacion.EstadoDonacion;
import Sdonaciones.dominio.donacion.TipoEstadoDonacion;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import Sdonaciones.repositorios.RepoEntidades;
import Sdonaciones.repositorios.RepoNecesidades;
import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class ServicioAsignacion {
    private RepoEntidades repositorioEntidades;
    private RepoNecesidades repositorioNecesidades;
    private List<IAlgoritmoAsignacion> algoritmos;
    private Map<Integer, List<RankingEntidadBeneficiaria>> rankings;
    private RepoDonacionesAsignadas repositorioDonacionesAsignadas;
    private List<DonacionSegmentada> donacionesSegmentadas;


    public ServicioAsignacion(List<IAlgoritmoAsignacion> algoritmos, RepoEntidades repo, RepoDonacionesAsignadas repositorioDonacionesAsignadas, RepoNecesidades repositorioNecesidades) {
        this.algoritmos = algoritmos;
        this.repositorioEntidades = repo;
        this.repositorioDonacionesAsignadas = repositorioDonacionesAsignadas;
        this.repositorioNecesidades = repositorioNecesidades;
        donacionesSegmentadas = new ArrayList<>();
        rankings = new HashMap<Integer, List<RankingEntidadBeneficiaria>>();
    }

    public void agregarDonacionesSegmentadas(List<DonacionSegmentada> donaciones) {
        this.donacionesSegmentadas.addAll(donaciones);
    }

    public Map<Integer, List<RankingEntidadBeneficiaria>> obtenerRankings() {
        return Map.copyOf(rankings);
    }

    @Async
    public void generarRanking() {
        donacionesSegmentadas.forEach(donacionSegmentada -> {
            List<RankingEntidadBeneficiaria> rankAux = new ArrayList<>();
            algoritmos.forEach(algoritmo -> {
                rankAux.addAll(algoritmo.rankear(donacionSegmentada, repositorioEntidades.getEntidadBeneficiarias()));
            });
            rankings.put(donacionSegmentada.getId(), rankAux);
        });
    }

    public DonacionAsignada asignarDonacion(Integer idDonacion, Integer idEntidad, Integer idNecesidad) {
        DonacionSegmentada donacion = donacionesSegmentadas.stream().filter(d -> idDonacion.equals(d.getId())).findFirst().orElse(null);
        Necesidad necesidad = repositorioEntidades.getEntidadBeneficiarias().stream().filter(e -> e.getId().equals(idEntidad))
                .findFirst().get().getNecesidadesActuales().stream().filter(e -> e.getId().equals(idNecesidad)).findFirst().get();
        DonacionAsignada donacionFinal = new DonacionAsignada(donacion, necesidad, new Date());
        donacionesSegmentadas.stream().filter(d -> idDonacion.equals(d.getId())).findFirst().get().cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ASIGNACION_REALIZADA, null));
        donacionesSegmentadas.removeIf(d -> d.getId().equals(idDonacion));
        repositorioNecesidades.eliminarNecesidad(idNecesidad, idEntidad);
        repositorioDonacionesAsignadas.guardar(donacionFinal);
        return donacionFinal;
    }

    public List<RankingEntidadBeneficiaria> filtrarEntidades(Integer idDonacion) {
        return Optional.of(rankings.get(idDonacion).stream().filter(ranking ->
                        rankings.get(idDonacion).stream().anyMatch(otroRanking ->
                                !otroRanking.getAlgoritmoUsado().equals(ranking.getAlgoritmoUsado()) &&
                                        otroRanking.getEntidad().equals(ranking.getEntidad())
                        )
                ).toList())
                .filter(rankings -> !rankings.isEmpty())
                .orElseGet(() -> {
                    return this.getRankings().get(idDonacion);
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

