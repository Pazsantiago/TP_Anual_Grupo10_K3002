package Sdonaciones.repositorios;


import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class RepoNecesidades {

    private final List<Necesidad> necesidades = new ArrayList<>();

    public List<Necesidad> listarTodas() {
        return List.copyOf(necesidades);
    }

    public void guardar(Necesidad necesidad) {
        necesidades.add(necesidad);
    }

    public Necesidad buscarPorId(Integer id) {
        return necesidades.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Necesidad actualizarNecesidad(Integer id, Necesidad updatedNecesidad) {
        Necesidad antigua = necesidades.stream().filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        necesidades.set(necesidades.indexOf(antigua), updatedNecesidad);
        return updatedNecesidad;
    }

    public String eliminarEntidad(Integer id) {
        necesidades.removeIf(e -> e.getId().equals(id));
        return "Necesidad eliminada";
    }

}
