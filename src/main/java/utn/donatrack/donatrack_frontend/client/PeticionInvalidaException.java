package utn.donatrack.donatrack_frontend.client;

/** El servicio respondio 4xx */
public class PeticionInvalidaException extends ServicioExternoException {
    public PeticionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
