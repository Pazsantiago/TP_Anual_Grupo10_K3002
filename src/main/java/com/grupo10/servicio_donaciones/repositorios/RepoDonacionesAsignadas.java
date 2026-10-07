package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionAsignada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoDonacionesAsignadas extends JpaRepository<DonacionAsignada, Long> {
    Optional<DonacionAsignada> findByNecesidadResueltaId(Long idNecesidad);
}
