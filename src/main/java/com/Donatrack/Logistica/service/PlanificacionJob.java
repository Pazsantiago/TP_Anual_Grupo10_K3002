package com.Donatrack.Logistica.service;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Corre en horario de baja carga y planifica la jornada siguiente. */
@Component
public class PlanificacionJob {
    private final PlanificacionService service;

    public PlanificacionJob(PlanificacionService service) { this.service = service; }

    @Scheduled(cron = "0 0 2 * * *")
    public void ejecutar() { service.planificarJornada(LocalDate.now().plusDays(1)); }
}