package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import utn.donatrack.donatrack_frontend.client.DonacionesClient;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EntidadResponse;
import utn.donatrack.donatrack_frontend.viewmodel.Seccion;

@Controller
@RequestMapping("/entidades")
public class EntidadesController {

    private final DonacionesClient client;

    public EntidadesController(DonacionesClient client) {
        this.client = client;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entidades", Seccion.cargar(client::listarEntidades));
        return "entidades/lista";
    }

    @GetMapping("/{id}/necesidades")
    public String necesidades(@PathVariable("id") long id, Model model) {
        model.addAttribute("idEntidad", id);
        model.addAttribute("entidad", buscarEntidad(id));
        model.addAttribute("necesidades", Seccion.cargar(() -> client.listarNecesidades(id)));
        return "entidades/necesidades";
    }

    private EntidadResponse buscarEntidad(long id) {
        try {
            return client.obtenerEntidad(id);
        } catch (ServicioExternoException e) {
            return null;
        }
    }
}
