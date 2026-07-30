package Sdonaciones.dominio.donante;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedioContacto {

    private TipoMedioContacto tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;

}
