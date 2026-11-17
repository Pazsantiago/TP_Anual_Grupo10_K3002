package com.grupo10.servicio_donaciones.Controllers.cRankingsEntidades;

import com.grupo10.servicio_donaciones.Services.ServiceAsignacion.ServicioAsignacion;
import com.grupo10.servicio_donaciones.dtos.response.SegmentadaRankingsResponseDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rankings")
public class CRankingsEntidades {
    private final ServicioAsignacion servicioAsignacion;
    private final DTOSMapper mapper;

    @GetMapping("")
    public ResponseEntity<List<SegmentadaRankingsResponseDTO>> obtenerRankings() {
        try {
            return ResponseEntity.ok(servicioAsignacion.obtenerRankings().stream().map(mapper::deDominioAResponse).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{idDonacion}")
    public ResponseEntity<?> obtenerEntidadesRepetidas(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioAsignacion.filtrarEntidades(idDonacion).stream().map(mapper::deDominioAResponse).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> crearRankings() {
        try {
            return ResponseEntity.ok(servicioAsignacion.generarRanking());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
