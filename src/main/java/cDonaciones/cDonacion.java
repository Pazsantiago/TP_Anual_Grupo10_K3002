//package cDonaciones;
////import donatrack.dominio.donacion.Donacion;
//import Sdonaciones.dominio.donacion.*;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@RestController
////@RequestMapping("/Donacion")
//public class CDonacion {
//
//    private List<Donacion> donaciones = new ArrayList<>();
//
//    // Inicializamos con algunos datos
//    public CDonacion() {
//        //donaciones.add(new Donacion("La Carbonilla"));
//        //donaciones.add(new Donacion("Jardin de Infantes Nro 2"));
//    }
//
//    // READ - Obtener todas las Donaciones
//    //@GetMapping
//    public List<Donacion> getAllDonaciones() {
//
//        return donaciones;
//    }
//
//    // READ - Obtener una Donaciones por idDonacion
//    //@GetMapping("/{idDonacion}")
//    public Donacion getDonacionById(@PathVariable Int idDonacion) {
//        return donaciones.stream()
//                .filter(d -> d.getIdDonacion() == idDonacion)
//                .findFirst()
//                .orElse(null);
//    }
//
//    // CREATE - Agregar una nueva Donacion
//    //@PostMapping("/{idDonacion}")
//    public Donacion createDonacion(@RequestBody Donacion donacion) {
//        donaciones.add(donacion);
//        return donaciones;
//
//    }
//
//    // UPDATE - Actualizar una Donacion existente
//    //@PutMapping("/{idDonacion}")
//    public Donacion updateDonacion(@PathVariable Int idDonacion, @RequestBody Donacion updatedDonacion) {
//        for (Donacion d : donaciones) {
//            if (d.getIdDonacion() == idDonacion) {
//                d.setDescripcion(updatedDonacion.getDescripcion());
//
//                return d;
//            }
//        }
//        return null;
//    }
//
//    // DELETE - Eliminar una Donacion
//    //@DeleteMapping("/{idDonacion}")
//    public String deleteDonacion(@PathVariable Int idDonacion) {
//        donaciones.removeIf(d -> d.getIdDonacion() == idDonacion);
//        return "Donacion eliminada.";
//    }
//};
