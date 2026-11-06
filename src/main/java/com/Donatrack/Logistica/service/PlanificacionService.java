package com.Donatrack.Logistica.service;

import com.Donatrack.Logistica.domain.*;
import com.Donatrack.Logistica.dto.ResultadoPlanificacionDTO;
import com.Donatrack.Logistica.dto.ResultadoPlanificacionDTO.*;
import com.Donatrack.Logistica.integracion.ProveedorRutas;
import com.Donatrack.Logistica.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlanificacionService {
    public static final int TAMANIO_LOTE = 100;
    private static final Logger log = LoggerFactory.getLogger(PlanificacionService.class);

    private final EntregaRepository entregaRepo;
    private final CamionRepository camionRepo;
    private final PlanRutaRepository planRepo;
    private final LoteRutaRepository loteRepo;
    private final ProveedorRutas proveedor;
    private final DistribucionService distribucion;

    public PlanificacionService(EntregaRepository entregaRepo, CamionRepository camionRepo,
                                PlanRutaRepository planRepo, LoteRutaRepository loteRepo,
                                ProveedorRutas proveedor, DistribucionService distribucion) {
        this.entregaRepo = entregaRepo;
        this.camionRepo = camionRepo;
        this.planRepo = planRepo;
        this.loteRepo = loteRepo;
        this.proveedor = proveedor;
        this.distribucion = distribucion;
    }

    /** Envía al proveedor las entregas pendientes sin ruta, en lotes de 100. */
    public List<LoteRuta> planificarJornada(LocalDate fecha) {
        List<Entrega> pendientes = entregaRepo.findByEstadoAndPlanRutaIdIsNull(EstadoEntrega.PENDIENTE);
        if (pendientes.isEmpty()) return List.of();

        List<Camion> camiones = camionRepo.findByDisponibleTrue();
        if (camiones.isEmpty()) throw new IllegalStateException("No hay camiones disponibles");

        // Prefiltro de capacidad: solo para entregas con datos de carga
        List<Entrega> sinCarga = pendientes.stream().filter(e -> e.getBultos().isEmpty()).toList();
        List<Entrega> conCarga = pendientes.stream().filter(e -> !e.getBultos().isEmpty()).toList();
        List<Entrega> aPlanificar = new ArrayList<>(sinCarga);
        if (!conCarga.isEmpty()) {
            DistribucionService.Resultado dist = distribucion.distribuir(camiones, conCarga);
            if (!dist.sinAsignar().isEmpty())
                log.warn("{} entregas no entran en la flota y quedan pendientes", dist.sinAsignar().size());
            dist.porCamion().values().forEach(aPlanificar::addAll);
        }
        if (aPlanificar.isEmpty()) return List.of();

        List<LoteRuta> lotes = new ArrayList<>();
        for (int i = 0; i < aPlanificar.size(); i += TAMANIO_LOTE) {
            List<Entrega> parte = aPlanificar.subList(i, Math.min(i + TAMANIO_LOTE, aPlanificar.size()));
            LoteRuta lote = loteRepo.save(new LoteRuta(fecha, parte.stream().map(Entrega::getId).toList()));
            try {
                proveedor.solicitarPlanificacion(lote.getId(), parte, camiones);
            } catch (RuntimeException ex) {   // un lote fallido no frena a los demás
                log.error("Falló el lote {}: {}", lote.getId(), ex.getMessage());
                lote.marcarError(ex.getMessage());
                lote = loteRepo.save(lote);
            }
            lotes.add(lote);
        }
        return lotes;
    }

    /** Callback del proveedor: crea o completa los PlanRuta y asigna las entregas. */
    public void registrarResultado(ResultadoPlanificacionDTO r) {
        LoteRuta lote = loteRepo.findById(r.idLote())
                .orElseThrow(() -> new NoSuchElementException("Lote no encontrado: " + r.idLote()));
        if (lote.getEstado() == LoteRuta.Estado.RESPONDIDO) return;   // idempotente

        Map<Long, Entrega> porDonacion = entregaRepo.findAllById(lote.getEntregaIds()).stream()
                .collect(Collectors.toMap(Entrega::getIdDonacion, Function.identity()));

        // 1) validar todo antes de escribir
        Map<String, Camion> camiones = new HashMap<>();
        for (RutaCamionDTO rc : r.rutas()) {
            camiones.put(rc.patente(), camionRepo.findByPatente(rc.patente())
                    .orElseThrow(() -> new NoSuchElementException("Camión no encontrado: " + rc.patente())));
            for (DestinoDTO d : rc.destinos())
                for (Long idDon : d.idDonaciones())
                    if (!porDonacion.containsKey(idDon))
                        throw new IllegalArgumentException("La donación " + idDon + " no pertenece al lote");
        }

        // 2) escribir
        for (RutaCamionDTO rc : r.rutas()) {
            Camion camion = camiones.get(rc.patente());
            PlanRuta plan = planRepo.findByFechaAndPatenteCamion(lote.getFecha(), rc.patente())
                    .orElseGet(() -> new PlanRuta(lote.getFecha(), camion.getId(), camion.getPatente(),
                            new ArrayList<>(), 0.0, ""));
            if (plan.getEstado() != EstadoPlan.PLANIFICADA)
                throw new IllegalStateException("La ruta de " + rc.patente() + " ya fue iniciada");

            plan.setIdLote(lote.getId());
            plan = planRepo.save(plan);                       // necesito el id para las entregas

            int orden = plan.getParadas().size();
            List<Entrega> asignadas = new ArrayList<>();
            for (DestinoDTO d : rc.destinos()) {
                Parada p = new Parada(++orden, new Direccion(d.direccion(), ""), d.idEntidad(), d.razonSocial());
                p.setDistancia(d.distancia());
                p.setDuracion(d.duracion());
                for (Long idDon : d.idDonaciones()) {
                    Entrega e = porDonacion.get(idDon);
                    e.asignarAPlan(plan.getId(), camion.getPatente());
                    p.getEntregaIds().add(e.getId());
                    asignadas.add(e);
                }
                plan.getParadas().add(p);
            }
            if (rc.distanciaTotal() != null)
                plan.setDistanciaTotal((plan.getDistanciaTotal() == null ? 0 : plan.getDistanciaTotal()) + rc.distanciaTotal());
            plan.setTiempoEstimado(rc.tiempoEstimado());
            entregaRepo.saveAll(asignadas);
            planRepo.save(plan);
        }
        lote.marcarRespondido();
        loteRepo.save(lote);
    }
}