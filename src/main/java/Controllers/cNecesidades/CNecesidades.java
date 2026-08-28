package Controllers.cNecesidades;

//import donatrack.dominio.donacion.Donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import Services.ServiceNecesidades.ServicioNecesidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
public class CNecesidades {

    private final ServicioNecesidades servicioNecesidades;

    // Inicializamos con algunos datos
    public CNecesidades(ServicioNecesidades servicioNecesidades) {
        this.servicioNecesidades = servicioNecesidades;
    }

    // READ - Obtener todas las Necesidades
    @GetMapping("")
    public ResponseEntity<List<Necesidad>> getAllNecesidades() {
        return ResponseEntity.ok(servicioNecesidades.getAllNecesidades());
    }

    // READ - Obtener una Necesidad  por ID de una entidad
    @GetMapping("/{idNecesidad}/entidad/{idEntidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Integer idNecesidad, @PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioNecesidades.getNecesidadById(idNecesidad, idEntidad));
    }

    // CREATE - Agregar una nueva Necesidad
    @PostMapping("/{idEntidad}")
    public ResponseEntity<Necesidad> createNecesidad(@PathVariable Integer idEntidad, @RequestBody Necesidad necesidad) {
        return ResponseEntity.ok(servicioNecesidades.createNecesidad(idEntidad, necesidad));
    }

    // UPDATE - Actualizar una Necesidad existente de una entidad
    @PutMapping("/{idNecesidad}/entidad/{idEntidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Integer idNecesidad, @PathVariable Integer idEntidad, @RequestBody Necesidad updatedNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.updateNecesidad(idNecesidad, idEntidad, updatedNecesidad));
    }

    // DELETE - Eliminar una Necesidad
    @DeleteMapping("/{idNecesidad}/entidad/{idEntidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Integer idNecesidad, @PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioNecesidades.deleteNecesidad(idNecesidad, idEntidad));
    }
}
