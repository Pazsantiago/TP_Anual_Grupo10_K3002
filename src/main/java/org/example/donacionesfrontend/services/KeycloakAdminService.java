package org.example.donacionesfrontend.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.frontend.url:http://localhost:8080}")
    private String frontendUrl;

    @Value("${backend.auth.url:http://localhost:8080}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm:donaciones-realm}")
    private String realm;

    @Value("${keycloak.user.client-id:donaciones-frontend}")
    private String userClientId;

    @Value("${keycloak.admin.client-id:admin-cli}")
    private String adminClientId;

    @Value("${keycloak.admin.client-secret:}")
    private String adminClientSecret;

    @Value("${keycloak.admin.username:admin}")
    private String adminUsername;

    @Value("${keycloak.admin.password:admin}")
    private String adminPassword;

    public String logout(HttpSession session) {
        session.invalidate();
        String redirectUri = frontendUrl + "/donaciones/home";

        String keycloakLogoutUrl = String.format(
                "%s/realms/%s/protocol/openid-connect/logout?client_id=%s&post_logout_redirect_uri=%s",
                keycloakServerUrl,
                realm,
                userClientId,
                redirectUri
        );

        return keycloakLogoutUrl;
    }

    public String obtenerTokenUsuario(String email, String password) {
        String tokenUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", userClientId);
        if (adminClientSecret != null && !adminClientSecret.isBlank()) {
            body.add("client_secret", adminClientSecret);
        }
        body.add("grant_type", "password");
        body.add("username", email);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("access_token")) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("No se pudo obtener el token para el usuario: " + email);
    }

    private String obtenerAdminToken() {
        String tokenUrl = keycloakServerUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", adminClientId);
        body.add("grant_type", "password");
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    public void crearUsuarioEnKeycloak(String email, String password, String rol) {
        String adminToken = obtenerAdminToken();
        String usersUrl = keycloakServerUrl + "/admin/realms/" + realm + "/users";

        // Estructura del JSON que exige Keycloak
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("username", email);
        userJson.put("email", email);
        userJson.put("enabled", true);


        // Credenciales (Contraseña)
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", password);
        credential.put("temporary", false);
        userJson.put("credentials", List.of(credential));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userJson, headers);
        restTemplate.postForEntity(usersUrl, request, String.class);
    }
}