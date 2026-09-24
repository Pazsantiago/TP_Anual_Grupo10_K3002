package Sdonaciones.repositorios;


import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class RepoNecesidades {
    private Long id = 0L;
    private final List<Necesidad> necesidades = new ArrayList<>();

    public List<Necesidad> listarTodas() {
        return List.copyOf(necesidades);
    }

    public void guardar(Necesidad necesidad) {
        necesidad.setId(++id);
        necesidades.add(necesidad);

    }

    public Long obtenerSiguienteIDNecesidad() {
        return ++id;
    }

    public Necesidad buscarPorId(Long idNecesidad) {
        return necesidades.stream()
                .filter(n -> n.getId().equals(idNecesidad))
                .findFirst()
                .orElse(null);
    }

    public void eliminarNecesidad(Long idNecesidad) {
        necesidades.removeIf(e -> e.getId().equals(idNecesidad));
    }

    public void eliminarNecesidadesDeEntidad(Long idEntidad) {
        necesidades.removeIf(e -> e.getEntidadBeneficiaria().getId().equals(idEntidad));
    }

}
