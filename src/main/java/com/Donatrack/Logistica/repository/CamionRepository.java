package com.Donatrack.Logistica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Donatrack.Logistica.domain.Camion;

public interface CamionRepository extends MongoRepository<Camion, String> {
    Optional<Camion> findByPatente(String patente);
    List<Camion> findByDisponibleTrue();
}