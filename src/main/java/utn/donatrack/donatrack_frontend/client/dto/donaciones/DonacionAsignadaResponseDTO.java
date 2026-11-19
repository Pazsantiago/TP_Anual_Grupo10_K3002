package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionAsignadaResponseDTO(
        Long id,
        List<Long> donacionesSegmentadas,
        Long necesidadResuelta
) {
}
