
package servicio_autenticacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@RestController
@RequestMapping("/auth")
public class JwtTestController {

  private final RestClient restClient = RestClient.create();

  @GetMapping("/probar-jwt-donante")
  public ResponseEntity<String> probarDonante(
      @RegisteredOAuth2AuthorizedClient("keycloak")
      OAuth2AuthorizedClient cliente) {

    return consultarApi("/api/donante/test", cliente);
  }

  @GetMapping("/probar-jwt-admin")
  public ResponseEntity<String> probarAdmin(
      @RegisteredOAuth2AuthorizedClient("keycloak")
      OAuth2AuthorizedClient cliente) {

    return consultarApi("/api/admin/test", cliente);
  }

  private ResponseEntity<String> consultarApi(
      String ruta,
      OAuth2AuthorizedClient cliente) {

    String token = cliente.getAccessToken().getTokenValue();

    try {
      return restClient.get()
          .uri("http://localhost:8091" + ruta)
          .headers(headers -> headers.setBearerAuth(token))
          .retrieve()
          .toEntity(String.class);

    } catch (RestClientResponseException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .body("API respondio: " + ex.getStatusCode());
    }
  }

  /*
  @GetMapping("/probar-jwt-roles")
  public ResponseEntity<String> probarRoles(
      @RegisteredOAuth2AuthorizedClient("keycloak")
      OAuth2AuthorizedClient cliente) {

    return consultarApi("/api/debug/roles", cliente);
  }
*/

}
