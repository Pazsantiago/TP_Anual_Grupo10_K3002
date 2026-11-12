package utn.donatrack.donatrack_frontend.client.dto.logistica;

/** Cuerpo de POST /api/donaciones/{id}/devolucion: la donacion vuelve al deposito y la entrega a "Pendiente". */
public record DevolucionRequest(String justificacion) {}
