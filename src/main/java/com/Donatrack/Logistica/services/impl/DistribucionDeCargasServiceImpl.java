package com.Donatrack.Logistica.services.impl;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.services.DistribucionDeCargasService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DistribucionDeCargasServiceImpl implements DistribucionDeCargasService {

    @Override
    public Map<Camion, List<Bulto>> asignarBultos(List<Camion> camiones, List<Bulto> bultos) {
        Map<Camion, List<Bulto>> asignaciones = new LinkedHashMap<>();
        for (Camion camion : camiones) {
            asignaciones.put(camion, new ArrayList<>());
        }

        for (Bulto bulto : bultos) {
            for (Camion camion : camiones) {
                if (validarCapacidad(camion, bulto)) {
                    camion.asignar(bulto);
                    asignaciones.get(camion).add(bulto);
                    break;
                }
            }
        }
        return asignaciones;
    }

    @Override
    public boolean validarCapacidad(Camion camion, Bulto bulto) {
        return camion.getCargaActual() + bulto.getPeso() <= camion.getCapacidadCarga();
    }

    @Override
    public double calcularCargaDisponible(Camion camion) {
        return camion.getCapacidadCarga() - camion.getCargaActual();
    }
}
