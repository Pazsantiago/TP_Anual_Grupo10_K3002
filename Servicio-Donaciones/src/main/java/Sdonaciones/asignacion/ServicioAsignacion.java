package Sdonaciones.asignacion;

import Sdonaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import Sdonaciones.asignacion.algoritmosAsignacion.Ranking;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.repositorios.RepositorioEntidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioAsignacion {
    private RepositorioEntidades repositorioEntidades;
    private ArrayList<IAlgoritmoAsignacion> algoritmos;

    public ServicioAsignacion(RepositorioEntidades repo){
        this.repositorioEntidades = repo;
    }

    public List<Ranking> mostrarInformacion(DonacionSegmentada donacion) {
        List<EntidadBeneficiaria> entidades = repositorioEntidades.getEntidadBeneficiarias();

        List<List<Ranking>> resultadosPorAlgoritmo = algoritmos.stream()
                .map(algoritmo -> algoritmo.rankEntidad(entidades,donacion))
                .toList();

        Set<EntidadBeneficiaria> interseccion = null;
        for (List<Ranking> resultado : resultadosPorAlgoritmo) {
            Set<EntidadBeneficiaria> entidadesDeEsteAlgoritmo = resultado.stream()
                    .map(Ranking::getEntidad)
                    .collect(Collectors.toSet());

            if (interseccion == null) {
                interseccion = entidadesDeEsteAlgoritmo;
            } else {
                interseccion.retainAll(entidadesDeEsteAlgoritmo);
            }
        }

        Set<EntidadBeneficiaria> finalInterseccion = interseccion;
        List<Ranking> coincidencias = resultadosPorAlgoritmo.stream()
                .flatMap(List::stream)
                .filter(r -> finalInterseccion.contains(r.getEntidad()))
                .toList();

        List<Ranking> resultadoFinal = !coincidencias.isEmpty()
                ? coincidencias
                : resultadosPorAlgoritmo.stream().flatMap(List::stream).toList();

        return new ArrayList<>(resultadoFinal);
    }

}
