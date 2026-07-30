package cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.repositorios.RepoDonaciones;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class CDonacion {


    private RepoDonaciones repoDonaciones;

    // Inicializamos con algunos datos
    public CDonacion() {
        repoDonaciones = new RepoDonaciones();
    }

    // READ - Obtener todas las Donaciones
    @GetMapping("")
    public ResponseEntity<List<Donacion>> getAllDonaciones() {

        return ResponseEntity.ok(repoDonaciones.getDonaciones());
    }

    //
    // READ - Obtener una Donaciones por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Integer idDonacion) {
        return ResponseEntity.ok(
                repoDonaciones.getDonaciones().stream()
                        .filter(d -> d.getId() == idDonacion)
                        .findFirst()
                        .orElse(null)
        );
    }

    //
    // CREATE - Agregar una nueva Donacion
    @PostMapping("")
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion) {
        repoDonaciones.guardar(donacion);
        return ResponseEntity.ok(donacion);

    }

    //
    // UPDATE - Actualizar una Donacion existente
    @PutMapping("")
    public ResponseEntity<Donacion> updateDonacion(@RequestBody Donacion updateDonacion) {
        Donacion oldDonacion = repoDonaciones.getDonaciones().stream()
                .filter(d -> d.getId() == updateDonacion.getId())
                .findFirst()
                .orElse(null);
        repoDonaciones.getDonaciones().set(repoDonaciones.getDonaciones().indexOf(oldDonacion), updateDonacion);
        return ResponseEntity.ok(updateDonacion);
    }

    //
    // DELETE - Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<Void> deleteDonacion(@PathVariable Integer idDonacion) {
        repoDonaciones.getDonaciones().removeIf(d -> d.getId() == idDonacion);
        return ResponseEntity.ok().build();
    }
}
