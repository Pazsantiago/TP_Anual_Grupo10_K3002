package com.Donatrack.Logistica.Rutas.domain;

import java.util.List;
import com.Donatrack.Logistica.domain.Direccion;

public class RutaRequest {
    private List<Direccion> direcciones;

    public List<Direccion> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion> direcciones) { this.direcciones = direcciones; }
}

