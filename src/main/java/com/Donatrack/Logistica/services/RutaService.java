package com.Donatrack.Logistica.services;
import com.Donatrack.Logistica.Rutas.domain.RutaRequest;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;

public interface RutaService {
    RutaResponse planificarRuta(RutaRequest request);
    RutaResponse obtenerRutaPorId(Long id);
}