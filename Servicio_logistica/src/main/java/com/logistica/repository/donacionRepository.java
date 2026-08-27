@Repository
public interface donacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByEstado(String estado);
}