package com.Donatrack.Logistica.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.domain.EstadoPlan;
import com.Donatrack.Logistica.domain.PlanRuta;
import com.Donatrack.Logistica.dto.Mensajes.*;
import com.Donatrack.Logistica.dto.Mensajes.MensajeConfirmacionEntrega;
import com.Donatrack.Logistica.dto.Mensajes.MensajeEntregaFallida;
import com.Donatrack.Logistica.dto.Mensajes.MensajeInicioDeRuta;
import com.Donatrack.Logistica.integracion.NotificadorBroker;
import com.Donatrack.Logistica.repository.EntregaRepository;
import com.Donatrack.Logistica.repository.PlanRutaRepository;

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

        broker.inicioDeRuta(new MensajeInicioDeRuta(plan.getId(), plan.getPatenteCamion(),
                entregas.stream().map(Entrega::getIdDonacion).toList(),
                entregas.stream().map(Entrega::getIdEntidad).distinct().toList(),
                plan.getFechaInicio()));
        return plan;
    }

    /** La entidad confirma la recepción. */
    public Entrega confirmarRecepcion(String entregaId) {
        Entrega e = entrega(entregaId);
        e.marcarEntregada();
        e = entregaRepo.save(e);
        cerrarPlanSiCorresponde(e.getPlanRutaId());

        broker.entregaConfirmada(new MensajeConfirmacionEntrega(e.getIdDonacion(), e.getIdDonantes(),
                List.of(), e.getIdEntidad(), e.getPatenteCamion(), e.getFechaEntrega()));
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

        broker.entregaFallida(new MensajeEntregaFallida(e.getIdDonacion(), e.getIdDonantes(),
                e.getIdEntidad(), motivo, LocalDateTime.now()));
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