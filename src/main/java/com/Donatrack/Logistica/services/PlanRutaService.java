package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.PlanRuta;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlanRutaService {

    public PlanRuta generarPlanRuta(List<Direccion> direcciones) {
        PlanRuta planRuta = new PlanRuta();
        planRuta.setDirecciones(direcciones);
        planRuta.setDistanciaTotal(0.0);
        planRuta.setTiempoEstimado("Pendiente de cálculo");
        return planRuta;
    }
}
