package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoDonantes extends JpaRepository<Donante, Long> {
    Optional<Donante> findByMediosDeContactoCorreoElectronico(String correo);

    @Query("SELECT d from Donante d where d.persona.documento.tipoDocumento = :tipoD and d.persona.documento.documento = :doc")
    Optional<Donante> findByDoc(@Param("tipoD") String tipoD, @Param("doc") String doc);

    @EntityGraph(attributePaths = {"donaciones"})
    @Query("SELECT d from Donante d where d.id = :id")
    Optional<Donante> findByIdWithDonaciones(@Param("id") Long id);
}
