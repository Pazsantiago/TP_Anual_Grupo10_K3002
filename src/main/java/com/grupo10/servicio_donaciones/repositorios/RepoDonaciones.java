package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.TipoEstadoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoDonaciones extends JpaRepository<Donacion, Long> {
    Optional<Donacion> findByDonacionesSegmentadasId(Long idSegmentada);

    @Query("SELECT ds from DonacionSegmentada ds")
    List<DonacionSegmentada> getAllSegmentadas();

    @Query("SELECT ds from DonacionSegmentada ds where ds.estadoActual.tipoEstado = :estado")
    List<DonacionSegmentada> obtenerDonacionesSegmentadasPorEstado(@Param("estado") TipoEstadoDonacion estado);

    @Query("SELECT ds from DonacionSegmentada ds where ds.id = :idSegmentada")
    Optional<DonacionSegmentada> findByDonacionSegmentadaId(@Param("idSegmentada") Long idSegmentada);
}
