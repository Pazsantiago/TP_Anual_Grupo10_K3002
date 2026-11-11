package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class LoginController {
    private String authUrl;
    private final RestClient restClientAuth;


    public LoginController(@Qualifier("restClientAuth") RestClient restClientAuth,
                           @Value("${backend.auth.url}") String authUrl) {
        this.restClientAuth = restClientAuth;
        this.authUrl = authUrl + "/login";
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
        return "redirect:" + authUrl;
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("token") String token,
            @RequestParam("rolUsuario") String rol,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
            session.setAttribute("jwtToken", token);
            session.setAttribute("rolUsuario", rol);
            return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:" + authUrl;
    }
}