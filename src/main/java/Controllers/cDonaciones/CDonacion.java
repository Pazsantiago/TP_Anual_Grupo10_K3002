package Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Services.ServiceDonaciones.ServicioDonacion;
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

    // READ - Obtener todas las Donaciones
    @GetMapping("")
    public ResponseEntity<List<Donacion>> getAllDonaciones() {

        return ResponseEntity.ok(servicioDonacion.getAllDonaciones());
    }

    //
    // READ - Obtener una Donaciones por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Integer idDonacion) {
        return ResponseEntity.ok(servicioDonacion.getDonacionById(idDonacion));
    }

    // Obtener todas las donaciones segmentadas
    @GetMapping("/segmentadas")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadas() {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadas());
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(@PathVariable Integer idSegmentada) {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionSegmentada(idSegmentada));
    }


    // Conocer las donaciones ya asignadas
    @GetMapping("/asignadas")
    public ResponseEntity<List<DonacionAsignada>> obtenerDonacionesAsignadas() {
        return ResponseEntity.ok(servicioDonacion.obtenerDonacionesAsignadas());
    }


    // CREATE - Agregar una nueva Donacion  
    @PostMapping("")
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion) {
        return ResponseEntity.ok(servicioDonacion.createDonacion(donacion));

    }

    // SELECT - DADA una entidad, se le asigna finalmente la donacion segmentada a su necesidad.
    @PostMapping("/{idDonacion}/entidad/{idEntidad}/necesidad/{idNecesidad}")
    public ResponseEntity<DonacionAsignada> asignarDonacionAEntidadPorNecesidad(@PathVariable Integer idDonacion, @PathVariable Integer idEntidad, @PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(servicioDonacion.asignarDonacionAEntidadPorNecesidad(idDonacion, idEntidad, idNecesidad));
    }


    // UPDATE - Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public ResponseEntity<Donacion> updateDonacion(@RequestBody Donacion updateDonacion, @PathVariable Integer idDonacion) {
        return ResponseEntity.ok(servicioDonacion.updateDonacion(updateDonacion, idDonacion));
    }

    //
    // DELETE - Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<String> deleteDonacion(@PathVariable Integer idDonacion) {
        return ResponseEntity.ok(servicioDonacion.deleteDonacion(idDonacion));
    }
}
