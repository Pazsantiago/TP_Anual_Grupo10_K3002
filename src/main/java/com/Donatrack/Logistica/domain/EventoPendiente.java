package com.Donatrack.Logistica.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Evento para el broker que no se pudo enviar (patrón outbox). */
@Document(collection = "eventosPendientes")
public class EventoPendiente {
    @Id private String id;
    private String path;
    private String payload;
    private int intentos = 1;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public EventoPendiente() {}
    public EventoPendiente(String path, String payload) { this.path = path; this.payload = payload; }

    public void registrarIntento() { intentos++; }

    public String getId() { return id; }
    public String getPath() { return path; }
    public String getPayload() { return payload; }
    public int getIntentos() { return intentos; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}