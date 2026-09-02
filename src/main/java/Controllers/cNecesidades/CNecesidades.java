package Controllers.cNecesidades;

//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import Services.ServiceNecesidades.ServicioNecesidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CNecesidades {

    private final ServicioNecesidades servicioNecesidades;

    // Inicializamos con algunos datos
    public CNecesidades(ServicioNecesidades servicioNecesidades) {
        this.servicioNecesidades = servicioNecesidades;
    }

    // READ - Obtener todas las Necesidades
    @GetMapping("/necesidades")
    public ResponseEntity<List<Necesidad>> getAllNecesidades() {
        return ResponseEntity.ok(servicioNecesidades.getAllNecesidades());
    }

    // READ - Obtener una Necesidad  por ID de una entidad
    @GetMapping("/necesidades/{idNecesidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.getNecesidadById(idNecesidad));
    }

    // CREATE - Agregar una nueva Necesidad
    @PostMapping("/entidad/{idEntidad}")
    public ResponseEntity<Necesidad> createNecesidad(@PathVariable Integer idEntidad, @RequestBody Necesidad necesidad) {
        return ResponseEntity.ok(servicioNecesidades.createNecesidad(idEntidad, necesidad));
    }

    // UPDATE - Actualizar una Necesidad existente de una entidad
    @PutMapping("/necesidad/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Integer idNecesidad, @RequestBody Necesidad updatedNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.updateNecesidad(idNecesidad, updatedNecesidad));
    }

    // DELETE - Eliminar una Necesidad
    @DeleteMapping("/necesidad/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Integer idNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.deleteNecesidad(idNecesidad));
    }
}
