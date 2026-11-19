package utn.donatrack.donatrack_frontend.client.dto.donaciones.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoRequestDTO {
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
}