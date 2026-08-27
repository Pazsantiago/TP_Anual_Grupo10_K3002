@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByEstado(String estado);
}