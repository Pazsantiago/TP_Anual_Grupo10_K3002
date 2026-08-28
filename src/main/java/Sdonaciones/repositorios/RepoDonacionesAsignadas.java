package Sdonaciones.repositorios;


import Sdonaciones.dominio.donacion.DonacionAsignada;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class RepoDonacionesAsignadas {
    private Integer id = 0;
    private final List<DonacionAsignada> donacionesAsignadas = new ArrayList<>();

    public List<DonacionAsignada> listarTodas() {
        return List.copyOf(donacionesAsignadas);
    }

    public void guardar(DonacionAsignada donacionAsignada) {
        donacionAsignada.setId(++id);
        donacionesAsignadas.add(donacionAsignada);
    }

    public Optional<DonacionAsignada> buscarPorId(Integer id) {
        return donacionesAsignadas.stream().filter(donacion -> donacion.getId() == id).findFirst();
    }

    public List<DonacionAsignada> getDonaciones() {
        return donacionesAsignadas;
    }
}
