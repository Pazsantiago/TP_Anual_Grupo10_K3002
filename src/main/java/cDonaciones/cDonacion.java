package cDonaciones;
//import donatrack.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.*;

import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.RepositorioDonaciones;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class cDonacion {


    private RepositorioDonaciones repoDonaciones;
    // Inicializamos con algunos datos
    public cDonacion() {
        repoDonaciones = new RepositorioDonaciones();
        Donacion donacion1 = new Donacion();
        donacion1.setDescripcionGeneral("algo");
        donacion1.setId(1);
        repoDonaciones.guardar(donacion1);

    }

    // READ - Obtener todas las Donaciones
    @GetMapping
    public List<Donacion> getAllDonaciones() {

        return repoDonaciones.getDonaciones();
    }
//
    // READ - Obtener una Donaciones por idDonacion
    @GetMapping("/{idDonacion}")
    public Donacion getDonacionById(@PathVariable Integer idDonacion) {
        return repoDonaciones.getDonaciones().stream()
                .filter(d -> d.getId() == idDonacion)
                .findFirst()
                .orElse(null);
    }
//
    // CREATE - Agregar una nueva Donacion
    @PostMapping
    public Donacion createDonacion(@RequestBody Donacion donacion) {
        repoDonaciones.guardar(donacion);
        return donacion;

    }
//
    // UPDATE - Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public Donacion updateDonacion(@PathVariable Integer idDonacion, @RequestBody Donacion updatedDonacion) {
        for (Donacion d : repoDonaciones.getDonaciones()) {
            if (d.getId() == idDonacion) {
                d.setDescripcionGeneral(updatedDonacion.getDescripcionGeneral());

                return d;
            }
        }
        return null;
    }
//
    // DELETE - Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public String deleteDonacion(@PathVariable Integer idDonacion) {
        repoDonaciones.getDonaciones().removeIf(d -> d.getId() == idDonacion);
        return "Donacion eliminada.";
    }
}
