package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.Rutas.external.GPS_Tool;
import com.Donatrack.Logistica.domain.Direccion;
import java.util.List;
import org.springframework.stereotype.Component;

/** Fachada para planificar una ruta a partir de direcciones de texto. */
@Component
public class GpsTool {
    private final GPS_Tool planificador;

    public GpsTool(GPS_Tool planificador) {
        this.planificador = planificador;
    }

    public Ruta planificarRuta(List<String> direcciones) {
        List<Direccion> destinos = direcciones.stream()
                .map(direccion -> new Direccion(direccion, ""))
                .toList();
        return planificador.planificarRuta(destinos);
    }
}
