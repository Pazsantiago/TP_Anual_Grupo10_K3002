package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonanteResponse;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

@Controller
@RequestMapping("/donantes")
public class DonantesController {

    private final DonacionesClient client;

    public DonantesController(DonacionesClient client) {
        this.client = client;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("donantes", Seccion.cargar(client::listarDonantes));
        return "donantes/lista";
    }

    @GetMapping("/{id}/donaciones")
    public String donaciones(@PathVariable("id") long id, Model model) {
        model.addAttribute("idDonante", id);
        model.addAttribute("donante", buscarDonante(id));
        model.addAttribute("donaciones", Seccion.cargar(() -> client.listarDonaciones(id)));
        return "donantes/donaciones";
    }

    /** El nombre es un adorno del titulo: si falla, la seccion de donaciones ya muestra el error del servicio. */
    private DonanteResponse buscarDonante(long id) {
        try {
            return client.obtenerDonante(id);
        } catch (ServicioExternoException e) {
            return null;
        }
    }
}
