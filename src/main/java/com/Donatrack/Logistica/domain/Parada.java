package com.Donatrack.Logistica.domain;

import java.util.ArrayList;
import java.util.List;

public class Parada {
    private int orden;
    private Direccion direccion;
    private Long idEntidad;
    private String razonSocial;               // snapshot
    private String distancia;
    private String duracion;
    private List<String> entregaIds = new ArrayList<>();

    public Parada() {}

    public Parada(int orden, Direccion direccion, Long idEntidad, String razonSocial) {
        this.orden = orden;
        this.direccion = direccion;
        this.idEntidad = idEntidad;
        this.razonSocial = razonSocial;
    }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public Long getIdEntidad() { return idEntidad; }
    public void setIdEntidad(Long idEntidad) { this.idEntidad = idEntidad; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getDistancia() { return distancia; }
    public void setDistancia(String distancia) { this.distancia = distancia; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public List<String> getEntregaIds() { return entregaIds; }
    public void setEntregaIds(List<String> entregaIds) { this.entregaIds = entregaIds; }
}