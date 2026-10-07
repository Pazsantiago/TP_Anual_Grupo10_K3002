package com.grupo10.servicio_donaciones.Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
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

        return ResponseEntity.ok(servicioDonacion.getAllDonaciones());
    }


    // Obtener una Donacion  por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Long idDonacion) {
        return ResponseEntity.ok(servicioDonacion.getDonacionById(idDonacion));
    }

    // Obtener todas las donaciones segmentadas
    @GetMapping("/segmentadas")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadas() {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadas());
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(@PathVariable Long idSegmentada) {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionSegmentada(idSegmentada));
    }


    // Conocer las donaciones ya asignadas
    @GetMapping("/asignadas")
    public ResponseEntity<List<DonacionAsignada>> obtenerDonacionesAsignadas() {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionesAsignadas());
    }


    // Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public ResponseEntity<Donacion> updateDonacion(@RequestBody Donacion updateDonacion, @PathVariable Long idDonacion) {
        return ResponseEntity.ok(servicioDonacion.updateDonacion(idDonacion, updateDonacion));
    }

    //
    // Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<String> deleteDonacion(@PathVariable Long idDonacion) {
        return ResponseEntity.ok(servicioDonacion.deleteDonacion(idDonacion));
    }
}
