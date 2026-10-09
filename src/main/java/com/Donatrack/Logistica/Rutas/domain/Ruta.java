package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Ruta {

    public enum EstadoRuta {
        REGISTRADA,
        PLANIFICADA,
        EN_TRANSITO
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Direccion> destinos;

    @ElementCollection
    private List<String> pasos;

    @Enumerated(EnumType.STRING)
    private EstadoRuta estado;

    // Getters y Setters
    public Long getId() { return id; }

        public List<Direccion> getDestinos() { return destinos; }
    public void setDestinos(List<Direccion> destinos) { this.destinos = destinos; }

    public List<String> getPasos() { return pasos; }
    public void setPasos(List<String> pasos) { this.pasos = pasos; }

    public EstadoRuta getEstado() { return estado; }
    public void setEstado(EstadoRuta estado) { this.estado = estado; }
}
