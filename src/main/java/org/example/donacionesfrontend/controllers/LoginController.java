package org.example.donacionesfrontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class LoginController {

    private final RestClient restClientAuth;


    public LoginController(@Qualifier("restClientAuth") RestClient restClientAuth) {
        this.restClientAuth = restClientAuth;
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
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Map respuesta = restClientAuth.post()
                    .uri("/api/auth/login")
                    .body(Map.of("username", username, "password", password))
                    .retrieve()
                    .body(Map.class);

            String token = (String) respuesta.get("token");
            String rol = (String) respuesta.get("rol");

            session.setAttribute("jwtToken", token);
            session.setAttribute("rolUsuario", rol);

            return "redirect:/home";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}