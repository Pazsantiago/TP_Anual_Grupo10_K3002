package utn.donatrack.donatrack_frontend.client.dto.incentivos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** progreso va de 0 a 100 (porcentaje). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MisionResponse(Long id, String nombre, String descripcion, String categoria, String insigniaNombre, Double progreso) {
    public MisionResponse {
        if (progreso == null) progreso = 0.0;
    }
}
