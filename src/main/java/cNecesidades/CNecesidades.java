package cNecesidades;

//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoNecesidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
public class CNecesidades {

    private RepoNecesidades repoNecesidades;

    // Inicializamos con algunos datos
    public CNecesidades(RepoNecesidades repoNecesidades) {
        this.repoNecesidades = repoNecesidades;
        //necesidades.add(new entidad("La Carbonilla"));
        //necesidades.add(new entidad("Jardin de Infantes Nro 2"));
    }

    // READ - Obtener todas las Necesidades Extraordinarias
    @GetMapping("")
    public ResponseEntity<List<Necesidad>> getAllNecesidades() {
        return ResponseEntity.ok(repoNecesidades.listarTodas());
    }

    // READ - Obtener una Necesidad Extraordinaria por ID
    @GetMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(repoNecesidades.buscarPorId(idNecesidad));
    }

    // CREATE - Agregar una nueva Necesidad Extraordinaria
    @PostMapping("")
    public ResponseEntity<Necesidad> createNecesidad(@RequestBody Necesidad necesidad) {
        repoNecesidades.guardar(necesidad);
        return ResponseEntity.ok(necesidad);
    }

    // UPDATE - Actualizar una Necesidad Extraordinaria existente
    @PutMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Integer idNecesidad, @RequestBody Necesidad updatedNecesidad) {
        return ResponseEntity.ok(repoNecesidades.actualizarNecesidad(idNecesidad, updatedNecesidad));
    }

    // DELETE - Eliminar una Necesidad Extraordinaria
    @DeleteMapping("/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(repoNecesidades.eliminarEntidad(idNecesidad));
    }
}
