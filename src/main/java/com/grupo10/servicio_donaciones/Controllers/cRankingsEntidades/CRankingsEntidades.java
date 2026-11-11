package com.grupo10.servicio_donaciones.Controllers.cRankingsEntidades;

import com.grupo10.servicio_donaciones.Services.ServiceAsignacion.ServicioAsignacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.RankingEntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.SegmentadaRankings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rankings")
public class CRankingsEntidades {
    private final ServicioAsignacion servicioAsignacion;

    @GetMapping("")
    public ResponseEntity<List<SegmentadaRankings>> obtenerRankings() {
        try {
            return ResponseEntity.ok(servicioAsignacion.obtenerRankings());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idDonacion}")
    public ResponseEntity<List<RankingEntidadBeneficiaria>> obtenerEntidadesRepetidas(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioAsignacion.filtrarEntidades(idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<String> crearRankings() {
        try {
            return ResponseEntity.ok(servicioAsignacion.generarRanking());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
