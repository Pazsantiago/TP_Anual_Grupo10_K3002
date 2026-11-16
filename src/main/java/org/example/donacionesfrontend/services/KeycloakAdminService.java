package org.example.donacionesfrontend.services;

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

    @Value("${backend.auth.url:http://localhost:8080}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm:donaciones-realm}")
    private String realm;

    @Value("${keycloak.admin.client-id:admin-cli}")
    private String adminClientId;

    @Value("${keycloak.admin.client-secret:}") // Si usás client-credentials
    private String adminClientSecret;

    @Value("${keycloak.admin.username:admin}") // O usuario admin directo
    private String adminUsername;

    @Value("${keycloak.admin.password:admin}")
    private String adminPassword;

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

        // Transmisión con Header Bearer Token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userJson, headers);

        // Hace el POST a la Admin API de Keycloak
        restTemplate.postForEntity(usersUrl, request, String.class);
    }
}