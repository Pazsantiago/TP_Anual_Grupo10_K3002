package com.Donatrack.Logistica.repository;

import com.Donatrack.Logistica.domain.Donacion;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByEstadoDonacion(EstadoEntrega estado);
}
