package Slogistica.dominio.ruta;

import Slogistica.dominio.camion.Camion;
import Slogistica.dominio.donacion.DonacionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Ruta de reparto de un camión para la jornada: una lista ORDENADA de
 * destinos (donaciones a entregar), tal como la devuelve el proveedor
 * externo de planificación de rutas.
 */
@Data
@NoArgsConstructor
public class PlanDeRuta {

    private Integer id;
    private Camion camion;
    private List<DonacionDTO> entregas = new ArrayList<>();
    private EstadoRuta estado = EstadoRuta.PLANIFICADA;

    public PlanDeRuta(Integer id, Camion camion) {
        this.id = id;
        this.camion = camion;
    }

    public void asignarRuta(List<DonacionDTO> cargamento) {
        this.entregas = new ArrayList<>(cargamento);
    }

    public void iniciarRuta() {
        this.estado = EstadoRuta.EN_CURSO;
    }

    public void finalizarRuta() {
        this.estado = EstadoRuta.FINALIZADA;
    }
}
