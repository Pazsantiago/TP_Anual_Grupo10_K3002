package utn.donatrack.donatrack_frontend.controller.donaciones;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonacionesHomeController {
    @GetMapping("/donaciones/home")
    public String home(HttpSession session,
                       Model model
                       ) {
        Object msg = session.getAttribute("errorMsg");
        if (msg != null) {
            session.removeAttribute("errorMsg");
            model.addAttribute("errorMsg", msg);
        }
        return "home";
    }
}
