package com.Donatrack.Logistica.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Donatrack.Logistica.domain.EstadoPlan;
import com.Donatrack.Logistica.domain.PlanRuta;

public interface PlanRutaRepository extends MongoRepository<PlanRuta, String> {
    List<PlanRuta> findByFecha(LocalDate fecha);
    Optional<PlanRuta> findByFechaAndPatenteCamion(LocalDate fecha, String patente);
    List<PlanRuta> findByEstado(EstadoPlan estado);
}