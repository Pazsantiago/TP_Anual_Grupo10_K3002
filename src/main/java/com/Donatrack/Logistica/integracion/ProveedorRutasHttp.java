package com.Donatrack.Logistica.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Entrega;

@Component
public class ProveedorRutasHttp implements ProveedorRutas {
    private final RestTemplate http;
    private final String url;
    private final String callbackUrl;

    public ProveedorRutasHttp(RestTemplate http, @Value("${proveedor.url}") String url,
                              @Value("${app.callback-url}") String callbackUrl) {
        this.http = http;
        this.url = url;
        this.callbackUrl = callbackUrl;
    }

    public record Solicitud(String idLote, String callbackUrl, List<Don> donaciones, List<Cam> camiones) {
        public record Don(Long idDonacion, Long idEntidad, String direccion) {}
        public record Cam(String patente, double capacidadKg, double capacidadM3, double alturaM) {}
    }

    @Override
    public void solicitarPlanificacion(String idLote, List<Entrega> entregas, List<Camion> camiones) {
        var body = new Solicitud(idLote, callbackUrl,
                entregas.stream().map(e -> new Solicitud.Don(e.getIdDonacion(), e.getIdEntidad(),
                        e.getDireccion().getCalle())).toList(),
                camiones.stream().map(c -> new Solicitud.Cam(c.getPatente(), c.getCapacidadCarga(),
                        c.getCapacidadVolumenM3(), c.getAlturaM())).toList());
        http.postForEntity(url, body, Void.class);
    }
}