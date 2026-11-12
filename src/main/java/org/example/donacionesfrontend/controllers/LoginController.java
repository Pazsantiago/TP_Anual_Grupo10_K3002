package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class LoginController {
    @Value("${app.frontend.url}")
    private String baseFrontUrl;
    private String authUrl;
    private final RestClient restClientAuth;


    public LoginController(@Qualifier("restClientAuth") RestClient restClientAuth,
                           @Value("${backend.auth.url}") String authUrl) {
        this.restClientAuth = restClientAuth;
        this.authUrl = authUrl;
    }

    @GetMapping("/dev-login")
    public String devLogin(HttpSession session) {
        // Seteamos datos falsos en la sesión para saltear los 'if'
        session.setAttribute("jwtToken", "token-falso-de-prueba");
        session.setAttribute("rolUsuario", "ADMIN");
        return "redirect:/donantes"; // Te manda directo a la lista
    }

    @GetMapping("/login")
    public String vistaLogin() {
        return "redirect:" + authUrl + "?redirect_uri=" + baseFrontUrl + "/login/callback";
    }

    @GetMapping("/login/callback")
    public String procesarLogin(
            @AuthenticationPrincipal OidcUser usuarioLogueado,
            HttpSession session) {
            if (usuarioLogueado != null) {
                String token = usuarioLogueado.getIdToken().getTokenValue();
                session.setAttribute("jwtToken", token);
            }
            return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:" + authUrl;
    }
}