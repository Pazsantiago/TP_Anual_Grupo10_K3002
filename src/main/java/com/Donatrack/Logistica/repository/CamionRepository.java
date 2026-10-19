package com.Donatrack.Logistica.repository;

import com.Donatrack.Logistica.domain.Camion;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CamionRepository extends MongoRepository<Camion, Long> {
    Optional<Camion> findByPatente(String patente);
}
