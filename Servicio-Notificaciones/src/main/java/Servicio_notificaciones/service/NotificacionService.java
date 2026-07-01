package Servicio_notificaciones.service;

import Servicio_notificaciones.DTOs.request.LoteNotificacionRequestDTO;
import Servicio_notificaciones.DTOs.request.NotificacionRequestDTO;
import Servicio_notificaciones.dominio.Destinatario;
import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import Servicio_notificaciones.repository.NotificacionRepository;
import Servicio_notificaciones.strategy.INotificador;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.management.NotificationFilter;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

  private final NotificacionRepository repository;
  private final Map<MedioNotificacion, INotificador> canales;

  public NotificacionService(
      NotificacionRepository repository,
      List<INotificador> canales
  ) {
    this.repository = repository;
    this.canales = canales.stream()
        .collect(Collectors.toMap(INotificador::medio, Function.identity()));
  }

  public Notificacion enviar(NotificacionRequestDTO notificacionRequest) {
    validarContacto(notificacionRequest);

    Destinatario destinatario = new Destinatario(
        notificacionRequest.getDestinatario().getNombre(),
        notificacionRequest.getDestinatario().getEmail(),
        notificacionRequest.getDestinatario().getTelefono(),
        notificacionRequest.getDestinatario().getWhatsapp()
    );

    Notificacion notificacion = new Notificacion(
        destinatario,
        notificacionRequest.getMedioNotificacion(),
        notificacionRequest.getAsunto(),
        notificacionRequest.getCuerpo()
    );

    repository.save(notificacion);

    try {
      INotificador notificador = canales.get(notificacionRequest.getMedioNotificacion());

      if (notificador == null) {
        throw new IllegalArgumentException("No existe canal para el medio: " + notificacionRequest.getMedioNotificacion());
      }

      notificador.enviar(notificacion);
      notificacion.marcarCompletada();

    } catch (Exception e) {
      notificacion.marcarFallida(e.getMessage());
    }

    return repository.save(notificacion);

  }

  public List<Notificacion> enviarLote(LoteNotificacionRequestDTO request) {
    return request.getNotificaciones()
        .stream()
        .map(this::enviar)
        .toList();
  }

  public List<Notificacion> obtenerTodas() {
    return repository.findAll();
  }

  public Notificacion obtenerPorId(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No existe la notificación con id: " + id));
  }

  private void validarContacto(NotificacionRequestDTO request) {
    MedioNotificacion medio = request.getMedioNotificacion();

    boolean contactoValido = switch (medio) {
      case EMAIL -> request.getDestinatario().getEmail() != null && !request.getDestinatario().getEmail().isBlank();
      case SMS -> request.getDestinatario().getTelefono() != null && !request.getDestinatario().getTelefono().isBlank();
      case WHATSAPP -> request.getDestinatario().getWhatsapp() != null && !request.getDestinatario().getWhatsapp().isBlank();
    };

    if (!contactoValido) {
      throw new IllegalArgumentException("El destinatario no posee dato de contacto válido para el medio " + medio);
    }
  }

}
