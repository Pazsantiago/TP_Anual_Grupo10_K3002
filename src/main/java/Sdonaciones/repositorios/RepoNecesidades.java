package Sdonaciones.repositorios;


import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class RepoNecesidades {
    private Integer id = 0;
    private final List<Necesidad> necesidades = new ArrayList<>();

    public List<Necesidad> listarTodas() {
        return List.copyOf(necesidades);
    }

    public void guardar(Necesidad necesidad) {
        necesidad.setId(++id);
        necesidades.add(necesidad);

    }

    public Necesidad buscarPorId(Integer idNecesidad, Integer idEntidad) {
        return necesidades.stream()
                .filter(n -> n.getId().equals(idNecesidad) && n.getEntidadBeneficiaria().getId().equals(idEntidad))
                .findFirst()
                .orElse(null);
    }

    public void actualizarNecesidad(Integer idNecesidad, Integer idEntidad, Necesidad updatedNecesidad) {
        Necesidad antigua = necesidades.stream().filter(p -> p.getId().equals(idNecesidad) && p.getEntidadBeneficiaria().getId().equals(idEntidad))
                .findFirst().orElse(null);
        if (antigua != null) {
            updatedNecesidad.setId(antigua.getId());
            necesidades.set(necesidades.indexOf(antigua), updatedNecesidad);
        }
    }

    public void eliminarNecesidad(Integer idNecesidad, Integer idEntidad) {
        Necesidad necesidad = necesidades.stream().filter(p -> p.getId().equals(idNecesidad) && p.getEntidadBeneficiaria().getId().equals(idEntidad)).findFirst().orElse(null);
        necesidad.getEntidadBeneficiaria().eliminarNecesidadActual(necesidad);
        necesidades.removeIf(e -> e.getId().equals(idNecesidad) && e.getEntidadBeneficiaria().getId().equals(idEntidad));
    }

}
