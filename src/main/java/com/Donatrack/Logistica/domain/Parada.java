package com.Donatrack.Logistica.domain;

import java.util.ArrayList;
import java.util.List;

public class Parada {
    private int orden;
    private Direccion direccion;
    private String idEntidad;
    private String razonSocial;               // snapshot
    private List<String> entregaIds = new ArrayList<>();

    public Parada() {}

    public Parada(int orden, Direccion direccion, String idEntidad, String razonSocial) {
        this.orden = orden;
        this.direccion = direccion;
        this.idEntidad = idEntidad;
        this.razonSocial = razonSocial;
    }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public String getIdEntidad() { return idEntidad; }
    public void setIdEntidad(String idEntidad) { this.idEntidad = idEntidad; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public List<String> getEntregaIds() { return entregaIds; }
    public void setEntregaIds(List<String> entregaIds) { this.entregaIds = entregaIds; }
}