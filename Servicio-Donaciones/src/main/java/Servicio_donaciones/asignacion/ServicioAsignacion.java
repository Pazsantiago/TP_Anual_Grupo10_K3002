package Servicio_donaciones.asignacion;

import Servicio_donaciones.asignacion.algoritmosAsignacion.IAlgoritmoAsignacion;
import Servicio_donaciones.asignacion.algoritmosAsignacion.Ranking_Entidad_Beneficiaria;
import Servicio_donaciones.dominio.donacion.DonacionSegmentada;
import Servicio_donaciones.dominio.entidad.EntidadBeneficiaria;
import Servicio_donaciones.repositorios.RepositorioEntidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicioAsignacion {
    private RepositorioEntidades repositorioEntidades;
    private ArrayList<IAlgoritmoAsignacion> algoritmos;

    public ServicioAsignacion(RepositorioEntidades repo){
        this.repositorioEntidades = repo;
    }

    public List<Ranking_Entidad_Beneficiaria> mostrarInformacion(DonacionSegmentada donacion) {
        List<EntidadBeneficiaria> entidades = repositorioEntidades.getEntidadBeneficiarias();

        List<List<Ranking_Entidad_Beneficiaria>> resultadosPorAlgoritmo = algoritmos.stream()
                .map(algoritmo -> algoritmo.rankEntidad(entidades,donacion))
                .toList();

        Set<EntidadBeneficiaria> interseccion = null;
        for (List<Ranking_Entidad_Beneficiaria> resultado : resultadosPorAlgoritmo) {
            Set<EntidadBeneficiaria> entidadesDeEsteAlgoritmo = resultado.stream()
                    .map(Ranking_Entidad_Beneficiaria::getEntidad)
                    .collect(Collectors.toSet());

            if (interseccion == null) {
                interseccion = entidadesDeEsteAlgoritmo;
            } else {
                interseccion.retainAll(entidadesDeEsteAlgoritmo);
            }
        }

        Set<EntidadBeneficiaria> finalInterseccion = interseccion;
        List<Ranking_Entidad_Beneficiaria> coincidencias = resultadosPorAlgoritmo.stream()
                .flatMap(List::stream)
                .filter(r -> finalInterseccion.contains(r.getEntidad()))
                .toList();

        List<Ranking_Entidad_Beneficiaria> resultadoFinal = !coincidencias.isEmpty()
                ? coincidencias
                : resultadosPorAlgoritmo.stream().flatMap(List::stream).toList();

        return new ArrayList<>(resultadoFinal);
    }

}
