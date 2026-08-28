package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import java.util.List;

public class DistribucionRequest {
    private List<Camion> camiones;
    private List<Bulto> bultos;

    public List<Camion> getCamiones() { return camiones; }
    public void setCamiones(List<Camion> camiones) { this.camiones = camiones; }
    public List<Bulto> getBultos() { return bultos; }
    public void setBultos(List<Bulto> bultos) { this.bultos = bultos; }
}
