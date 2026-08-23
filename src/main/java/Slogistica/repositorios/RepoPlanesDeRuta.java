package Slogistica.repositorios;

import Slogistica.dominio.ruta.PlanDeRuta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepoPlanesDeRuta {

    private final List<PlanDeRuta> planes = new ArrayList<>();
    private Integer contadorId = 1;

    public PlanDeRuta guardar(PlanDeRuta plan) {
        plan.setId(contadorId++);
        planes.add(plan);
        return plan;
    }

    public List<PlanDeRuta> getPlanes() {
        return planes;
    }

    public Optional<PlanDeRuta> buscarPorId(Integer id) {
        return planes.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Optional<PlanDeRuta> buscarPorPatenteCamion(String patente) {
        return planes.stream()
                .filter(p -> p.getCamion().getPatente().equalsIgnoreCase(patente))
                .findFirst();
    }
}
