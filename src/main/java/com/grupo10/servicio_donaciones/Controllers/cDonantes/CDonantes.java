package com.grupo10.servicio_donaciones.Controllers.cDonantes;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;


@RestController
@RequestMapping("/api/donantes")
public class CDonantes {

    private final ServicioDonantes servicioDonantes;
    private final ServicioDonacion servicioDonacion;

    public CDonantes(ServicioDonantes servicioDonantes, ServicioDonacion servicioDonacion, @Qualifier("restClientIncentivos") RestClient restClientIncentivos,
                     @Qualifier("restClientMensajes") RestClient restClientMensajes) {
        this.servicioDonantes = servicioDonantes;
        this.servicioDonacion = servicioDonacion;
    }

    // READ - Obtener todas las Personas
    @GetMapping("")
    public ResponseEntity<List<Donante>> getAllPersonas(@RequestParam(value = "tipoD", required = false) String tipoD, @RequestParam(value = "doc", required = false) String doc) {
        if (tipoD != null && doc != null) {
            servicioDonantes.obtenerPersonaPorDocumento(tipoD, doc);
        }
        return ResponseEntity.ok(servicioDonantes.getAllPersonas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donante> getPersonaById(@PathVariable Long id) {
        return ResponseEntity.ok(servicioDonantes.obtenerPorId(id));
    }

    // CREATE - Agregar un nueva persona
    @PostMapping("")
    public ResponseEntity<Donante> createPersona(@RequestBody Donante persona) {
        return ResponseEntity.ok(servicioDonantes.createPersona(persona));

    }

    // Agregar una nueva Donacion
    @PostMapping("/{idDonante}/donaciones")
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion, @PathVariable Long idDonante) {
        return ResponseEntity.ok(servicioDonacion.createDonacion(donacion, idDonante));

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
    @PutMapping("/{id}")
    public ResponseEntity<Donante> updateDonante(@PathVariable Long id, @RequestBody Donante updateDonante) {
        return ResponseEntity.ok(servicioDonantes.updateDonante(id, updateDonante));
    }

    // DELETE - Eliminar una Persona
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable Long id) {
        return ResponseEntity.ok(servicioDonantes.deletePersona(id));
    }


}
