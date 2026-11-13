package utn.donatrack.donatrack_frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String vistaLogin(HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("jwtToken");
        if (token != null) {
            redirectAttributes.addFlashAttribute("exito", "Ya estas logueado.");
        }
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
            return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/oauth2/authorization/keycloak";
    }
}