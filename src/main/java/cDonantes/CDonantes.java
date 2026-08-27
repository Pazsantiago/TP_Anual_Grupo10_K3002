package cDonantes;

import Sdonaciones.Importador.Importador;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.RepoDonantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/donantes")
public class CDonantes {

    private final Importador importadorCSV = Importador.GetInstance();
    private final RepoDonantes repoDonantes;

    public CDonantes(RepoDonantes repoDonantes) {
        this.repoDonantes = repoDonantes;
    }

    // READ - Obtener todas las Personas
    @GetMapping("")
    public ResponseEntity<List<Donante>> getAllPersonas() {
        return ResponseEntity.ok(repoDonantes.listarTodos());
    }

    // READ - Obtener una persona por correoElectronico
    @GetMapping("/")
    public ResponseEntity<Donante> getPersonaByEmail(@RequestParam String correoElectronico) {
        return ResponseEntity.ok(repoDonantes.buscarPorCorreo(correoElectronico));
    }

    // CREATE - Agregar un nueva persona
    @PostMapping("")
    public ResponseEntity<Donante> createPersona(@RequestBody Donante persona) {
        repoDonantes.guardar(persona);
        return ResponseEntity.ok(persona);

    }

    // Import
    @PostMapping("/importador")
    public ResponseEntity<String> importarCSV(@RequestParam String rutaArchivo) {
        importadorCSV.setRepositorioDonadores(repoDonantes);

        boolean importado = importadorCSV.importarCsv(rutaArchivo);

        if (importado) {
            return ResponseEntity.ok("Archivo importado correctamente");
        }

        return ResponseEntity.badRequest().body("No se pudo importar el archivo");
    }

    // UPDATE - Actualizar una persona existente
    //Con estos datos (tipo persona, tipo doc y nro doc) debe ser posible ubicar a la
    //persona donante en el sistema. En caso
    //contrario, se le debe crear un usuario --> Una vez creado el usuario/donante en el sistema
    @PutMapping("")
    public ResponseEntity<Donante> updateDonante(@RequestParam String tipoD, @RequestParam String doc, @RequestBody Donante updateDonante) {
        repoDonantes.actualizarDonante(tipoD, doc, updateDonante);
        return ResponseEntity.ok(updateDonante);
    }

    // DELETE - Eliminar una Persona
    @DeleteMapping("")
    public ResponseEntity<String> deletePersona(@RequestParam String tipoD, @RequestParam String doc) {
        return ResponseEntity.ok(repoDonantes.eliminarDonante(tipoD, doc));
//        personas.removeIf(p -> p.getEmail() == correoElectronico);
//        return "Persona con Email " + correoElectronico + " eliminado.";
    }


}
