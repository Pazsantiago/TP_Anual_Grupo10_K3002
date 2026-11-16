package com.grupo10.servicio_donaciones.repositorios;

import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.TipoEstadoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoDonaciones extends JpaRepository<Donacion, Long> {
    Optional<Donacion> findByDonacionesSegmentadasId(Long idSegmentada);

    @Query("SELECT ds from DonacionSegmentada ds " +
            "JOIN FETCH ds.donacionInicial di " +
            "JOIN FETCH di.donante " +
            "where di.donante.id = :idDonante")
    List<DonacionSegmentada> getAllSegmentadasWithIdDonacionAndIdDonante(@Param("idDonante") Long idDonante);

    @Query("SELECT ds from DonacionSegmentada ds " +
            "JOIN FETCH ds.donacionInicial di " +
            "JOIN FETCH di.donante " +
            "JOIN FETCH ds.estadoActual " +
            "where ds.estadoActual.tipoEstado = :estadoDonacion and di.donante.id = :idDonante")
    List<DonacionSegmentada> getAllSegmentadasWithEstadoDonacionAndIdDonante(@Param("idDonante") Long idDonante, @Param("estadoDonacion") TipoEstadoDonacion estadoDonacion);

    @Query("SELECT ds from DonacionSegmentada ds " +
            "JOIN FETCH ds.donacionInicial di " +
            "JOIN FETCH di.donante " +
            "JOIN FETCH ds.subcategoria " +
            "where ds.subcategoria.nombre = :subcategoria and di.donante.id = :idDonante")
    List<DonacionSegmentada> getAllSegmentadasWithSubcategoriaAndIdDonante(@Param("idDonante") Long idDonante, @Param("subcategoria") String subcategoria);

    @Query("SELECT ds from DonacionSegmentada ds " +
            "JOIN FETCH ds.subcategoria sc " +
            "JOIN FETCH sc.categoria " +
            "where sc.categoria.id = :idCategoria and ds.donacionInicial.donante.id = :idDonante")
    List<DonacionSegmentada> getAllSegmentadasWithCategoriaAndIdDonante(@Param("idDonante") Long idDonante, @Param("idCategoria") Long idCategoria);


    @Query("SELECT ds from DonacionSegmentada ds where ds.estadoActual.tipoEstado = :estado")
    List<DonacionSegmentada> obtenerDonacionesSegmentadasPorEstado(@Param("estado") TipoEstadoDonacion estado);


    @Query("SELECT ds from DonacionSegmentada ds where ds.id = :idSegmentada")
    Optional<DonacionSegmentada> findByDonacionSegmentadaId(@Param("idSegmentada") Long idSegmentada);

    List<Donacion> findByDonanteId(Long idDonacion);
}
