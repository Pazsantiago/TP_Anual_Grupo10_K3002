package Servicio_notificaciones.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import Servicio_notificaciones.DTOs.request.DestinatarioRequestDTO;
import Servicio_notificaciones.DTOs.request.NotificacionRequestDTO;
import Servicio_notificaciones.dominio.EstadoNotificacion;
import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import Servicio_notificaciones.repository.NotificacionRepository;
import Servicio_notificaciones.strategy.INotificador;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailAuthenticationException;

/**
 * Tests del servicio de notificaciones usando canales (INotificador) simulados con Mockito,
 * para poder demostrar el comportamiento sin depender de un servidor SMTP real.
 */
class NotificacionServiceTest {

  private NotificacionRepository repository;
  private INotificador notificadorEmail;
  private INotificador notificadorSms;
  private INotificador notificadorWhatsapp;
  private NotificacionService service;

  @BeforeEach
  void setUp() {
    repository = new NotificacionRepository();

    notificadorEmail = mock(INotificador.class);
    Mockito.when(notificadorEmail.medio()).thenReturn(MedioNotificacion.EMAIL);

    notificadorSms = mock(INotificador.class);
    Mockito.when(notificadorSms.medio()).thenReturn(MedioNotificacion.SMS);

    notificadorWhatsapp = mock(INotificador.class);
    Mockito.when(notificadorWhatsapp.medio()).thenReturn(MedioNotificacion.WHATSAPP);

    service = new NotificacionService(
        repository,
        List.of(notificadorEmail, notificadorSms, notificadorWhatsapp)
    );
  }

  private NotificacionRequestDTO requestSms(String telefono) {
    DestinatarioRequestDTO destinatario = new DestinatarioRequestDTO();
    destinatario.setNombre("Ana Pérez");
    destinatario.setTelefono(telefono);

    NotificacionRequestDTO request = new NotificacionRequestDTO();
    request.setMedioNotificacion(MedioNotificacion.SMS);
    request.setAsunto("Donación asignada");
    request.setCuerpo("Tu donación fue asignada a una entidad beneficiaria.");
    request.setDestinatario(destinatario);
    request.setEventoOrigen("DONACION_ASIGNADA");
    return request;
  }

  private NotificacionRequestDTO requestEmail(String email) {
    DestinatarioRequestDTO destinatario = new DestinatarioRequestDTO();
    destinatario.setNombre("Ana Pérez");
    destinatario.setEmail(email);

    NotificacionRequestDTO request = new NotificacionRequestDTO();
    request.setMedioNotificacion(MedioNotificacion.EMAIL);
    request.setAsunto("¡Bienvenido/a a DonaTrack!");
    request.setCuerpo("Tu cuenta fue creada exitosamente.");
    request.setDestinatario(destinatario);
    request.setEventoOrigen("BIENVENIDA");
    return request;
  }

  @Test
  void enviar_conCanalDisponible_marcaLaNotificacionComoEnviada() throws Exception {
    doNothing().when(notificadorSms).enviar(any(Notificacion.class));

    Notificacion resultado = service.enviar(requestSms("+541155555555"));

    assertThat(resultado.getEstadoNotificacion()).isEqualTo(EstadoNotificacion.ENVIADA);
    assertThat(resultado.getId()).isNotNull();
    assertThat(resultado.getFechaEnvio()).isNotNull();
    verify(notificadorSms).enviar(any(Notificacion.class));
  }

  @Test
  void enviar_conFallaDelMailSender_marcaLaNotificacionComoFallidaYGuardaElMotivo() throws Exception {
    doThrow(new MailAuthenticationException("535 Authentication failed"))
        .when(notificadorEmail).enviar(any(Notificacion.class));

    Notificacion resultado = service.enviar(requestEmail("ana@mail.com"));

    assertThat(resultado.getEstadoNotificacion()).isEqualTo(EstadoNotificacion.FALLIDA);
    assertThat(resultado.getError()).contains("Authentication failed");
    // Aunque falló el envío, la notificación queda persistida con su estado y motivo (trazabilidad).
    assertThat(repository.findById(resultado.getId())).isPresent();
  }

  @Test
  void enviar_sinDatoDeContactoParaElMedio_lanzaIllegalArgumentException() {
    NotificacionRequestDTO request = requestSms(null);

    assertThrows(IllegalArgumentException.class, () -> service.enviar(request));
  }

  @Test
  void enviar_sinCanalRegistradoParaElMedio_marcaLaNotificacionComoFallida() {
    NotificacionService serviceSinCanales = new NotificacionService(repository, List.of());

    Notificacion resultado = serviceSinCanales.enviar(requestSms("+541155555555"));

    assertThat(resultado.getEstadoNotificacion()).isEqualTo(EstadoNotificacion.FALLIDA);
    assertThat(resultado.getError()).contains("No existe canal para el medio");
  }

  @Test
  void obtenerPorId_conIdInexistente_lanzaIllegalArgumentException() {
    UUID idInexistente = UUID.randomUUID();

    assertThrows(IllegalArgumentException.class, () -> service.obtenerPorId(idInexistente));
  }

  @Test
  void obtenerPorId_conIdExistente_devuelveLaNotificacion() throws Exception {
    doNothing().when(notificadorSms).enviar(any(Notificacion.class));
    Notificacion creada = service.enviar(requestSms("+541155555555"));

    Notificacion encontrada = service.obtenerPorId(creada.getId());

    assertThat(encontrada.getId()).isEqualTo(creada.getId());
  }

}
