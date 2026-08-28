package com.Donatrack.Logistica.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {
    private String calle;
    private String ciudad;

    protected Direccion() {
    }

    public Direccion(String calle, String ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}
