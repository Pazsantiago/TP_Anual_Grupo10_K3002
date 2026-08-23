package Slogistica.repositorios;

import Slogistica.dominio.entrega.Entrega;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepoEntregas {

    private final List<Entrega> entregas = new ArrayList<>();
    private Integer contadorId = 1;

    public Entrega guardar(Entrega entrega) {
        entrega.setId(contadorId++);
        entregas.add(entrega);
        return entrega;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public Optional<Entrega> buscarPorId(Integer id) {
        return entregas.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public List<Entrega> buscarPorCamion(String patente) {
        return entregas.stream()
                .filter(e -> e.getCamion().getPatente().equalsIgnoreCase(patente))
                .toList();
    }
}
