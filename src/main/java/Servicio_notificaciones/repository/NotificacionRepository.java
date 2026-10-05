package Servicio_notificaciones.repository;

import Servicio_notificaciones.dominio.Notificacion;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {

}
