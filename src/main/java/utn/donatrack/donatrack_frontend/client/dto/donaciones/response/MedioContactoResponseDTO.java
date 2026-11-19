package  utn.donatrack.donatrack_frontend.client.dto.donaciones.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoResponseDTO {
    private String tipo;
    private String correoElectronico;
    private String telefono;
}