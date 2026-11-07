package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionResponse(Long id, Long idDonante, String descripcionGeneral, List<BienResponse> bienesDeEntrada) {
    public DonacionResponse {
        if (bienesDeEntrada == null) bienesDeEntrada = List.of();
    }
}
