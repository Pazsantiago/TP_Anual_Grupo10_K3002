package com.grupo10.servicio_donaciones.Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.dtos.frontend.DonacionDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donantes/{idDonante}/donaciones")
@RequiredArgsConstructor
public class CDonacion {
    private final DTOSMapper mapper;
    private final ServicioDonacion servicioDonacion;

    // Obtener todas las Donaciones
    @GetMapping("")
    public ResponseEntity<List<DonacionDTO>> getAllDonaciones(@PathVariable Long idDonante) {
        try {
            return ResponseEntity.ok(servicioDonacion.getAllDonaciones(idDonante).stream().map(mapper::aDonacionDTO).toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Obtener una Donacion por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<DonacionDTO> getDonacionById(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(mapper.aDonacionDTO(servicioDonacion.getDonacionById(idDonacion)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas
    @GetMapping("/{idDonacion}/segmentadas")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadas(@PathVariable Long idDonante, @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadas(idDonante, idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/{idDonacion}/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(@PathVariable Long idDonante, @PathVariable Long idDonacion, @PathVariable Long idSegmentada) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionSegmentada(idSegmentada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Agregar una nueva Donacion
    @PostMapping("")
    public ResponseEntity<DonacionDTO> createDonacion(@RequestBody Donacion donacion, @PathVariable Long idDonante) {
        try {
            return ResponseEntity.ok(mapper.aDonacionDTO(servicioDonacion.createDonacion(donacion, idDonante)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public ResponseEntity<DonacionDTO> updateDonacion(@RequestBody Donacion updateDonacion, @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(mapper.aDonacionDTO(servicioDonacion.updateDonacion(idDonacion, updateDonacion)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //
    // Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<String> deleteDonacion(@PathVariable Long idDonante, @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.deleteDonacion(idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
