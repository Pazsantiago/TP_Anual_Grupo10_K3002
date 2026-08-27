package Sdonaciones.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadoDonacion {
    private TipoEstadoDonacion tipoEstado;
    private String justificacion;
}
