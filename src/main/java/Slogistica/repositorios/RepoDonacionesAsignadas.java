package Slogistica.repositorios;

import Slogistica.dominio.donacion.DonacionDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda las donaciones que el Servicio de Donaciones envió como
 * "Asignación Realizada" y que todavía no fueron incluidas en un
 * PlanDeRuta.
 */
@Repository
public class RepoDonacionesAsignadas {

    private final List<DonacionDTO> donacionesAsignadas = new ArrayList<>();

    public void recibirDonaciones(List<DonacionDTO> donaciones) {
        donacionesAsignadas.addAll(donaciones);
    }

    public List<DonacionDTO> pedirDonacionesAsignadas() {
        return new ArrayList<>(donacionesAsignadas);
    }

    public void quitarDonaciones(List<DonacionDTO> donaciones) {
        donacionesAsignadas.removeAll(donaciones);
    }
}
