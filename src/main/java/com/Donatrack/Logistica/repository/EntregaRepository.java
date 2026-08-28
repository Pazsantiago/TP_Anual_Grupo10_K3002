package com.Donatrack.Logistica.repository;

import com.Donatrack.Logistica.domain.Entrega;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Acceso a persistencia para las entregas. */
@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByEstado(Entrega.Estado estado);
}
