package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/dev-login")
    public String devLogin(HttpSession session) {
        session.setAttribute("jwtToken", "token-falso-de-prueba");
        session.setAttribute("rolUsuario", "ADMIN");
        return "redirect:/donaciones/home";
    }

    @GetMapping("/login")
    public String vistaLogin() {
        return "redirect:/oauth2/authorization/keycloak";
    }

    @GetMapping("/login/callback")
    public String procesarLogin(
            @AuthenticationPrincipal OidcUser usuarioLogueado,
            HttpSession session) {
            if (usuarioLogueado != null) {
                String token = usuarioLogueado.getIdToken().getTokenValue();
                session.setAttribute("jwtToken", token);
            }else{
                return "redirect:/login";
            }

            SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
                return "redirect:" + targetUrl;
            }
            return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/oauth2/authorization/keycloak";
    }
}