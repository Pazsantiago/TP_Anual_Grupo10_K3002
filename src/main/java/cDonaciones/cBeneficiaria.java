//package cDonaciones
//
//-Donaciones.cBeneficiaria;
//
////import donatrack.dominio.donacion.Donacion;
//import donatrack.Servicio-Donaciones.Dominio.EntidadBeneficiaria;
//
//import //org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@RestController
////@RequestMapping("/EntidadBeneficiaria")
//public class CBeneficiaria {
//
//    private List<EntidadBeneficiaria> beneficiarias = new ArrayList<>();
//
//    // Inicializamos con algunos datos
//    public CBeneficiaria() {
//        //entidades.add(new entidad("La Carbonilla"));
//        //entidades.add(new entidad("Jardin de Infantes Nro 2"));
//    }
//
//    // READ - Obtener todas las Entidades Beneficiaria
//    //@GetMapping
//    public List<EntidadBeneficiaria> getAllEntidades() {
//        return entidades;
//    }
//
//    // READ - Obtener una Entidad Beneficiaria por RazonSocial
//    //@GetMapping("/{razonSocial}")
//    public EntidadBeneficiaria getEntidadByEmail(@PathVariable String razonSocial) {
//        return entidad.stream()
//                .filter(p -> p.getRazonSocial() == razonSocial)
//                .findFirst()
//                .orElse(null);
//    }
//
//    // CREATE - Agregar una nueva Entidad Beneficiaria
//    //@PostMapping("/{razonSocial}")
//    public EntidadBeneficiaria createEntidad(@RequestBody EntidadBeneficiaria entidad) {
//        entidades.add(entidad);
//        return entidades;
//
//    }
//
//    // UPDATE - Actualizar una Entidad Beneficiaria existente
//    //@PutMapping("/{razonSocial}")
//    public EntidadBeneficiaria updateEntidad(@PathVariable String razonSocial, @RequestBody EntidadBeneficiaria updatedEntidad) {
//        for (EntidadBeneficiaria e : Entidades) {
//            if (e.getRazonSocial() == razonSocial) {
//                e.setRazonSocial(updatedEntidad.getRazonSocial());
//                e.setEmail(updatedEntidad.getEmail());
//                return e;
//            }
//        }
//        return null;
//    }
//
//    // DELETE - Eliminar una Entidad Beneficiaria
//    //@DeleteMapping("/{razonSocial}")
//    public String deleteEntidad(@PathVariable String razonSocial) {
//        entidades.removeIf(e -> e.getRazonSocial() == razonSocial);
//        return "Entidad " + razonSocial + " eliminada.";
//    }
//}