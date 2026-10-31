package utn.donatrack.donatrack_frontend.client.dto;

/** Formato de error del GlobalExceptionHandler del servicio: {"timestamp": ..., "error": "..."} */
public record ErrorResponse(String error) {}
