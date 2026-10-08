package com.Donatrack.Logistica.services.impl;
import org.springframework.stereotype.Service;
import com.Donatrack.Logistica.Rutas.domain.RutaRequest;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import com.Donatrack.Logistica.services.RutaService;

@Service
public class RutaServiceImpl implements RutaService {

    @Override
    public RutaResponse planificarRuta(RutaRequest request) {
        // lógica de planificación
        return new RutaResponse();
    }

    @Override
    public RutaResponse obtenerRutaPorId(Long id) {
        // lógica de búsqueda
        return new RutaResponse();
    }
}
