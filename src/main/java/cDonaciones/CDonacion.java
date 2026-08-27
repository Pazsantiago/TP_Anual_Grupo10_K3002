package cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.asignacion.ServicioAsignacion;
import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.repositorios.RepoDonaciones;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class CDonacion {

    private RepoDonaciones repoDonaciones;
    private RepoDonacionesAsignadas repoDonacionesAsignadas;
    private final ServicioAsignacion servicioAsignacion;

    // Inicializamos con algunos datos
    public CDonacion(ServicioAsignacion servicioAsignacion, RepoDonacionesAsignadas repoDonacionesAsignadas, RepoDonaciones repoDonaciones) {
        this.servicioAsignacion = servicioAsignacion;
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.repoDonaciones = repoDonaciones;
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

    // Obtener todas las donaciones segmentadas
    @GetMapping("/segmentadas")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadas() {
        List<DonacionSegmentada> lista = new ArrayList<>();
        repoDonaciones.getDonaciones().forEach(d -> {
            lista.addAll(d.getDonacionesSegmentadas());
        });
        return ResponseEntity.ok(lista);
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(@PathVariable Integer idSegmentada) {
        return ResponseEntity.ok(
                repoDonaciones.getDonaciones().stream().filter(d ->
                                d.getDonacionesSegmentadas().stream().anyMatch(s -> s.getId() == idSegmentada))
                        .findFirst().get().getDonacionesSegmentadas().stream().filter(e ->
                                e.getId() == idSegmentada
                        ).findFirst().get()
        );
    }


    // Conocer las donaciones ya asignadas
    @GetMapping("/donaciones/asignadas")
    public ResponseEntity<List<DonacionAsignada>> obtenerDonacionesAsignadas() {
        return ResponseEntity.ok(repoDonacionesAsignadas.listarTodas());
    }


    // CREATE - Agregar una nueva Donacion  
    @PostMapping("")
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion) {
        repoDonaciones.guardar(donacion);
        servicioAsignacion.agregarDonacionesSegmentadas(donacion.getDonacionesSegmentadas());
        servicioAsignacion.generarRanking();
        return ResponseEntity.ok(donacion);

    }

    // SELECT - DADA una entidad, se le asigna finalmente la donacion segmentada a su necesidad.
    @PostMapping("/donaciones/{idDonacion}/entidad/{idEntidad}/necesidad/{idNecesidad}")
    public ResponseEntity<DonacionAsignada> asignarDonacionAEntidadPorNecesidad(@PathVariable Integer idDonacion, @PathVariable Integer idEntidad, @PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(servicioAsignacion.asignarDonacion(idDonacion, idEntidad, idNecesidad));
    }


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
