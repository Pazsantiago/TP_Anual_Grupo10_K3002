package Sdonaciones.dominio.entidad;

import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadBeneficiaria {
    private Long id;
    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    private List<Necesidad> necesidadesActuales = new ArrayList<>();
    private List<Necesidad> necesidadesHistoricas = new ArrayList<>();


    public void agregarNecesidadActual(Necesidad necesidad) {
        necesidadesActuales.add(necesidad);
    }

    public void eliminarNecesidadActual(Necesidad necesidad) {
        necesidadesActuales.removeIf(e -> e.getId().equals(necesidad.getId()));
        necesidadesHistoricas.add(necesidad);
    }

}
