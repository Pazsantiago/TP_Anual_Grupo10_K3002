package utn.donatrack.donatrack_frontend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.ForwardedHeaderFilter;
@Configuration
public class SecurityConfig {

    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitimos rutas estáticas, login, dev-login y el callback
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/error", "/login", "/login/callback", "/dev-login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        // Al volver de Keycloak, Spring ejecuta procesarLogin en LoginController
                        .defaultSuccessUrl("/login/callback", true)
                );

        return http.build();
    }
}