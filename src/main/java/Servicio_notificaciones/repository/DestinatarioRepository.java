package Servicio_notificaciones.repository;

import Servicio_notificaciones.dominio.Destinatario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

  Optional<Destinatario> findFirstByNombreAndEmailAndTelefonoAndWhatsapp(
      String nombre, String email, String telefono, String whatsapp);

}
