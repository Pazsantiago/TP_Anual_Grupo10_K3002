package Servicio_notificaciones.strategy;

import Servicio_notificaciones.dominio.MedioNotificacion;
import Servicio_notificaciones.dominio.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificadorSMS implements INotificador{

  private final RestTemplate restTemplate;
  private final String apiUrl;
  private final String accountSid;
  private final String authToken;
  private final String numeroRemitente;

  public NotificadorSMS(
      RestTemplateBuilder restTemplateBuilder,
      @Value("${twilio.sms.url}") String apiUrl,
      @Value("${twilio.account-sid}") String accountSid,
      @Value("${twilio.auth-token}") String authToken,
      @Value("${twilio.phone-number}") String numeroRemitente
  ) {
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(10))
        .build();

    this.apiUrl = apiUrl;
    this.accountSid = accountSid;
    this.authToken = authToken;
    this.numeroRemitente = numeroRemitente;
  }

  @Override
  public MedioNotificacion medio() {
    return MedioNotificacion.SMS;
  }

  @Override
  public void enviar(Notificacion notificacion) {
    String telefonoDestinatario =
        notificacion.getDestinatario().getTelefono();

    validarTelefono(telefonoDestinatario);
    validarConfiguracion();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(
        MediaType.APPLICATION_FORM_URLENCODED
    );
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setBasicAuth(accountSid, authToken);

    MultiValueMap<String, String> body =
        new LinkedMultiValueMap<>();

    body.add("To", telefonoDestinatario);
    body.add("From", numeroRemitente);
    body.add("Body", notificacion.getCuerpo());

    HttpEntity<MultiValueMap<String, String>> request =
        new HttpEntity<>(body, headers);

    try {
      ResponseEntity<String> response =
          restTemplate.exchange(
              apiUrl,
              HttpMethod.POST,
              request,
              String.class,
              accountSid
          );

      if (!response.getStatusCode().is2xxSuccessful()) {
        throw new IllegalStateException(
            "Twilio respondió con estado "
                + response.getStatusCode()
        );
      }

    } catch (RestClientResponseException e) {
      throw new IllegalStateException(
          "Twilio rechazó el SMS. HTTP "
              + e.getStatusCode().value()
              + ": "
              + e.getResponseBodyAsString(),
          e
      );
    }
  }

  private void validarTelefono(String telefono) {
    if (telefono == null ||
        !telefono.matches("^\\+[1-9]\\d{7,14}$")) {
      throw new IllegalArgumentException(
          "El teléfono debe tener formato internacional E.164. "
              + "Ejemplo: +5491123456789"
      );
    }
  }

  private void validarConfiguracion() {
    if (accountSid == null || accountSid.isBlank()) {
      throw new IllegalStateException(
          "No se configuró TWILIO_ACCOUNT_SID"
      );
    }

    if (authToken == null || authToken.isBlank()) {
      throw new IllegalStateException(
          "No se configuró TWILIO_AUTH_TOKEN"
      );
    }

    if (numeroRemitente == null ||
        numeroRemitente.isBlank()) {
      throw new IllegalStateException(
          "No se configuró TWILIO_PHONE_NUMBER"
      );
    }
  }
/*
  @Override
  public MedioNotificacion medio(){
    return MedioNotificacion.SMS;
  }

  @Override
  public void enviar(Notificacion notificacion){
    System.out.println(
        "[SIMULACION SMS] Para: " +
            notificacion.getDestinatario().getTelefono() +
            " | Mensaje: " +
            notificacion.getCuerpo()
    );
  }

*/
}
