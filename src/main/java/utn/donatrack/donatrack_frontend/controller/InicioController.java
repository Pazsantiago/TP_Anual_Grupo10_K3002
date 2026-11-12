package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import utn.donatrack.donatrack_frontend.client.IncentivosClient;
import utn.donatrack.donatrack_frontend.client.dto.incentivos.PosicionRankingResponse;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

@Controller
public class InicioController {

    private final IncentivosClient incentivos;

    public InicioController(IncentivosClient incentivos) {
        this.incentivos = incentivos;
    }

    /** Landing publica: proposito, como funciona y los tres donantes mas activos del mes. */
    @GetMapping("/")
    public String inicio(Model model) {
        Seccion<PosicionRankingResponse> ranking = Seccion.cargar(incentivos::rankingMensual);
        model.addAttribute("topRanking", new Seccion<>(ranking.datos().stream().limit(3).toList(), ranking.error()));
        return "inicio";
    }

    /** Panel de gestion: accesos a todas las secciones (ver NavegacionAdvice). */
    @GetMapping("/panel")
    public String panel() {
        return "panel";
    }
}
