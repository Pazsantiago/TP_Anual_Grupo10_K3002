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

    private Long idEntidad = 0L;
    private Long idNecesidad = 0L;
    private final List<EntidadBeneficiaria> entidadBeneficiarias = new ArrayList<>();

    public EntidadBeneficiaria obtenerPorId(Long id) {
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
        entidadBeneficiaria.setId(obtenerSiguienteIDNecesidad());
        entidadBeneficiarias.add(entidadBeneficiaria);
    }

    public Long obtenerSiguienteIDNecesidad() {
        return ++idNecesidad;
    }


    public boolean existePorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().anyMatch(donante -> Objects.equals(donante.getRazonSocial(), razonSocial));
    }

    public void actualizarEntidad(EntidadBeneficiaria updatedEntidad) {
        entidadBeneficiarias.set(entidadBeneficiarias.indexOf(obtenerPorId(updatedEntidad.getId())), updatedEntidad);
    }

    public void eliminarEntidad(Long id) {
        entidadBeneficiarias.removeIf(e -> e.getId().equals(id));
    }


    public Optional<EntidadBeneficiaria> buscarPorRazonSocial(String razonSocial) {
        return entidadBeneficiarias.stream().filter(d -> Objects.equals(d.getRazonSocial(), razonSocial)).findFirst();
    }

    public List<EntidadBeneficiaria> listarTodas() {
        return List.copyOf(entidadBeneficiarias);
    }
}
