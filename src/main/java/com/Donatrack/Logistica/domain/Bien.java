package com.Donatrack.Logistica.domain;

import jakarta.persistence.*;

@Entity
public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // clave primaria
    private String nombre;
    private int cantidad;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "bulto_id") // FK hacia la tabla bulto
    private Bulto bulto;

    // Constructor vacío obligatorio para JPA
    public Bien() {}

    // Constructor con validaciones


    public Bien(String nombre, int cantidad, String tipo, Bulto bulto) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del bien es obligatorio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.bulto = bulto;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bulto getBulto() {
        return bulto;
    }

    public void setBulto(Bulto bulto) {
        this.bulto = bulto;
    }
}

