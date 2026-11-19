package  utn.donatrack.donatrack_frontend.client.dto.donaciones.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionRequestDTO {
    private String calle;
    private Integer altura;
    private String localidad;
    private String codigoPostal;
}
