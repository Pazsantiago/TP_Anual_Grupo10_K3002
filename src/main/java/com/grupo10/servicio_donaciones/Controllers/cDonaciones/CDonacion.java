package com.grupo10.servicio_donaciones.Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class CDonacion {

    private final ServicioDonacion servicioDonacion;

    // Inicializamos con algunos datos
    public CDonacion(ServicioDonacion servicioDonacion) {
        this.servicioDonacion = servicioDonacion;
    }

    // Obtener todas las Donaciones
    @GetMapping("")
    public ResponseEntity<List<Donacion>> getAllDonaciones() {
        try {
            return ResponseEntity.ok(servicioDonacion.getAllDonaciones());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Obtener una Donacion  por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.getDonacionById(idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas
    @GetMapping("/segmentadas")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadas() {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(@PathVariable Long idSegmentada) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionSegmentada(idSegmentada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Conocer las donaciones ya asignadas
    @GetMapping("/asignadas")
    public ResponseEntity<List<DonacionAsignada>> obtenerDonacionesAsignadas() {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesAsignadas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public ResponseEntity<Donacion> updateDonacion(@RequestBody Donacion updateDonacion, @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.updateDonacion(idDonacion, updateDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //
    // Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<String> deleteDonacion(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.deleteDonacion(idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
