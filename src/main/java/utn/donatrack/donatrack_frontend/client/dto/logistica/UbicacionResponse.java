package utn.donatrack.donatrack_frontend.client.dto.logistica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UbicacionResponse(Double latitud, Double longitud, LocalDateTime timestamp) {}
