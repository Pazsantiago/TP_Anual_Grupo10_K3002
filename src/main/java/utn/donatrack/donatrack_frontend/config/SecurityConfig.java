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
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/error/**", "/login", "/logout", "/login/callback", "/", "/inicio", "/galeria", "/mapa", "/legal", "/donantes/nuevo", "/entidadesBeneficiarias/nueva").permitAll()
                        // Permisos con Rol
                        .requestMatchers("/ruta", "/ruta/iniciar").hasRole("CHOFER")
                        .requestMatchers("/logistica/seguimiento", "/logistica/seguimiento/ubicaciones").hasAnyRole("ENTIDAD", "DONANTE")
                        .requestMatchers("/donaciones", "/donaciones/segmentadas").hasAnyRole("DONANTE", "ADMIN")
                        .requestMatchers("/entidadesBeneficiarias").hasAnyRole("DONANTE", "ENTIDAD", "ADMIN")
                        .requestMatchers("/donantes/**", "/donaciones/**",
                        "/asignaciones/nueva", "/asignaciones/agregar-segmentada", "/logistica/**", "/incentivos", "/rankings/**").hasRole("ADMIN")
                        .requestMatchers("/incentivos/perfil-donante", "/incentivos/*").hasRole("DONANTE")
                        .requestMatchers("/entidadesBeneficiarias/**", "/necesidades/**", "/asignaciones").hasRole("ENTIDAD")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            request.getSession().setAttribute("errorMsg", "No tenés permisos suficientes para acceder a ese recurso.");
                            response.sendRedirect("/");
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