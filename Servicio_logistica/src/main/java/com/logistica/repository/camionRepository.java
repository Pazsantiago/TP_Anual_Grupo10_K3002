@Repository
public interface CamionRepository extends JpaRepository<Camion, Long> {
    List<Camion> findByDisponibleTrue();
}