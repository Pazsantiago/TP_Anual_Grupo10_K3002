package com.grupo10.servicio_donaciones.Services.KeyclockService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.server-url:http://localhost:8080}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm:donatrack}")
    private String realm;

    @Value("${keycloak.admin.client-id:admin-cli}")
    private String adminClientId;

    @Value("${keycloak.admin.username:admin}")
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
        credential.put("temporary", true);
        userJson.put("credentials", List.of(credential));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userJson, headers);

        restTemplate.postForEntity(usersUrl, request, String.class);
        asignarRolAUsuario(email, rol, adminToken);
    }

    private void asignarRolAUsuario(String email, String nombreRol, String adminToken) {
        try {
            String rolLimpio = nombreRol.replace("ROLE_", "");

            String searchUserUrl = keycloakServerUrl + "/admin/realms/" + realm + "/users?username=" + email;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(adminToken);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<List> userResponse = restTemplate.exchange(searchUserUrl, HttpMethod.GET, request, List.class);
            if (userResponse.getBody() == null || userResponse.getBody().isEmpty()) return;

            Map<String, Object> usuario = (Map<String, Object>) userResponse.getBody().get(0);
            String userId = (String) usuario.get("id");

            String getRoleUrl = keycloakServerUrl + "/admin/realms/" + realm + "/roles/" + rolLimpio;
            ResponseEntity<Map> roleResponse = restTemplate.exchange(getRoleUrl, HttpMethod.GET, request, Map.class);
            Map<String, Object> roleMap = roleResponse.getBody();

            String addRoleUrl = keycloakServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
            HttpEntity<List<Map<String, Object>>> addRoleRequest = new HttpEntity<>(List.of(roleMap), headers);
            restTemplate.postForEntity(addRoleUrl, addRoleRequest, String.class);

        } catch (Exception e) {
            System.err.println("No se pudo asignar el rol en Keycloak: " + e.getMessage());
        }
    }
}