package utn.donatrack.donatrack_frontend.client;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import utn.donatrack.donatrack_frontend.client.dto.ErrorResponse;

/**
 * Base de los clientes HTTP de cada servicio. Concentra el manejo de errores para que
 * cada cliente solo describa sus endpoints.
 *
 * Traduce todo lo que puede salir mal a 3 excepciones propias:
 *   404            -> RecursoNoEncontradoException
 *   otros 4xx      -> PeticionInvalidaException (con el mensaje del servicio)
 *   5xx, timeout,
 *   host caido,
 *   respuesta rara -> ServicioNoDisponibleException
 *
 * Para sumar un servicio nuevo: crear su RestClient en RestClientConfig y extender esta clase.
 */
public abstract class ClienteServicio {

    private final RestClient restClient;
    private final String nombreServicio;

    /** @param nombreServicio como se nombra en los mensajes al usuario, ej. "donaciones" */
    protected ClienteServicio(RestClient restClient, String nombreServicio) {
        this.restClient = restClient;
        this.nombreServicio = nombreServicio;
    }

    protected RestClient http() {
        return restClient;
    }

    /** Ejecuta una consulta que devuelve lista; si el servicio responde vacio/null, devuelve lista vacia. */
    protected <T> List<T> listaDe(Supplier<List<T>> operacion) {
        List<T> res = call(operacion);
        return res == null ? List.of() : res;
    }

    /** Ejecuta una llamada que no devuelve nada util (altas, cambios de estado, bajas) con el mismo manejo de errores. */
    protected void ejecutar(Runnable operacion) {
        call(() -> {
            operacion.run();
            return null;
        });
    }

    /** Convierte un archivo subido en una parte multipart, conservando su nombre original. */
    protected static ByteArrayResource parteMultipart(org.springframework.web.multipart.MultipartFile archivo) {
        try {
            return new ByteArrayResource(archivo.getBytes()) {
                @Override
                public String getFilename() {
                    return archivo.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new PeticionInvalidaException("No pudimos leer el archivo " + archivo.getOriginalFilename() + ".", e);
        }
    }

    /** Ejecuta la llamada HTTP y traduce los errores. */
    protected <T> T call(Supplier<T> operacion) {
        try {
            return operacion.get();
        } catch (RestClientResponseException e) {
            int status = e.getStatusCode().value();
            if (status == 404) {
                throw new RecursoNoEncontradoException(
                    mensajeDelServicio(e, "No se encontró lo pedido en el servicio de " + nombreServicio + "."), e);
            }
            if (e.getStatusCode().is4xxClientError()) {
                throw new PeticionInvalidaException(
                    mensajeDelServicio(e, "El servicio rechazó la solicitud (" + status + ")."), e);
            }
            throw new ServicioNoDisponibleException(
                "El servicio de " + nombreServicio + " tuvo un problema (" + status + "). Probá de nuevo en unos minutos.", e);
        } catch (ResourceAccessException e) {
            throw new ServicioNoDisponibleException(
                "No pudimos comunicarnos con el servicio de " + nombreServicio + ". Probá de nuevo en unos minutos.", e);
        } catch (RestClientException e) {
            // por ejemplo, la respuesta no se pudo convertir al DTO esperado
            throw new ServicioNoDisponibleException(
                "El servicio de " + nombreServicio + " respondió algo que no pudimos interpretar.", e);
        }
    }

    /** Acepta los dos formatos de error que usan los servicios: JSON {"error": "..."} o texto plano. */
    private String mensajeDelServicio(RestClientResponseException e, String porDefecto) {
        try {
            ErrorResponse body = e.getResponseBodyAs(ErrorResponse.class);
            if (body != null && body.error() != null && !body.error().isBlank()) {
                return body.error();
            }
        } catch (RuntimeException ignorada) {
            // el cuerpo no era JSON con ese formato
        }
        String texto = e.getResponseBodyAsString();
        if (texto != null) {
            String limpio = texto.strip();
            if (!limpio.isEmpty() && limpio.length() <= 200 && !limpio.startsWith("{") && !limpio.startsWith("<")) {
                return limpio;
            }
        }
        return porDefecto;
    }
}
