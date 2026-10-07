package com.grupo10.servicio_donaciones.Controllers.cRankingsEntidades;

import com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion.RankingEntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion.SegmentadaRankings;
import com.grupo10.servicio_donaciones.Services.ServiceAsignacion.ServicioAsignacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/rankings")
public class CRankingsEntidades {
    private final ServicioAsignacion servicioAsignacion;

    public CRankingsEntidades(ServicioAsignacion servicioAsignacion) {
        this.servicioAsignacion = servicioAsignacion;
    }

    public Map<String, Object> aRankingEntidadREST(RankingEntidadBeneficiaria ranking) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("idEntidad", ranking.getEntidadBeneficiaria().getId());
        dto.put("posicion", ranking.getPosicion());
        dto.put("puntaje", ranking.getPuntaje());
        dto.put("algoritmoUtilizado", ranking.getAlgoritmoUsado());
        return dto;
    }

    public Map<String, Object> aSegmentadaRankingsREST(SegmentadaRankings segmentadaRankings) {

        Map<String, Object> dto = new HashMap<>();
        dto.put("idSegmentada", segmentadaRankings.getSegmentada().getId());
        dto.put("ranking", segmentadaRankings.getRankings().stream().map(this::aRankingEntidadREST));
        return dto;
    }

    @GetMapping("")
    public ResponseEntity<Stream<Map<String, Object>>> obtenerRankings() {
        return ResponseEntity.ok(servicioAsignacion.obtenerRankings().stream().map(this::aSegmentadaRankingsREST));
    }

    @GetMapping("/{idDonacion}")
    public ResponseEntity<List<RankingEntidadBeneficiaria>> obtenerEntidadesRepetidas(@PathVariable Long idDonacion) {
        return ResponseEntity.ok(servicioAsignacion.filtrarEntidades(idDonacion));
    }

    @PostMapping("")
    public ResponseEntity<String> crearRankings() {
        servicioAsignacion.generarRanking();
        return ResponseEntity.ok("Rankings creados exitosamente");
    }

}
