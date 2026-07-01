package Servicio_notificaciones.repository;

import Servicio_notificaciones.dominio.Notificacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import jdk.jfr.Registered;

public class NotificacionRepository {

    private final ConcurrentHashMap<UUID, Notificacion> notificaciones = new ConcurrentHashMap<>();

    public Notificacion save(Notificacion notificacion) {
      notificaciones.put(notificacion.getId(), notificacion);
      return notificacion;
    }

    public Optional<Notificacion> findById(UUID id) {
      return Optional.ofNullable(notificaciones.get(id));
    }

    public List<Notificacion> findAll() {
      return new ArrayList<>(notificaciones.values());
    }

}
