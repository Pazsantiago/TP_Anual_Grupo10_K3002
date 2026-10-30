package com.Donatrack.Logistica.domain;

import java.time.LocalDateTime;

public class UbicacionCamion {
    private double latitud;
    private double longitud;
    private LocalDateTime timestamp;

    public UbicacionCamion() {}
    public UbicacionCamion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.timestamp = LocalDateTime.now();
    }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public LocalDateTime getTimestamp() { return timestamp; }
}