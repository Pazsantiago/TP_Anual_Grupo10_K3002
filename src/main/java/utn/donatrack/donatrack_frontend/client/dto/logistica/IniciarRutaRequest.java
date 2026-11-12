package utn.donatrack.donatrack_frontend.client.dto.logistica;

/** Cuerpo de POST /api/rutas/iniciar: el chofer da por iniciado el recorrido de su camion. */
public record IniciarRutaRequest(String idCamion) {}
