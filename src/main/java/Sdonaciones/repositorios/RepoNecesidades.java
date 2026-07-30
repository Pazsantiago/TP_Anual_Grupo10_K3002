package Sdonaciones.repositorios;


import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RepoNecesidades {

    private final List<Necesidad> necesidades = new ArrayList<>();


//    public void guardar(Necesidad necesidad) {
//        if () {
//            throw new IllegalStateException(
//                    "Ya existe un entidadBeneficiaria con el RazonSocial: " + entidadBeneficiaria.getRazonSocial());
//        }
//        entidadBeneficiarias.add(entidadBeneficiaria);
//    }
//
//    public boolean existePorRazonSocial(String razonSocial) {
//        return entidadBeneficiarias.stream().anyMatch(donante -> Objects.equals(donante.getRazonSocial(), razonSocial));
//    }
//
//
//    public Optional<EntidadBeneficiaria> buscarPorRazonSocial(String razonSocial) {
//        return entidadBeneficiarias.stream().filter(d -> Objects.equals(d.getRazonSocial(), razonSocial)).findFirst();
//    }
//
//
//    public List<EntidadBeneficiaria> getEntidadBeneficiarias() {
//        return entidadBeneficiarias;
//    }
}
