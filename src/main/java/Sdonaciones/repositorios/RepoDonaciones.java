package Sdonaciones.repositorios;

import Sdonaciones.dominio.donacion.Donacion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class RepoDonaciones {

    private final List<Donacion> donaciones = new ArrayList<>();

    public void guardar(Donacion donacion) {
        donaciones.add(donacion);
    }

    public Optional<Donacion> buscarPorId(Integer id) {
        return donaciones.stream().filter(donacion -> donacion.getId() == id).findFirst();
    }

    public List<Donacion> getDonaciones() {
        return donaciones;
    }
}
