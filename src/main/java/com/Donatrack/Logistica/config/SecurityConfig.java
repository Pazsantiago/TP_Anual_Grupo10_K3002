package com.Donatrack.Logistica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error", "/api/healthCheck").permitAll()
                .requestMatchers("/api/planificacion/callback").permitAll()
                .requestMatchers("/api/entregas/asignadas/**").permitAll()

                // TEMPORAL: rutas que consume rama_cliente sin token
                .requestMatchers("/api/camiones/**", "/api/rutas/**",
                                 "/api/donaciones/**", "/api/bultos/**",
                                 "/api/seguimiento/**", "/api/entidades/**",
                                 "/api/distribucion/**").permitAll()

                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}