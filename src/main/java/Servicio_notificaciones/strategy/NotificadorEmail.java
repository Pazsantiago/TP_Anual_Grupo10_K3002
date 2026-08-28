package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmail implements INotificador {

  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String apiKey;
  private final String remitenteEmail;
  private final String remitenteNombre;

  public NotificadorEmail(
      RestTemplateBuilder restTemplateBuilder,
      @Value("${brevo.api.url}") String apiUrl,
      @Value("${brevo.api.key}") String apiKey,
      @Value("${brevo.sender.email}") String remitenteEmail,
      @Value("${brevo.sender.name}") String remitenteNombre
  ) {
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(10))
        .build();

    this.apiUrl = apiUrl;
    this.apiKey = apiKey;
    this.remitenteEmail = remitenteEmail;
    this.remitenteNombre = remitenteNombre;
  }

  @Override
  public MedioNotificacion medio() {
    return MedioNotificacion.EMAIL;
  }

  @Override
  public void enviar(Notificacion notificacion) {
    Destinatario destinatario = notificacion.getDestinatario();

    BrevoEmailRequest request = new BrevoEmailRequest(
        new BrevoSender(
            remitenteNombre,
            remitenteEmail
        ),
        List.of(
            new BrevoDestinatario(
                destinatario.getNombre(),
                destinatario.getEmail()
            )
        ),
        notificacion.getAsunto(),
        notificacion.getCuerpo()
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.set("api-key", apiKey);

    HttpEntity<BrevoEmailRequest> peticion =
        new HttpEntity<>(request, headers);

    try {
      ResponseEntity<String> respuesta =
          restTemplate.exchange(
              apiUrl,
              HttpMethod.POST,
              peticion,
              String.class
          );

      if (!respuesta.getStatusCode().is2xxSuccessful()) {
        throw new IllegalStateException(
            "Brevo respondió con estado "
                + respuesta.getStatusCode()
        );
      }

    } catch (RestClientResponseException e) {
      throw new IllegalStateException(
          "Brevo rechazó el envío. HTTP "
              + e.getStatusCode().value()
              + ": "
              + e.getResponseBodyAsString(),
          e
      );
    }
  }

  private record BrevoSender(
      String name,
      String email
  ) {
  }

  private record BrevoDestinatario(
      String name,
      String email
  ) {
  }

  private record BrevoEmailRequest(
      BrevoSender sender,
      List<BrevoDestinatario> to,
      String subject,
      String textContent
  ) {
  }

/*
  private final JavaMailSender mailSender;

  public NotificadorEmail(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public MedioNotificacion medio(){
    return MedioNotificacion.EMAIL;
  }

  @Override
  public void enviar(Notificacion notificacion){

    SimpleMailMessage mensaje = new SimpleMailMessage();

    mensaje.setTo(notificacion.getDestinatario().getEmail());
    mensaje.setSubject(notificacion.getAsunto());
    mensaje.setText(notificacion.getCuerpo());

    mailSender.send(mensaje);
  }
*/
}
