package com.Donatrack.Logistica.integracion;

import java.util.List;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Entrega;

public interface ProveedorRutas {
    void solicitarPlanificacion(String idLote, List<Entrega> entregas, List<Camion> camiones);
}