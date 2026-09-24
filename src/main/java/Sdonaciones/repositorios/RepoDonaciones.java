package Sdonaciones.repositorios;

import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@Data
public class RepoDonaciones {
    private Long ultimoIdDonacionOriginal = 0L, ultimoIdDonacionSegmentada = 0L;
    private final List<Donacion> donaciones = new ArrayList<>();

    public void guardar(Donacion donacion) {
        donacion.setId(++ultimoIdDonacionOriginal);
        donacion.getDonacionesSegmentadas().stream().max(Comparator.comparingLong(DonacionSegmentada::getId))
                .ifPresent(d -> ultimoIdDonacionSegmentada = d.getId());
        //Por ahora se fija si a es mayor que b, devuelve a si es positivo o b si es negativo.
        donaciones.add(donacion);
    }

    public Donacion buscarPorId(Long id) {
        return donaciones.stream().filter(donacion -> donacion.getId().equals(id)).findFirst().orElse(null);
    }


    public void actualizarDonacion(Donacion updateDonacion) {
        donaciones.set(donaciones.indexOf(updateDonacion), updateDonacion);
    }

    public void borrarDonacion(Long id) {
        donaciones.removeIf(d -> d.getId().equals(id));
    }

}
