package com.Donatrack.Logistica.Rutas.service;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.domain.GpsTool;
import com.Donatrack.Logistica.domain.Direccion;

import java.util.List;

public interface GPSService {
    Ruta planificarRuta(List<Direccion> direcciones);
}
