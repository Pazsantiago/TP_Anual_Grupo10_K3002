package com.Donatrack.Logistica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error", "/actuator/health", "/api/healthCheck").permitAll()
                        .requestMatchers("/api/planificacion/callback", "/api/entregas/asignadas/**").permitAll()
                        // TEMPORAL: rutas que usa rama_cliente, que todavía no envía token
                        .requestMatchers("/api/camiones/**", "/api/bultos/**", "/api/donaciones/**",
                                "/api/rutas/**", "/api/seguimiento/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}