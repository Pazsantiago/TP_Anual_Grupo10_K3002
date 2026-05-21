package ar.edu.utn.donatrack.repositorios;

import ar.edu.utn.donatrack.dominio.donacion.Donacion;
import ar.edu.utn.donatrack.dominio.donacion.EstadoDonacion;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioDonaciones {

    private final Map<String, Donacion> donaciones = new ConcurrentHashMap<>();


    public void guardar(Donacion donacion) {
        donaciones.put(donacion.getId(), donacion);
    }


    public Optional<Donacion> buscarPorId(String id) {
        return Optional.ofNullable(donaciones.get(id));
    }


    public List<DonacionSegmentada> listarSegmentadasPorEstado(EstadoDonacion estado) {
        return donaciones.values().stream()
                .flatMap(d -> d.getDonacionesSegmentadas().stream())
                .filter(ds -> ds.getEstado() == estado)
                .toList();
    }

    public List<Donacion> listarTodas() {
        return List.copyOf(donaciones.values());
    }
}
