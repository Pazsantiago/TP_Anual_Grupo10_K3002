package utn.donatrack.donatrack_frontend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.config.RolUsuario;
import utn.donatrack.donatrack_frontend.services.KeycloakAdminService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final KeycloakAdminService keycloakAdminService;
    @GetMapping("/login")
    public String vistaLogin(
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (session != null && session.getAttribute("jwtToken") != null) {
            redirectAttributes.addFlashAttribute("Exito", "Ya estás autenticado");
            return "redirect:/panel";
        }
        return "redirect:/oauth2/authorization/keycloak";
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
            session.setAttribute("idToken", usuarioLogueado.getIdToken().getTokenValue());

            String rolFinal = "ROLE_DONANTE"; // Rol por defecto si no matchea otro
            String infoUsuario = usuarioLogueado.getAuthorities().toString().toUpperCase()
                    + usuarioLogueado.getAttributes().toString().toUpperCase();

            for (RolUsuario rol : RolUsuario.values()) {
                if (infoUsuario.contains(rol.name())) {
                    rolFinal = "ROLE_" + rol.name();
                    break;
                }
            }
            session.setAttribute("userRol", rolFinal);

        }else{
            return "redirect:/login";
        }
            redirectAttributes.addFlashAttribute("exito", "Usuario autenticado!");
            return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return "redirect:" + keycloakAdminService.logout(session);
    }
}