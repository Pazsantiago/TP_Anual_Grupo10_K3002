package com.Donatrack.Logistica.services;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.Entrega;
import java.util.List;

public interface ServicioLogistica {
    void distribuirBultos(List<Camion> camiones, List<Bulto> bultos);
    Ruta planificarRuta(List<Direccion> direcciones);
    void iniciarRuta(Ruta ruta);
    void registrarEntrega(Entrega entrega);
}
