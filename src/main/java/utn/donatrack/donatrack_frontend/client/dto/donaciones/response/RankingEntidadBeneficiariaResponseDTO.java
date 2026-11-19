package  utn.donatrack.donatrack_frontend.client.dto.donaciones.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingEntidadBeneficiariaResponseDTO {
    private EntidadBeneficiariaResponseDTO entidadBeneficiaria;
    private Integer posicion;
    private double puntaje;
    private String algoritmoUsado;

}