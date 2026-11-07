package com.Donatrack.Logistica.service;

import com.Donatrack.Logistica.domain.*;
import com.Donatrack.Logistica.dto.Mensajes.*;
import com.Donatrack.Logistica.integracion.NotificadorBroker;
import com.Donatrack.Logistica.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RutaService {
    private final PlanRutaRepository planRepo;
    private final EntregaRepository entregaRepo;
    private final NotificadorBroker broker;

    public RutaService(PlanRutaRepository planRepo, EntregaRepository entregaRepo, NotificadorBroker broker) {
        this.planRepo = planRepo;
        this.entregaRepo = entregaRepo;
        this.broker = broker;
    }

    public List<PlanRuta> rutasDelDia(LocalDate fecha) { return planRepo.findByFecha(fecha); }

    /** El chofer inicia su ruta. Valida todo antes de escribir. */
    public PlanRuta iniciarRuta(String planId) {
        PlanRuta plan = plan(planId);
        plan.iniciar();
        List<Entrega> entregas = entregaRepo.findAllById(plan.entregaIds());
        entregas.forEach(Entrega::iniciarTraslado);
        entregaRepo.saveAll(entregas);
        plan = planRepo.save(plan);

        entregas.forEach(x -> broker.inicioDeRuta(new MensajeInicioDeRuta(x.getIdDonacion())));
        return plan;
    }

    /** La entidad confirma la recepción. */
    public Entrega confirmarRecepcion(String entregaId) {
        Entrega e = entrega(entregaId);
        e.marcarEntregada();
        e = entregaRepo.save(e);
        cerrarPlanSiCorresponde(e.getPlanRutaId());

        Date fecha = Date.from(e.getFechaEntrega().atZone(ZoneId.systemDefault()).toInstant());
        broker.entregaConfirmada(new MensajeConfirmacionEntrega(e.getIdDonacion(), e.getIdEntidad(),
                e.getIdSegmentadas(), e.getNroComprobante(), fecha, null)); // idCamionAsignado: a acordar
        return e;
    }

    public Entrega agregarFotos(String entregaId, List<String> fotos) {
        Entrega e = entrega(entregaId);
        e.agregarFotos(fotos);
        return entregaRepo.save(e);
    }

    /** La entidad informa que no recibió la entrega. */
    public Entrega informarNoRecibida(String entregaId, String motivo) {
        Entrega e = entrega(entregaId);
        e.marcarNoRecibida(motivo);
        e = entregaRepo.save(e);
        cerrarPlanSiCorresponde(e.getPlanRutaId());

        broker.entregaFallida(new MensajeEntregaFallida(e.getIdDonacion(), motivo, true));
        return e;
    }

    /** Un admin confirma que la donación volvió al depósito: queda lista para replanificar. */
    public Entrega reponerEnDeposito(String entregaId) {
        Entrega e = entrega(entregaId);
        e.volverAPendiente();
        return entregaRepo.save(e);
    }

    private void cerrarPlanSiCorresponde(String planId) {
        if (planId == null) return;
        boolean quedanEnTraslado = entregaRepo.findByPlanRutaId(planId).stream()
                .anyMatch(x -> x.getEstado() == EstadoEntrega.EN_TRASLADO);
        if (quedanEnTraslado) return;
        planRepo.findById(planId).ifPresent(p -> {
            if (p.getEstado() == EstadoPlan.EN_CURSO) { p.finalizar(); planRepo.save(p); }
        });
    }

    private PlanRuta plan(String id) {
        return planRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Ruta no encontrada: " + id));
    }

    private Entrega entrega(String id) {
        return entregaRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Entrega no encontrada: " + id));
    }
}