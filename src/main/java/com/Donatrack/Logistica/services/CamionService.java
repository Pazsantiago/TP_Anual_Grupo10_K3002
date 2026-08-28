package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.domain.Camion;
import java.util.List;

public interface CamionService {
    List<Camion> obtenerCamiones();
    Camion guardar(Camion camion);
}
