package utn.donatrack.donatrack_frontend.client.dto.logistica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/** estadoDonacion: PENDIENTE, EN_TRASLADO, ENTREGADA o NO_RECIBIDA (texto, para tolerar valores nuevos). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionPendienteResponse(
    String idDonacion,
    String destinatario,
    String estadoDonacion,
    DireccionResponse direccion,
    List<BultoResponse> bultos) {
    public DonacionPendienteResponse {
        if (bultos == null) bultos = List.of();
    }
}
