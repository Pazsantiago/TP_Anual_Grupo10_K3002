package cDonantes;

import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.RepoDonantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/donantes")
public class CDonantes {

    private RepoDonantes repoDonantes;

    public CDonantes() {
        repoDonantes = new RepoDonantes();
    }

    // READ - Obtener todas las Personas
    @GetMapping("")
    public ResponseEntity<List<Donante>> getAllPersonas() {
        return ResponseEntity.ok(repoDonantes.getDonantes());
    }

    // READ - Obtener una persona por correoElectronico
    @GetMapping("/")
    public ResponseEntity<Donante> getPersonaByEmail(@RequestParam String correoElectronico) {
        return ResponseEntity.ok(repoDonantes.getDonantes().stream()
                .filter(p -> p.getMediosDeContacto().stream().anyMatch(m -> m.getCorreoElectronico().equals(correoElectronico)))
                .findFirst()
                .orElse(null));
    }

    // CREATE - Agregar un nueva persona
    @PostMapping("")
    public ResponseEntity<Donante> createPersona(@RequestBody Donante persona) {
        repoDonantes.getDonantes().add(persona);
        return ResponseEntity.ok(persona);

    }

    // UPDATE - Actualizar una persona existente
    @PutMapping("")
    public ResponseEntity<Donante> updateDonante(@RequestParam String tipoD, @RequestParam String doc, @RequestBody Donante updateDonante) {
        Donante oldDonante = repoDonantes.getDonantes().stream()
                .filter(p -> p.getPersona().getDocumento().getTipoDocumento().equals(tipoD) &&
                        p.getPersona().getDocumento().getDocumento().equals(doc)
                )
                .findFirst()
                .orElse(null);
        //System.out.println("#-------#%s" + repoDonantes.getDonantes().indexOf(oldDonante));
        repoDonantes.getDonantes().set(repoDonantes.getDonantes().indexOf(oldDonante), updateDonante);
        return ResponseEntity.ok(updateDonante);
    }

    // DELETE - Eliminar una Persona
    @DeleteMapping("")
    public ResponseEntity<String> deletePersona(@RequestParam String tipoD, @RequestParam String doc) {
        repoDonantes.getDonantes().remove(repoDonantes.getDonantes().stream()
                .filter(p -> p.getPersona().getDocumento().getTipoDocumento().equals(tipoD) &&
                        p.getPersona().getDocumento().getDocumento().equals(doc)
                )
                .findFirst()
                .orElse(null));
        return ResponseEntity.ok("Eliminado");
//        personas.removeIf(p -> p.getEmail() == correoElectronico);
//        return "Persona con Email " + correoElectronico + " eliminado.";
    }


}
