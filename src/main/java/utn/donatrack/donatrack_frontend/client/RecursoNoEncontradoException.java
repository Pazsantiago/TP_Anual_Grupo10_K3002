package utn.donatrack.donatrack_frontend.client;

/** El servicio respondio 404: el recurso pedido no existe (ej. un donante sin perfil de incentivos). */
public class RecursoNoEncontradoException extends ServicioExternoException {
    public RecursoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
