package org.example.donacionesfrontend.controllers.donaciones;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonacionesHomeController {
    @GetMapping("/donaciones/home")
    public String home(HttpSession session,
                       RedirectAttributes redirectAttributes
                       ) {
        Object msg = session.getAttribute("errorMsg");
        if (msg == null) {
            redirectAttributes.addFlashAttribute("Fallo", msg);
        }
        return "home";
    }
}
