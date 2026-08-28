package Sdonaciones.repositorios;

import Sdonaciones.dominio.donacion.Donacion;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class RepoDonaciones {
    private Integer ultimoIdDonacionOriginal = 0, ultimoIdDonacionSegmentada = 0;
    private final List<Donacion> donaciones = new ArrayList<>();

    public void guardar(Donacion donacion) {
        donacion.setId(++ultimoIdDonacionOriginal);
        ultimoIdDonacionSegmentada = donacion.segmentarse(ultimoIdDonacionSegmentada);
        donaciones.add(donacion);
    }

    public Optional<Donacion> buscarPorId(Integer id) {
        return donaciones.stream().filter(donacion -> donacion.getId() == id).findFirst();
    }

    public void actualizarDonacion(Integer id, Donacion updateDonacion) {
        Donacion oldDonacion = donaciones.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
        updateDonacion.setId(id);
        updateDonacion.setDonacionesSegmentadas(oldDonacion.getDonacionesSegmentadas());
        donaciones.set(donaciones.indexOf(oldDonacion), updateDonacion);
    }

    public void borrarDonacion(Integer id) {
        donaciones.removeIf(d -> d.getId() == id);
    }

}
