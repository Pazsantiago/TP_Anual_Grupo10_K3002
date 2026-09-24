package Services.ServiceAsignacion;

import Sdonaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import Services.ServiceDonaciones.ServicioDonacion;
import Services.ServiceNecesidades.ServicioNecesidades;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class ServicioAsignacion {
    private ServicioBeneficiarias servicioBeneficiarias;
    private ServicioDonacion servicioDonacion;
    private List<IAlgoritmoAsignacion> algoritmos;
    private Map<Long, List<RankingEntidadBeneficiaria>> rankings;


    public ServicioAsignacion(List<IAlgoritmoAsignacion> algoritmos, ServicioBeneficiarias servicioBeneficiarias, ServicioNecesidades servicioNecesidades,
                              ServicioDonacion servicioDonacion) {
        this.algoritmos = algoritmos;
        this.servicioBeneficiarias = servicioBeneficiarias;
        this.servicioDonacion = servicioDonacion;
        rankings = new HashMap<Long, List<RankingEntidadBeneficiaria>>();

    }

    public Map<Long, List<RankingEntidadBeneficiaria>> obtenerRankings() {
        return Map.copyOf(rankings);
    }

    @Scheduled(cron = "${horario-baja-carga.cron}")
    public void generarRanking() {
        servicioDonacion.obtenerDonacionesSegmentadasEnDeposito().forEach(donacionSegmentada -> {
            List<RankingEntidadBeneficiaria> rankAux = new ArrayList<>();
            algoritmos.forEach(algoritmo -> {
                rankAux.addAll(algoritmo.rankear(donacionSegmentada, servicioBeneficiarias.getAllEntidades()));
            });
            rankings.put(donacionSegmentada.getId(), rankAux);
        });
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

