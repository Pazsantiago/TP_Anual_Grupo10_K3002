package Servicio_notificaciones.repository;

import Servicio_notificaciones.dominio.Destinatario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

  /**
   * Busca un destinatario con exactamente los mismos datos (los null se comparan con IS NULL),
   * para reutilizarlo en vez de crear un duplicado en cada notificación.
   */
  Optional<Destinatario> findFirstByNombreAndEmailAndTelefonoAndWhatsapp(
      String nombre, String email, String telefono, String whatsapp);

}
