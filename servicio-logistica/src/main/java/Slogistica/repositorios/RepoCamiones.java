package Slogistica.repositorios;

import Slogistica.dominio.camion.Camion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepoCamiones {

    private final List<Camion> camiones = new ArrayList<>();
    private Integer contadorId = 1;

    public Camion guardarCamion(Camion camion) {
        camion.setId(contadorId++);
        camiones.add(camion);
        return camion;
    }

    public void addCamion(Camion camion) {
        camiones.add(camion);
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public List<Camion> getCamionesDisponibles() {
        return camiones.stream().filter(Camion::isDisponible).toList();
    }

    public Optional<Camion> buscarPorId(Integer id) {
        return camiones.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<Camion> buscarPorPatente(String patente) {
        return camiones.stream().filter(c -> c.getPatente().equalsIgnoreCase(patente)).findFirst();
    }
}
