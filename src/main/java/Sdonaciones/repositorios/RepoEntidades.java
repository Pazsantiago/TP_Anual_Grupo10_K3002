package Sdonaciones.repositorios;


import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@Data
public class RepoEntidades {

    private Integer idEntidad = 0;
    private final List<EntidadBeneficiaria> entidadBeneficiarias = new ArrayList<>();

    public EntidadBeneficiaria obtenerPorId(Integer id) {
        return entidadBeneficiarias.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void guardar(EntidadBeneficiaria entidadBeneficiaria) {
        if (existePorRazonSocial(entidadBeneficiaria.getRazonSocial())) {
            throw new IllegalStateException(
                    "Ya existe un entidadBeneficiaria con el RazonSocial: " + entidadBeneficiaria.getRazonSocial());
        }
        entidadBeneficiaria.setId(++idEntidad);
        entidadBeneficiarias.add(entidadBeneficiaria);
    }

    public boolean existePorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().anyMatch(donante -> Objects.equals(donante.getRazonSocial(), razonSocial));
    }

    public EntidadBeneficiaria actualizarEntidad(Integer id, EntidadBeneficiaria updatedEntidad) {
        EntidadBeneficiaria antigua = entidadBeneficiarias.stream().filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        entidadBeneficiarias.set(entidadBeneficiarias.indexOf(antigua), updatedEntidad);
        return updatedEntidad;
    }

    public String eliminarEntidad(Integer id) {
        String razonSocial = entidadBeneficiarias.stream().filter(e -> e.getId().equals(id)).findFirst().get().getRazonSocial();
        entidadBeneficiarias.removeIf(e -> e.getId().equals(id));
        return razonSocial;
    }

    public Optional<EntidadBeneficiaria> buscarPorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().filter(d -> Objects.equals(d.getRazonSocial(), razonSocial)).findFirst();
    }

    public List<EntidadBeneficiaria> listarTodas() {
        return List.copyOf(entidadBeneficiarias);
    }

    public List<EntidadBeneficiaria> getEntidadBeneficiarias() {
        return entidadBeneficiarias;
    }
}
