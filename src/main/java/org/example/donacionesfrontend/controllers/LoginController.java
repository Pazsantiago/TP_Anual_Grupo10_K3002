package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.services.KeycloakAdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/login")
    public String vistaLogin() {
        return "redirect:/oauth2/authorization/keycloak";
    }

    @GetMapping("/dev-login")
    public String devLogin(HttpSession session) {
        session.setAttribute("jwtToken", "token-falso-de-prueba");
        session.setAttribute("rolUsuario", "ADMIN");
        return "redirect:/donaciones/home";
    }

    @GetMapping("/login/callback")
    public String procesarLogin(
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OidcUser usuarioLogueado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
            if (usuarioLogueado != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                session.setAttribute("jwtToken", accessToken);
                session.setAttribute("userEmail", usuarioLogueado.getEmail());
                String rol = usuarioLogueado.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(a -> a.startsWith("ROLE_"))
                        .findFirst()
                        .orElse("ROLE_DONANTE");
                session.setAttribute("userRol", rol);

            }else{
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("exito", "Usuario autenticado!");
            return "redirect:/donaciones/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return "redirect:" + keycloakAdminService.logout(session);
    }
}