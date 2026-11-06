    package com.Donatrack.Logistica.repository;

    import java.util.Collection;
    import java.util.List;
    import java.util.Optional;

    import org.springframework.data.mongodb.repository.MongoRepository;

    import com.Donatrack.Logistica.domain.Entrega;
    import com.Donatrack.Logistica.domain.EstadoEntrega;

    public interface EntregaRepository extends MongoRepository<Entrega, String> {
        Optional<Entrega> findByIdDonacion(Long idDonacion);
        List<Entrega> findByIdDonacionIn(Collection<Long> ids);
        List<Entrega> findByPlanRutaId(String planRutaId);
        List<Entrega> findByEstadoAndPlanRutaIdIsNull(EstadoEntrega estado);
        List<Entrega> findByIdDonantesAndEstado(Long idDonante, EstadoEntrega estado);
        List<Entrega> findByIdEntidadAndEstado(Long idEntidad, EstadoEntrega estado);
    }