package cLogistica;

import Slogistica.dominio.ruta.PlanDeRuta;
import Slogistica.gestor.GestorLogistica;
import Slogistica.repositorios.RepoPlanesDeRuta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistica/rutas")
public class CRutas {

    private final RepoPlanesDeRuta repoPlanesDeRuta;
    private final GestorLogistica gestorLogistica;

    public CRutas(RepoPlanesDeRuta repoPlanesDeRuta, GestorLogistica gestorLogistica) {
        this.repoPlanesDeRuta = repoPlanesDeRuta;
        this.gestorLogistica = gestorLogistica;
    }

    // READ - Todas las rutas planificadas
    @GetMapping("")
    public ResponseEntity<List<PlanDeRuta>> getAllRutas() {
        return ResponseEntity.ok(repoPlanesDeRuta.getPlanes());
    }

    // READ - Ruta asignada a un camión (para la app del chofer)
    @GetMapping("/camion/{patente}")
    public ResponseEntity<PlanDeRuta> getRutaPorCamion(@PathVariable String patente) {
        return repoPlanesDeRuta.buscarPorPatenteCamion(patente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // El chofer indica que inicia su ruta
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<PlanDeRuta> iniciarRuta(@PathVariable Integer id) {
        return ResponseEntity.ok(gestorLogistica.iniciarRuta(id));
    }

    // El chofer indica que finalizó su ruta
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<PlanDeRuta> finalizarRuta(@PathVariable Integer id) {
        return ResponseEntity.ok(gestorLogistica.finalizarRuta(id));
    }
}
