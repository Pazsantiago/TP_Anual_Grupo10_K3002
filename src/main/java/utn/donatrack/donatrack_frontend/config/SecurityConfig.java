package utn.donatrack.donatrack_frontend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SessionAuthFilter sessionAuthFilter;

    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas accesibles por cualquiera
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/error", "/login", "/logout", "/login/callback", "/inicio", "/", "/donaciones/home", "/galeria", "/mapa", "/legal", "/donantes/nuevo", "/entidades/nueva").permitAll()
                        // Permisos con Rol
                        .requestMatchers("/entidades").hasAnyRole("DONANTE", "ENTIDAD")
                        .requestMatchers("/donaciones/vencida", "/asignaciones/nueva", "/asignaciones/agregar-segmentada", "/donantes/importador").hasRole("ADMIN")
                        // Definir arriba CUALES son los endpoints especificos que solo puede el ADMINISTRADOR sobre incentivos y logistica
                        // Especificamente son administrar camiones, ver el ranking de incentivos de donantes mas activos y rankings anteriores
                        .requestMatchers("/donantes/**", "/donaciones/**", "/incentivos/**").hasRole("DONANTE")
                        // Arriba definir de incentivos cuales son los endpoints para consultar sus misiones a realizar e insignias obtenidas
                        .requestMatchers("/entidades/**", "/necesidades/**", "/asignadas", "/rankings/**", "/incentivos/**").hasRole("ENTIDAD")
                        // Aqui definir la ruta que le permite a una entidad confirmar la recepción.
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            request.getSession().setAttribute("errorMsg", "No tenés permisos suficientes para acceder a ese recurso.");
                            response.sendRedirect("/panel");
                        })
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/keycloak")
                        .defaultSuccessUrl("/login/callback", true)
                )
                .logout(logout -> logout.disable()) //Lo desactivamos porque la logica se la delegamos al keycloakService
                .addFilterBefore(sessionAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}