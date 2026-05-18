package ar.edu.utn.donatrack.excepciones;

public class DonadorNoEncontradoException extends RuntimeException {
    public DonadorNoEncontradoException(String correo) {
        super("No se encontró donador con correo: " + correo);
    }
}
