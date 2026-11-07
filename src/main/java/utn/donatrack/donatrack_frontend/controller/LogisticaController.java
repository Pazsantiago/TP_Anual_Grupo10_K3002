package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utn.donatrack.donatrack_frontend.client.LogisticaClient;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

/** Panel de logistica: cada bloque se carga por separado, asi que si uno falla los demas se ven. */
@Controller
@RequestMapping("/logistica")
public class LogisticaController {

    private final LogisticaClient client;

    public LogisticaController(LogisticaClient client) {
        this.client = client;
    }

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("camiones", Seccion.cargar(client::listarCamiones));
        model.addAttribute("bultos", Seccion.cargar(client::listarBultosPendientes));
        model.addAttribute("donaciones", Seccion.cargar(client::listarDonacionesPendientes));
        return "logistica/panel";
    }
}
