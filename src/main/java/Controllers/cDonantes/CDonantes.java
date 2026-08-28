package Controllers.cDonantes;

import Sdonaciones.dominio.donante.Donante;
import Services.ServiceDonantes.ServicioDonantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/donantes")
public class CDonantes {

    private final ServicioDonantes servicioDonantes;

    public CDonantes(ServicioDonantes servicioDonantes) {
        this.servicioDonantes = servicioDonantes;
    }

    // READ - Obtener todas las Personas
    @GetMapping("")
    public ResponseEntity<List<Donante>> getAllPersonas() {
        return ResponseEntity.ok(servicioDonantes.getAllPersonas());
    }

    // READ - Obtener una persona por correoElectronico
    @GetMapping("/")
    public ResponseEntity<Donante> obtenerPersonaPorDocumento(@RequestParam String tipoD, @RequestParam String doc) {
        return ResponseEntity.ok(servicioDonantes.obtenerPersonaPorDocumento(tipoD, doc));
    }

    // CREATE - Agregar un nueva persona
    @PostMapping("")
    public ResponseEntity<Donante> createPersona(@RequestBody Donante persona) {
        return ResponseEntity.ok(servicioDonantes.createPersona(persona));

    }

    // Import
    @PostMapping("/importador")
    public ResponseEntity<String> importarCSV(@RequestParam String rutaArchivo) {
        return ResponseEntity.ok(servicioDonantes.importarCSV(rutaArchivo));
    }


    // UPDATE - Actualizar una persona existente
    //Con estos datos (tipo persona, tipo doc y nro doc) debe ser posible ubicar a la
    //persona donante en el sistema. En caso
    //contrario, se le debe crear un usuario --> Una vez creado el usuario/donante en el sistema
    @PutMapping("")
    public ResponseEntity<Donante> updateDonante(@RequestParam String tipoD, @RequestParam String doc, @RequestBody Donante updateDonante) {
        return ResponseEntity.ok(servicioDonantes.updateDonante(tipoD, doc, updateDonante));
    }

    // DELETE - Eliminar una Persona
    @DeleteMapping("")
    public ResponseEntity<String> deletePersona(@RequestParam String tipoD, @RequestParam String doc) {
        return ResponseEntity.ok(servicioDonantes.deletePersona(tipoD, doc));
    }


}
