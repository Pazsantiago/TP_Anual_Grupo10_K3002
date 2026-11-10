
package servicio_autenticacion.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/me")
  public UsuarioAutenticado obtenerUsuario(
      @AuthenticationPrincipal OidcUser usuario) {

    return new UsuarioAutenticado(
        usuario.getSubject(),
        usuario.getPreferredUsername(),
        usuario.getEmail(),
        usuario.getFullName()
    );
  }

  public record UsuarioAutenticado(
      String id,
      String username,
      String email,
      String nombreCompleto
  ) {}


  @GetMapping("/logged-out")
  public String logoutExitoso() {
    return "Sesion cerrada correctamente en DonaTrack.";
  }


  @GetMapping("/donante/test")
  public String accesoDonante() {
    return "Acceso permitido: DONANTE";
  }

  @GetMapping("/admin/test")
  public String accesoAdmin() {
    return "Acceso permitido: ADMIN";
  }


}
