
package servicio_autenticacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;

@Configuration
public class SecurityConfig {

  @Bean
  public GrantedAuthoritiesMapper authoritiesMapper() {
    return new KeycloakRolesMapper();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      ClientRegistrationRepository clientRegistrationRepository,
      GrantedAuthoritiesMapper authoritiesMapper
  ) throws Exception {

    OidcClientInitiatedLogoutSuccessHandler logoutHandler =
        new OidcClientInitiatedLogoutSuccessHandler(
            clientRegistrationRepository
        );

    logoutHandler.setPostLogoutRedirectUri(
        "{baseUrl}/auth/logged-out"
    );

    http
        .authorizeHttpRequests(auth -> auth

            // Rutas públicas
            .requestMatchers(
                "/",
                "/nosotros",
                "/campanas",
                "/css/**",
                "/js/**",
                "/images/**",
                "/error",
                "/auth/logged-out"
            ).permitAll()

            // Endpoints propios de autenticación
            .requestMatchers("/auth/admin/**")
            .hasRole("ADMIN")

            .requestMatchers("/auth/donante/**")
            .hasAnyRole("DONANTE", "ADMIN")

            .requestMatchers("/auth/me")
            .authenticated()

            // Cliente Liviano
            .requestMatchers("/app/**")
            .authenticated()

            // Resto de las rutas
            .anyRequest()
            .authenticated()
        )

        .oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(userInfo -> userInfo
                .userAuthoritiesMapper(authoritiesMapper)
            )

            // Recuperar la página solicitada
            // o utilizar /auth/me por defecto
            .defaultSuccessUrl("/auth/me", false)
        )

        .logout(logout -> logout
            .logoutSuccessHandler(logoutHandler)
        );

    return http.build();
  }
}
