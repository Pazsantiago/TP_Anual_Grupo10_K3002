package com.Donatrack.Logistica.repository;

import com.Donatrack.Logistica.domain.Entrega;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/** Acceso a persistencia para las entregas. */
@Repository
public interface EntregaRepository extends MongoRepository<Entrega, Long> {
    List<Entrega> findByEstado(Entrega.Estado estado);
}
