package Controllers.cRankingsEntidades;

import Sdonaciones.asignacion.ServicioAsignacion;
import Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<Integer, List<RankingEntidadBeneficiaria>>> obtenerRankings() {
        return ResponseEntity.ok(servicioAsignacion.obtenerRankings());
    }

    @GetMapping("/{idDonacion}")
    public ResponseEntity<List<RankingEntidadBeneficiaria>> obtenerEntidadesRepetidas(@PathVariable Integer idDonacion) {
        return ResponseEntity.ok(servicioAsignacion.filtrarEntidades(idDonacion));
    }


}
