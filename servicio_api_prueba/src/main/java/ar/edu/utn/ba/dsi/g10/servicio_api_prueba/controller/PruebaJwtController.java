
package ar.edu.utn.ba.dsi.g10.servicio_api_prueba.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PruebaJwtController {

  @GetMapping("/donante/test")
  public Map<String, String> testDonante(
      @AuthenticationPrincipal Jwt jwt) {

    return Map.of(
        "resultado", "Acceso permitido",
        "rol", "DONANTE",
        "usuarioId", jwt.getSubject()
    );
  }

  @GetMapping("/admin/test")
  public Map<String, String> testAdmin(
      @AuthenticationPrincipal Jwt jwt) {

    return Map.of(
        "resultado", "Acceso permitido",
        "rol", "ADMIN",
        "usuarioId", jwt.getSubject()
    );
  }

/*
  @GetMapping("/debug/roles")
  public Map<String, Object> verRoles(
      Authentication authentication,
      @AuthenticationPrincipal Jwt jwt) {

    return Map.of(
        "usuario", jwt.getSubject(),
        "rolesToken",
        jwt.getClaimAsStringList("roles") != null
            ? jwt.getClaimAsStringList("roles")
            : List.of(),
        "autoridadesSpring",
        authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList()
    );
  }
*/

}
