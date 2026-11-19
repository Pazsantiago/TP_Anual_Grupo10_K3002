package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionSegmentadaResponseDTO(
        Long id,
        Long donacionInicial,
        String estadoActual,
        String subcategoria
) {
}



