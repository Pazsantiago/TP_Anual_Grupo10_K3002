package com.Donatrack.Logistica.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.Donatrack.Logistica.domain.EventoPendiente;
import com.Donatrack.Logistica.dto.Mensajes.*;
import com.Donatrack.Logistica.dto.Mensajes.MensajeConfirmacionEntrega;
import com.Donatrack.Logistica.dto.Mensajes.MensajeEntregaFallida;
import com.Donatrack.Logistica.dto.Mensajes.MensajeInicioDeRuta;
import com.Donatrack.Logistica.repository.EventoPendienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BrokerHttpNotificador implements NotificadorBroker {
    private static final Logger log = LoggerFactory.getLogger(BrokerHttpNotificador.class);

    private final RestTemplate http;
    private final String baseUrl;
    private final EventoPendienteRepository pendientes;
    private final ObjectMapper mapper;

    public BrokerHttpNotificador(RestTemplate http, @Value("${broker.url}") String baseUrl,
                                 EventoPendienteRepository pendientes, ObjectMapper mapper) {
        this.http = http;
        this.baseUrl = baseUrl;
        this.pendientes = pendientes;
        this.mapper = mapper;
    }

    @Override public void inicioDeRuta(MensajeInicioDeRuta m) { enviar("/api/iniciosDeRutas", m); }
    @Override public void entregaConfirmada(MensajeConfirmacionEntrega m) { enviar("/api/entregasConfirmadas", m); }
    @Override public void entregaFallida(MensajeEntregaFallida m) { enviar("/api/entregasFallidas", m); }

    /** Nunca lanza: si el broker no responde, el evento queda guardado para reintentar. */
    private void enviar(String path, Object mensaje) {
        String json;
        try { json = mapper.writeValueAsString(mensaje); }
        catch (JsonProcessingException e) { throw new IllegalStateException(e); }
        if (!intentar(path, json)) pendientes.save(new EventoPendiente(path, json));
    }

    private boolean intentar(String path, String json) {
        try {
            HttpHeaders h = new HttpHeaders();
            h.setContentType(MediaType.APPLICATION_JSON);
            http.postForEntity(baseUrl + path, new HttpEntity<>(json, h), String.class);
            return true;
        } catch (RestClientException e) {
            log.warn("Broker no disponible para {}: {}", path, e.getMessage());
            return false;
        }
    }

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
    public void reintentar() {
        for (EventoPendiente ev : pendientes.findAll(Sort.by("fechaCreacion"))) {
            if (intentar(ev.getPath(), ev.getPayload())) pendientes.delete(ev);
            else { ev.registrarIntento(); pendientes.save(ev); }
        }
    }
}