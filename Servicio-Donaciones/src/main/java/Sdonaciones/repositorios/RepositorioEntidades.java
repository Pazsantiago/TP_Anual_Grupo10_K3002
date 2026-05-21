package ar.edu.utn.donatrack.repositorios;

import ar.edu.utn.donatrack.dominio.entidad.EntidadBeneficiaria;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioEntidades {

    private final Map<String, EntidadBeneficiaria> entidades = new ConcurrentHashMap<>();


    public void guardar(EntidadBeneficiaria entidad) {
        entidades.put(entidad.getRazonSocial().toLowerCase(), entidad);
    }


    public Optional<EntidadBeneficiaria> buscarPorRazonSocial(String razonSocial) {
        return Optional.ofNullable(entidades.get(razonSocial.toLowerCase()));
    }


    public List<EntidadBeneficiaria> listarTodas() {
        return List.copyOf(entidades.values());
    }
}
