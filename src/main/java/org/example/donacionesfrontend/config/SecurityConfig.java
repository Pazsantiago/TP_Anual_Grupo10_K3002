package org.example.donacionesfrontend.config;

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
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/error", "/login", "/login/callback", "/inicio", "/", "/donaciones/home", "/galeria", "/mapa", "/legal", "/donantes/nuevo", "/entidadesBeneficiarias/nueva").permitAll()
                        // Permisos con Rol
                        .requestMatchers("/donaciones/vencida", "/asignaciones/nueva", "/donantes/agregar-segmentada", "/donantes/importador").hasRole("ADMIN")
                        .requestMatchers("/entidadesBeneficiarias").hasAnyRole("DONANTE", "ENTIDAD")
                        .requestMatchers("/donantes/**", "/donaciones/**").hasRole("DONANTE")
                        .requestMatchers("/entidades/**").hasRole("ENTIDAD")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/login/callback", true)
                )
                .addFilterBefore(sessionAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Permite todas las peticiones sin autenticaciÃ³n
//                );
//        return http.build();
//    }
}