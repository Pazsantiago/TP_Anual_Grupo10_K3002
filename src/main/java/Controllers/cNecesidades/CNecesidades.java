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

    // READ - Obtener una Necesidad  por ID
    @GetMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Long idNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.getNecesidadById(idNecesidad));
    }


    // UPDATE - Actualizar una Necesidad existente de una entidad
    @PutMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Long idNecesidad, @RequestBody Necesidad updatedNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.updateNecesidad(idNecesidad, updatedNecesidad));
    }

    // DELETE - Eliminar una Necesidad
    @DeleteMapping("/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Long idNecesidad) {
        return ResponseEntity.ok(servicioNecesidades.deleteNecesidad(idNecesidad));
    }
}
