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
////@RequestMapping("/PersonaJuridica")
//public class CPersonaJuridica {
//
//    private List<PersonaJuridica> personas = new ArrayList<>();
//
//    // Inicializamos con algunos datos
//    public CPersonaJuridica() {
//        //personas.add(new persona("","", "Fundacion Amigos del Barrio", "amigosdelBarrio@example.com"));
//        //personas.add(new persona("","","DTV Argentina SA", "dtv_AR@example.com"));
//    }
//
//    // READ - Obtener todas las Personas Juridica
//    //@GetMapping
//    public List<PersonaJuridica> getAllPersonas() {
//        return personas;
//    }
//
//    // READ - Obtener una persona por correoElectronico
//    //@GetMapping("/{correoElectronico}")
//
//
//    public PersonaJuridica getPersonaByEmail(@PathVariable String correoElectronico) {
//        return persona.stream()
//                .filter(p -> p.getEmail() == correoElectronico)
//                .findFirst()
//                .orElse(null);
//    }
//
//    // CREATE - Agregar un nueva persona Juridica
//    //@PostMapping
//    public PersonaJuridica createPersona(@RequestBody PersonaJuridica persona) {
//        personas.add(persona);
//        return persona;
//
//    }
//
//    // UPDATE - Actualizar una persona juridica existente
//    //@PutMapping("/{correoElectronico}")
//        for (PersonaJurdica p : personas) {
//            if (p.getEmail() == correoElectronico) {
//                p.setNombre(updatedPersona.getNombre());
//                p.setEmail(updatedPersona.getEmail());
//                return p;
//            }
//        }
//        return null;
//    }
//
//    // DELETE - Eliminar una Persona Juridica
//    //@DeleteMapping("/{correoElectronico}")
//    public String deletePersona(@PathVariable String correoElectronico) {
//        personas.removeIf(p -> p.getEmail() == correoElectronico);
//        return "Persona con Email " + correoElectronico + " eliminado.";
//    }
//}