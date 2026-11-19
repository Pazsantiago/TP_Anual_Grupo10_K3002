package  utn.donatrack.donatrack_frontend.client.dto.donaciones.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDonacionRequestDTO {
    private TipoEstadoDonacion tipoEstado;
    private String justificacion;
}
