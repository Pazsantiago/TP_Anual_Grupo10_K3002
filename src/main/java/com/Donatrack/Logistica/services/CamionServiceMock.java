package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.domain.Camion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock")
public class CamionServiceMock implements CamionService {
    private final List<Camion> camiones = new ArrayList<>(List.of(
            new Camion("ABC123", 5000),
            new Camion("XYZ789", 8000)
    ));

    @Override
    public List<Camion> obtenerCamiones() {
        return List.copyOf(camiones);
    }

    @Override
    public Camion guardar(Camion camion) {
        camiones.add(camion);
        return camion;
    }
}
