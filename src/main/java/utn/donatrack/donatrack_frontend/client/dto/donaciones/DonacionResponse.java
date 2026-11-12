package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
/** "estado" es opcional: si el servicio todavia no lo informa llega null y los filtros por estado avisan que no se aplican. */
public record DonacionResponse(Long id, Long idDonante, String descripcionGeneral, List<BienResponse> bienesDeEntrada, String estado) {
    public DonacionResponse {
        if (bienesDeEntrada == null) bienesDeEntrada = List.of();
    }
}
