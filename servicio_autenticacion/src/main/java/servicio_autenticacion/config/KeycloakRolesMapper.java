
package servicio_autenticacion.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeycloakRolesMapper implements GrantedAuthoritiesMapper {

  @Override
  public Collection<? extends GrantedAuthority> mapAuthorities(
      Collection<? extends GrantedAuthority> authorities) {

    Set<GrantedAuthority> mappedAuthorities =
        new HashSet<>(authorities);

    for (GrantedAuthority authority : authorities) {

      if (authority instanceof OidcUserAuthority oidcAuthority) {

        List<String> roles = oidcAuthority
            .getIdToken()
            .getClaimAsStringList("roles");

        if (roles != null) {

          for (String role : roles) {

            if (role.equals("DONANTE") ||
                role.equals("ADMIN")) {

              mappedAuthorities.add(
                  new SimpleGrantedAuthority(
                      "ROLE_" + role
                  )
              );
            }
          }
        }
      }
    }

    return mappedAuthorities;
  }
}
