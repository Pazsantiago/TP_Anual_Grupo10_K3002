package utn.donatrack.donatrack_frontend.client;

/** Timeout, host caido, URL mal configurada o error 5xx. */
public class ServicioNoDisponibleException extends ServicioExternoException {
    public ServicioNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
