package Sdonaciones.repositorios;


import Sdonaciones.dominio.donacion.DonacionAsignada;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class RepoDonacionesAsignadas {
    private Long id = 0L;
    private final List<DonacionAsignada> donacionesAsignadas = new ArrayList<>();

    public List<DonacionAsignada> listarTodas() {
        return List.copyOf(donacionesAsignadas);
    }

    public DonacionAsignada buscarPorIdSegmentada(Long idDonacionSegmentada) {
        return donacionesAsignadas.stream().filter(
                d -> d.getDonacionesSegmentadas().stream().anyMatch(s -> s.getId().equals(idDonacionSegmentada))
        ).findFirst().orElse(null);
    }

    public DonacionAsignada buscarPorIdNecesidad(Long idNecesidad) {
        return donacionesAsignadas.stream().filter(
                d -> d.getNecesidadResuelta().getId().equals(idNecesidad)
        ).findFirst().orElse(null);
    }


    public void guardar(DonacionAsignada donacionAsignada) {
        donacionAsignada.setId(++id);
        donacionesAsignadas.add(donacionAsignada);
    }

    public void eliminar(Long idDonacionAsignada) {
        donacionesAsignadas.removeIf(d -> d.getId() == idDonacionAsignada);
    }

    public void actualizarDonacion(Long id, DonacionAsignada donacionAsignadaActualizada) {
        donacionesAsignadas.removeIf(d -> d.getId() == id);
        guardar(donacionAsignadaActualizada);
    }

    public DonacionAsignada buscarPorId(Long id) {
        return donacionesAsignadas.stream().filter(donacion -> donacion.getId() == id).findFirst().orElse(null);
    }

    public Long obtenerUltimoID() {
        return ++id;
    }

    public List<DonacionAsignada> getDonaciones() {
        return donacionesAsignadas;
    }
}
