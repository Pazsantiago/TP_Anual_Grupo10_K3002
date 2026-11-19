package utn.donatrack.donatrack_frontend.client.dto.donaciones.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String tipo;
}

