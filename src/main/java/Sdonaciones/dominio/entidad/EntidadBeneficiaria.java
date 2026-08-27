package Sdonaciones.dominio.entidad;

import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntidadBeneficiaria {
    private Integer id;
    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    private List<Necesidad> necesidadesActuales;
    private List<Necesidad> necesidadesHistoricas;

}
