package Controllers.cRankingsEntidades;

import Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import Services.ServiceAsignacion.ServicioAsignacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rankings")
public class CRankingsEntidades {
    private final ServicioAsignacion servicioAsignacion;

    public CRankingsEntidades(ServicioAsignacion servicioAsignacion) {
        this.servicioAsignacion = servicioAsignacion;
    }

    @GetMapping("")
    public ResponseEntity<Map<Long, List<RankingEntidadBeneficiaria>>> obtenerRankings() {
        return ResponseEntity.ok(servicioAsignacion.obtenerRankings());
    }

    @GetMapping("/{idDonacion}")
    public ResponseEntity<List<RankingEntidadBeneficiaria>> obtenerEntidadesRepetidas(@PathVariable Integer idDonacion) {
        return ResponseEntity.ok(servicioAsignacion.filtrarEntidades(idDonacion));
    }

    @PostMapping("")
    public ResponseEntity<String> crearRankings() {
        servicioAsignacion.generarRanking();
        return ResponseEntity.ok("Rankings creados exitosamente");
    }

}
