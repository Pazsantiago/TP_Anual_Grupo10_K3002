package com.Donatrack.Logistica.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.Donatrack.Logistica.domain.Donacion;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import java.util.List;


public interface DonacionRepository extends MongoRepository<Donacion, Long> {
    List<Donacion> findByEstadoDonacion(EstadoEntrega estado);
}
