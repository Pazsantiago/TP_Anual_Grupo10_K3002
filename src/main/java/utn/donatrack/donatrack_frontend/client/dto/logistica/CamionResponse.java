package utn.donatrack.donatrack_frontend.client.dto.logistica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** capacidadCarga en kg, capacidadVolumenM3 en m3, alturaM en metros. ultimaUbicacion puede ser null. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CamionResponse(
    String id,
    String patente,
    Double capacidadCarga,
    Double capacidadVolumenM3,
    Double alturaM,
    Double cargaActual,
    Double volumenActual,
    Boolean disponible,
    UbicacionResponse ultimaUbicacion) {}
