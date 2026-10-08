package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.Rutas.domain.RutaRequest;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import com.Donatrack.Logistica.domain.Bulto;
import java.util.List;

public interface BultoService {
    List<Bulto> obtenerBultosPendientes();
    Bulto guardar(Bulto bulto);

    interface RutaService {
        RutaResponse planificarRuta(RutaRequest request);
        RutaResponse obtenerRutaPorId(Long id);
    }
}
