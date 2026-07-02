//package cDonaciones;
//
////import donatrack.dominio.donacion.Donacion;
//import Sdonaciones.dominio.donante.*;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@RestController
////@RequestMapping("/PersonaHumana")
//public class CPersonaHumana {
//
//    private List<PersonaHumana> personas = new ArrayList<>();
//
//    // Inicializamos con algunos datos
//    public CPersonaHumana() {
//        //personas.add(new persona("","","Juan", "Anchorena", "juan@example.com"));
//        //personas.add(new persona("","","Ana", "Urquiza", "ana@example.com"));
//    }
//
//    // READ - Obtener todas las Personas
//    //@GetMapping
//    public List<PersonaHumana> getAllPersonas() {
//        return personas;
//    }
//
//    // READ - Obtener una persona por correoElectronico
//    //@GetMapping("/{correoElectronico}")
//    public PersonaHumana getPersonaByEmail(@PathVariable String correoElectronico) {
//        return persona.stream()
//                .filter(p -> p.getEmail() == correoElectronico)
//                .findFirst()
//                .orElse(null);
//    }
//
//    // CREATE - Agregar un nueva persona
//    //@PostMapping
//    public PersonaHumana createPersona(@RequestBody PersonaHumana persona) {
//        personas.add(persona);
//        return persona;
//
//    }
//
//    // UPDATE - Actualizar una persona existente
//    //@PutMapping("/{correoElectronico}")
//    public PersonaHumana updatePersona(@PathVariable String correoElectronico, @RequestBody PersonaHumana updatedPerson) {
//        for (PersonaHumana d : personas) {
//            if (p.getEmail() == correoElectronico) {
//                p.setNombre(updatedPersona.getNombre());
//                p.setEmail(updatedPersona.getEmail());
//                return p;
//            }
//        }
//        return null;
//    }
//
//    // DELETE - Eliminar una Persona
//    //@DeleteMapping("/{correoElectronico}")
//    public String deletePersona(@PathVariable String correoElectronico) {
//        personas.removeIf(p -> p.getEmail() == correoElectronico);
//        return "Persona con Email " + correoElectronico + " eliminado.";
//    }
//}
