package Sdonaciones.asignacion;

import Sdonaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.repositorios.RepoEntidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioAsignacion {
    private RepoEntidades repositorioEntidades;
    private ArrayList<IAlgoritmoAsignacion> algoritmos;

    public ServicioAsignacion(RepoEntidades repo) {
        this.repositorioEntidades = repo;
    }

    public List<RankingEntidadBeneficiaria> mostrarInformacion(DonacionSegmentada donacion) {
        List<EntidadBeneficiaria> entidades = repositorioEntidades.getEntidadBeneficiarias();

        List<List<RankingEntidadBeneficiaria>> resultadosPorAlgoritmo = algoritmos.stream()
                .map(algoritmo -> algoritmo.rankear(entidades, donacion))
                .toList();

        Set<EntidadBeneficiaria> interseccion = null;
        for (List<RankingEntidadBeneficiaria> resultado : resultadosPorAlgoritmo) {
            Set<EntidadBeneficiaria> entidadesDeEsteAlgoritmo = resultado.stream()
                    .map(RankingEntidadBeneficiaria::getEntidad)
                    .collect(Collectors.toSet());

            if (interseccion == null) {
                interseccion = entidadesDeEsteAlgoritmo;
            } else {
                interseccion.retainAll(entidadesDeEsteAlgoritmo);
            }
        }

        Set<EntidadBeneficiaria> finalInterseccion = interseccion;
        List<RankingEntidadBeneficiaria> coincidencias = resultadosPorAlgoritmo.stream()
                .flatMap(List::stream)
                .filter(r -> finalInterseccion.contains(r.getEntidad()))
                .toList();

        List<RankingEntidadBeneficiaria> resultadoFinal = !coincidencias.isEmpty()
                ? coincidencias
                : resultadosPorAlgoritmo.stream().flatMap(List::stream).toList();

        return new ArrayList<>(resultadoFinal);
    }

}
