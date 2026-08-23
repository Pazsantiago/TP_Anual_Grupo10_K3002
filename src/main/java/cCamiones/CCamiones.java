package cCamiones;

import Slogistica.dominio.camion.Camion;
import Slogistica.repositorios.RepoCamiones;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
public class CCamiones {

    private final RepoCamiones repoCamiones;

    public CCamiones(RepoCamiones repoCamiones) {
        this.repoCamiones = repoCamiones;
    }

    // READ - Obtener todos los camiones
    @GetMapping("")
    public ResponseEntity<List<Camion>> getAllCamiones() {
        return ResponseEntity.ok(repoCamiones.getCamiones());
    }

    // READ - Obtener un camion por id
    @GetMapping("/{id}")
    public ResponseEntity<Camion> getCamionById(@PathVariable Integer id) {
        return repoCamiones.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE - Agregar un nuevo camion
    @PostMapping("")
    public ResponseEntity<Camion> createCamion(@RequestBody Camion camion) {
        return ResponseEntity.ok(repoCamiones.guardarCamion(camion));
    }

    // UPDATE - Actualizar un camion existente
    @PutMapping("/{id}")
    public ResponseEntity<Camion> updateCamion(@PathVariable Integer id, @RequestBody Camion camionActualizado) {
        return repoCamiones.buscarPorId(id)
                .map(existente -> {
                    camionActualizado.setId(id);
                    repoCamiones.getCamiones().set(repoCamiones.getCamiones().indexOf(existente), camionActualizado);
                    return ResponseEntity.ok(camionActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Eliminar un camion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamion(@PathVariable Integer id) {
        repoCamiones.getCamiones().removeIf(c -> c.getId().equals(id));
        return ResponseEntity.ok().build();
    }
}
