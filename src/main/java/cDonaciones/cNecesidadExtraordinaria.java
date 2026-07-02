//package cDonaciones;
//
////import donatrack.dominio.donacion.Donacion;
//import Sdonaciones.dominio.necesidad.NecesidadExtraordinaria;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@RestController
////@RequestMapping("/CNecesidadExtraordinaria")
//public class CNecesidadExtraordinaria {
//
//    private List<CNecesidadExtraordinaria> necesidades = new ArrayList<>();
//
//    // Inicializamos con algunos datos
//    public CNecesidadExtraordinaria() {
//        //necesidades.add(new entidad("La Carbonilla"));
//        //necesidades.add(new entidad("Jardin de Infantes Nro 2"));
//    }
//
//    // READ - Obtener todas las Necesidades Extraordinarias
//    //@GetMapping
//    pubic List<NecesidadExtraordinaria> getAllNecesidades() {
//        return necesidades;
//    }
//
//    // READ - Obtener una Necesidad Extraordinaria por ID
//    //@GetMapping("/{idNecesidad}")
//    public NecesidadExtraordinaria getNecesidadeByIdNecesidad(@PathVariable Int idNecesidad) {
//        return necesidad.stream()
//                .filter(n -> n.getIdNecesidad() == idNecesidad)
//                .findFirst()
//                .orElse(null);
//    }
//
//    // CREATE - Agregar una nueva Necesidad Extraordinaria
//    //@PostMapping("/{idNecesidad}")
//    public NecesidadExtraordinaria createNecesidad(@RequestBody NecesidadExtraordinaria idNecesidad) {
//        necesidades.add(necesidad);
//        return necesidades;
//
//    }
//
//    // UPDATE - Actualizar una Necesidad Extraordinaria existente
//    //@PutMapping("/{idNecesidad}")
//    public NecesidadExtraordinaria updateNecesidad(@PathVariable Int idNecesidad, @RequestBody NecesidadExtraordinaria updatedNecesidad) {
//        for (NecesidadExtrardinaria n : necesidades) {
//            if (n.getIdNecesidad() == idNecesidad) {
//                n.setDescripcion(updatedNecesidad.getDescripcion());
//                return n;
//            }
//        }
//        return null;
//    }
//
//    // DELETE - Eliminar una Necesidad Extraordinaria
//    //@DeleteMapping("/{idNecesidadl}")
//    public String deleteNecesidad(@PathVariable Int idNecesidad) {
//        necesidades.removeIf(e -> e.getIdNecesidad() == idNecesidad);
//        return "Necedidad Extraordinario eliminada.";
//    }
//}
