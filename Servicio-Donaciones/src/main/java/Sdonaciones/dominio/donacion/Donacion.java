package org.example.dominio.donacion;

import org.example.dominio.donador.D;
import org.example.dominio.bien.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa la carga completa ingresada por un donador en una visita al depósito.
 * Contiene la lista de DonacionesSegmentadas generadas automáticamente por el sistema.
 *
 * El administrador registra la Donacion; el sistema la segmenta por subcategoría.
 */
public class Donacion {

    private final String id;
    private final Donador donador;
    private final String idAdministrador;
    private final String descripcion;
    private final LocalDateTime fechaRegistro;
    private final List<Bien> bienes;

    public Donacion(Donador donador, String idAdministrador, String descripcion) {
        if (donador == null)
            throw new IllegalArgumentException("El donador es obligatorio.");
        if (idAdministrador == null || idAdministrador.isBlank())
            throw new IllegalArgumentException("El administrador es obligatorio.");

        this.id = UUID.randomUUID().toString();
        this.donador = donador;
        this.idAdministrador = idAdministrador;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
        this.donacionesSegmentadas = new ArrayList<>();
    }


    public String getId() { return id; }
    public Donador getDonador() { return donador; }
    public String getIdAdministrador() { return idAdministrador; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public List<DonacionSegmentada> getDonacionesSegmentadas() {
        return List.copyOf(donacionesSegmentadas);
    }
}
