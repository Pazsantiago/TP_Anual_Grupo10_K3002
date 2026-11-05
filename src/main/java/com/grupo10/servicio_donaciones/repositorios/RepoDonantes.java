package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoDonantes extends JpaRepository<Donante, Long> {
    Optional<Donante> findByMediosDeContactoCorreoElectronico(String correo);

    @Query("SELECT d FROM Donante d JOIN d.persona p JOIN p.documento doc WHERE doc.tipoDocumento = :tipoD AND doc.documento = :doc")
    Optional<Donante> findByDoc(@Param("tipoD") String tipoD, @Param("doc") String doc);


    @Query("SELECT d from Donante d LEFT JOIN FETCH d.donaciones where d.id = :id")
    Optional<Donante> findByIdWithDonaciones(@Param("id") Long id);
}
