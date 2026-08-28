package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import java.util.List;
import java.util.Map;

public interface DistribucionDeCargasService {
    Map<Camion, List<Bulto>> asignarBultos(List<Camion> camiones, List<Bulto> bultos);
    boolean validarCapacidad(Camion camion, Bulto bulto);
    double calcularCargaDisponible(Camion camion);
}
