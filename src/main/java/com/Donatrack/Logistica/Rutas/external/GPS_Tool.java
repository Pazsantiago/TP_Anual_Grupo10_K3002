package com.Donatrack.Logistica.Rutas.external;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import org.springframework.stereotype.Component;
import com.Donatrack.Logistica.domain.Direccion;
import java.util.List;

@Component
public class GPS_Tool {
    public Ruta planificarRuta(List<Direccion> direcciones) {
        Ruta ruta = new Ruta();
        ruta.setDestinos(direcciones);
        ruta.setPasos(List.of("Salida", "Llegada"));
        ruta.setEstado(Ruta.EstadoRuta.PLANIFICADA);
        return ruta;
    }
}
