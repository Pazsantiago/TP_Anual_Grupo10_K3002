package Sdonaciones.repositorios;


import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.*;

public class RepositorioEntidades {

    private final List<EntidadBeneficiaria> entidadBeneficiarias = new ArrayList<>();


    public void guardar(EntidadBeneficiaria entidadBeneficiaria) {
        if (existePorRazonSocial(entidadBeneficiaria.getRazonSocial())) {
            throw new IllegalStateException(
                    "Ya existe un entidadBeneficiaria con el RazonSocial: " + entidadBeneficiaria.getRazonSocial());
        }
        entidadBeneficiarias.add(entidadBeneficiaria);
    }

    public boolean existePorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().anyMatch(donante -> Objects.equals(donante.getRazonSocial(), razonSocial));
    }


    public Optional<EntidadBeneficiaria> buscarPorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().filter(d -> Objects.equals(d.getRazonSocial(), razonSocial)).findFirst();
    }


    public List<EntidadBeneficiaria> getEntidadBeneficiarias() {
        return entidadBeneficiarias;
    }
}
