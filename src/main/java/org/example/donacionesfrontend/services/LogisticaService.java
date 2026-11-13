package org.example.donacionesfrontend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LogisticaService {

    private final RestClient restClient;

    public LogisticaService(@Qualifier("restClientLogistica") RestClient restClient) {
        this.restClient = restClient;
    }

    private static final ParameterizedTypeReference<List<Map<String, Object>>> LISTA =
            new ParameterizedTypeReference<>() {};

    // ---- camiones ----
    public List<Map<String, Object>> camiones() {
        return restClient.get().uri("/api/camiones").retrieve().body(LISTA);
    }

    public void crearCamion(String patente, double carga, double volumen, double altura) {
        restClient.post().uri("/api/camiones")
                .body(Map.of("patente", patente, "capacidadCarga", carga,
                        "capacidadVolumenM3", volumen, "alturaM", altura))
                .retrieve().toBodilessEntity();
    }

    public void setDisponible(String patente, boolean disponible) {
        restClient.put().uri("/api/camiones/{p}/disponible", patente)
                .body(Map.of("disponible", disponible)).retrieve().toBodilessEntity();
    }

    public void actualizarUbicacion(String patente, double lat, double lng) {
        restClient.put().uri("/api/camiones/{p}/ubicacion", patente)
                .body(Map.of("latitud", lat, "longitud", lng)).retrieve().toBodilessEntity();
    }

    // ---- rutas ----
    public List<Map<String, Object>> rutas(LocalDate fecha) {
        return restClient.get().uri("/api/rutas?fecha={f}", fecha).retrieve().body(LISTA);
    }

    public void iniciarRuta(String id) {
        restClient.post().uri("/api/rutas/{id}/iniciar", id).retrieve().toBodilessEntity();
    }

    // ---- seguimiento ----
    public List<Map<String, Object>> seguimientoEntidad(Long idEntidad) {
        return restClient.get().uri("/api/seguimiento/entidades/{id}", idEntidad).retrieve().body(LISTA);
    }

    // ---- entregas ----
    public List<Map<String, Object>> entregasEntidad(Long idEntidad) {
        return restClient.get().uri("/api/entidades/{id}/entregas", idEntidad).retrieve().body(LISTA);
    }

    public void confirmar(String id, String foto) {
        restClient.post().uri("/api/entregas/{id}/confirmar", id).retrieve().toBodilessEntity();
        if (foto != null && !foto.isBlank()) {
            restClient.post().uri("/api/entregas/{id}/fotos", id)
                    .body(Map.of("fotos", List.of(foto))).retrieve().toBodilessEntity();
        }
    }

    public void noRecibida(String id, String motivo) {
        restClient.post().uri("/api/entregas/{id}/no-recibida", id)
                .body(Map.of("motivo", motivo)).retrieve().toBodilessEntity();
    }
}