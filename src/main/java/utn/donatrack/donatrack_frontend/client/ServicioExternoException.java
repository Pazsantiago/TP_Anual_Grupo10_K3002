package utn.donatrack.donatrack_frontend.client;

/** Base */
public abstract class ServicioExternoException extends RuntimeException {
    protected ServicioExternoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
