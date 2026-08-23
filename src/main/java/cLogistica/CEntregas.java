package cLogistica;

import Slogistica.dominio.entrega.Entrega;
import Slogistica.gestor.GestorLogistica;
import Slogistica.repositorios.RepoEntregas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logistica/entregas")
public class CEntregas {

    private final RepoEntregas repoEntregas;
    private final GestorLogistica gestorLogistica;

    public CEntregas(RepoEntregas repoEntregas, GestorLogistica gestorLogistica) {
        this.repoEntregas = repoEntregas;
        this.gestorLogistica = gestorLogistica;
    }

    /**
     * Deja disponible el estado de todas las entregas para que otros
     * servicios (p. ej. Donaciones) lo consulten. Este endpoint es la
     * forma en la que Logística "informa el estado de las donaciones"
     * sin invocar a nadie por su cuenta.
     */
    @GetMapping("")
    public ResponseEntity<List<Entrega>> getEstadoDeDonaciones() {
        return ResponseEntity.ok(gestorLogistica.informarEstadoDeDonaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrega> getEntregaById(@PathVariable Integer id) {
        return repoEntregas.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // La entidad beneficiaria confirma la recepción, opcionalmente con fotos
    @PostMapping("/{id}/confirmar")
    public ResponseEntity<Entrega> confirmarRecepcion(@PathVariable Integer id,
                                                        @RequestBody(required = false) List<String> fotos) {
        return ResponseEntity.ok(gestorLogistica.entregarDonacion(id, fotos));
    }

    // La entidad beneficiaria informa que no recibió la entrega el día correspondiente
    @PostMapping("/{id}/no-recibida")
    public ResponseEntity<Entrega> informarNoRecibida(@PathVariable Integer id,
                                                        @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin motivo informado.");
        return ResponseEntity.ok(gestorLogistica.informarNoEntregaDeDonacion(id, motivo));
    }
}
