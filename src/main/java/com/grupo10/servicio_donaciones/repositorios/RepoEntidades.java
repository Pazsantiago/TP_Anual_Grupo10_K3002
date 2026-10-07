package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoEntidades extends JpaRepository<EntidadBeneficiaria, Long> {
    @Query("SELECT n FROM Necesidad n")
    List<Necesidad> findNecesidades();

    @Query("SELECT n FROM Necesidad n where n.id = :idNecesidad")
    Optional<Necesidad> findNecesidadById(@Param("idNecesidad") Long idNecesidad);


}
