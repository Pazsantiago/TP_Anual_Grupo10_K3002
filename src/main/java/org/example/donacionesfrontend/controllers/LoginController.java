package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
public class LoginController {


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
            }else{
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("exito", "Usuario autenticado!");
            return "redirect:/donaciones/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/oauth2/authorization/keycloak";
    }
}