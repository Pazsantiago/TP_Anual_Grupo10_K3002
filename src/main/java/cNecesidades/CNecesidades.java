package cNecesidades;

//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.dominio.necesidad.NecesidadExtraordinaria;
import Sdonaciones.dominio.necesidad.NecesidadRecurrente;
import Sdonaciones.repositorios.RepoNecesidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
public class CNecesidades {

    private RepoNecesidades repoNecesidades;

    // Inicializamos con algunos datos
    public CNecesidades() {
        repoNecesidades = new RepoNecesidades();
        //necesidades.add(new entidad("La Carbonilla"));
        //necesidades.add(new entidad("Jardin de Infantes Nro 2"));
    }

    // READ - Obtener todas las Necesidades Extraordinarias
    @GetMapping("")
    public ResponseEntity<List<Necesidad>> getAllNecesidades() {
        return ResponseEntity.ok(repoNecesidades.getNecesidades());
    }

    // READ - Obtener una Necesidad Extraordinaria por ID
    @GetMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(repoNecesidades.getNecesidades().stream()
                .filter(n -> n.getId().equals(idNecesidad))
                .findFirst()
                .orElse(null));
    }

    // CREATE - Agregar una nueva Necesidad Extraordinaria
    @PostMapping("/Extraordinaria")
    public ResponseEntity<Necesidad> createNecesidadExtraordinaria(@RequestBody NecesidadExtraordinaria necesidad) {
        repoNecesidades.getNecesidades().add(necesidad);
        return ResponseEntity.ok(necesidad);
    }

    @PostMapping("/Recurrente")
    public ResponseEntity<Necesidad> createNecesidadRecurrente(@RequestBody NecesidadRecurrente necesidad) {
        repoNecesidades.getNecesidades().add(necesidad);
        return ResponseEntity.ok(necesidad);
    }

    // UPDATE - Actualizar una Necesidad Extraordinaria existente
    @PutMapping("/Extraordinaria/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Integer idNecesidad, @RequestBody NecesidadExtraordinaria updatedNecesidad) {
        Necesidad antigua = repoNecesidades.getNecesidades().stream().filter(p -> p.getId().equals(idNecesidad))
                .findFirst().orElse(null);
        repoNecesidades.getNecesidades().set(repoNecesidades.getNecesidades().indexOf(antigua), updatedNecesidad);
        return ResponseEntity.ok(updatedNecesidad);
    }

    @PutMapping("/Recurrente/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Integer idNecesidad, @RequestBody NecesidadRecurrente updatedNecesidad) {
        Necesidad antigua = repoNecesidades.getNecesidades().stream().filter(p -> p.getId().equals(idNecesidad))
                .findFirst().orElse(null);
        repoNecesidades.getNecesidades().set(repoNecesidades.getNecesidades().indexOf(antigua), updatedNecesidad);
        return ResponseEntity.ok(updatedNecesidad);
    }


    // DELETE - Eliminar una Necesidad Extraordinaria
    @DeleteMapping("/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Integer idNecesidad) {
        boolean es;
        repoNecesidades.getNecesidades().removeIf(e -> e.getId().equals(idNecesidad));
        return ResponseEntity.ok("Necesidad eliminada.");
    }
}
