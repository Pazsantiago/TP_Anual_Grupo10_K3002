package com.Donatrack.Logistica.services.impl;

import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.repository.EntregaRepository;
import java.time.LocalDateTime;
import java.util.List;

import com.Donatrack.Logistica.services.DistribucionDeCargasService;
import com.Donatrack.Logistica.services.ServicioLogistica;
import org.springframework.stereotype.Service;

@Service
public class ServicioLogisticoImpl implements ServicioLogistica {
    private final DistribucionDeCargasService distribucionDeCargasService;
    private final GPSService gpsService;
    private final EntregaRepository entregaRepository;

    public ServicioLogisticoImpl(DistribucionDeCargasService distribucionDeCargasService,
                                 GPSService gpsService,
                                 EntregaRepository entregaRepository) {
        this.distribucionDeCargasService = distribucionDeCargasService;
        this.gpsService = gpsService;
        this.entregaRepository = entregaRepository;
    }

    @Override
    public void distribuirBultos(List<Camion> camiones, List<Bulto> bultos) {
        long asignados = distribucionDeCargasService.asignarBultos(camiones, bultos)
                .values().stream().mapToLong(List::size).sum();
        if (asignados != bultos.size()) {
            throw new IllegalStateException("No hay capacidad disponible para todos los bultos");
        }
    }

    @Override
    public Ruta planificarRuta(List<Direccion> direcciones) {
        return gpsService.planificarRuta(direcciones);
    }

    @Override
    public void iniciarRuta(Ruta ruta) {
        ruta.setEstado(Ruta.EstadoRuta.EN_TRANSITO);
    }

    @Override
    public void registrarEntrega(Entrega entrega) {
        entrega.setEstado(Entrega.Estado.ENTREGADA);
        entrega.setFechaEntrega(LocalDateTime.now());
        entregaRepository.save(entrega);
    }
}
