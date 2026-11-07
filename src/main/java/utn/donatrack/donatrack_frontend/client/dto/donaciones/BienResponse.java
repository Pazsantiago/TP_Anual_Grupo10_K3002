package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BienResponse(String descripcion, Integer cantidadOriginal, String unidad) {}
