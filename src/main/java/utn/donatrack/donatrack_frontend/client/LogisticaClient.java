package utn.donatrack.donatrack_frontend.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import utn.donatrack.donatrack_frontend.client.dto.logistica.CamionRequest;
import utn.donatrack.donatrack_frontend.client.dto.logistica.DevolucionRequest;
import utn.donatrack.donatrack_frontend.client.dto.logistica.IniciarRutaRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.dto.logistica.BultoResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.CamionResponse;
import utn.donatrack.donatrack_frontend.client.dto.logistica.DonacionPendienteResponse;

/**
 * Servicio de Logistica: flota, bultos, donaciones pendientes, inicio de ruta y recepcion de entregas.
 * Los cuerpos de las escrituras son los *Request de client.dto.logistica.
 */
@Component
public class LogisticaClient extends ClienteServicio {

    public LogisticaClient(@Qualifier("logisticaRestClient") RestClient restClient) {
        super(restClient, "logística");
    }

    /** GET /api/camiones */
    public List<CamionResponse> listarCamiones() {
        return listaDe(() -> http().get()
            .uri("/api/camiones")
            .retrieve()
            .body(new ParameterizedTypeReference<List<CamionResponse>>() {}));
    }

    /** GET /api/bultos/pendientes */
    public List<BultoResponse> listarBultosPendientes() {
        return listaDe(() -> http().get()
            .uri("/api/bultos/pendientes")
            .retrieve()
            .body(new ParameterizedTypeReference<List<BultoResponse>>() {}));
    }

    /** GET /api/donaciones/pendientes */
    public List<DonacionPendienteResponse> listarDonacionesPendientes() {
        return listaDe(() -> http().get()
            .uri("/api/donaciones/pendientes")
            .retrieve()
            .body(new ParameterizedTypeReference<List<DonacionPendienteResponse>>() {}));
    }

    /** POST /api/camiones */
    public void crearCamion(CamionRequest request) {
        ejecutar(() -> http().post().uri("/api/camiones")
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** PUT /api/camiones/{id} */
    public void actualizarCamion(String id, CamionRequest request) {
        ejecutar(() -> http().put().uri("/api/camiones/{id}", id)
            .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().toBodilessEntity());
    }

    /** DELETE /api/camiones/{id} */
    public void darDeBajaCamion(String id) {
        ejecutar(() -> http().delete().uri("/api/camiones/{id}", id).retrieve().toBodilessEntity());
    }

    /** POST /api/rutas/iniciar: las entregas de la ruta pasan a "En traslado". */
    public void iniciarRuta(String idCamion) {
        ejecutar(() -> http().post().uri("/api/rutas/iniciar")
            .contentType(MediaType.APPLICATION_JSON).body(new IniciarRutaRequest(idCamion)).retrieve().toBodilessEntity());
    }

    /**
     * POST /api/donaciones/{id}/recepcion (multipart): la entidad confirma (RECIBIDA, con fotos) o informa (NO_RECIBIDA)
     * lo que paso con la entrega.
     */
    public void registrarRecepcion(String idDonacion, String resultado, String observaciones, List<MultipartFile> fotos) {
        MultiValueMap<String, Object> partes = new LinkedMultiValueMap<>();
        partes.add("resultado", resultado);
        if (observaciones != null && !observaciones.isBlank()) {
            partes.add("observaciones", observaciones.strip());
        }
        for (MultipartFile foto : fotos) {
            if (!foto.isEmpty()) {
                partes.add("fotos", parteMultipart(foto));
            }
        }
        ejecutar(() -> http().post().uri("/api/donaciones/{id}/recepcion", idDonacion)
            .contentType(MediaType.MULTIPART_FORM_DATA).body(partes).retrieve().toBodilessEntity());
    }

    /** POST /api/donaciones/{id}/devolucion: la donacion vuelve al deposito y la entrega queda "Pendiente". */
    public void devolverAlDeposito(String idDonacion, String justificacion) {
        ejecutar(() -> http().post().uri("/api/donaciones/{id}/devolucion", idDonacion)
            .contentType(MediaType.APPLICATION_JSON).body(new DevolucionRequest(justificacion)).retrieve().toBodilessEntity());
    }
}
