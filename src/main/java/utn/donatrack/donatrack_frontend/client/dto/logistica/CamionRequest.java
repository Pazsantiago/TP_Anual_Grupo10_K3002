package utn.donatrack.donatrack_frontend.client.dto.logistica;

/** Cuerpo de POST /api/camiones y PUT /api/camiones/{id}. */
public record CamionRequest(String patente, Double capacidadCarga, Double capacidadVolumenM3, Double alturaM, Boolean disponible) {}
