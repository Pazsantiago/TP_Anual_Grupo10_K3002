package com.Donatrack.Logistica.repository;

import com.Donatrack.Logistica.domain.Camion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    Optional<Camion> findByPatente(String patente);
}
