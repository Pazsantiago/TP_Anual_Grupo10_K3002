package Controllers.cBeneficiarias;


import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import Services.ServiceDonacionAsignada.ServicioDonacionAsignada;
import Services.ServiceNecesidades.ServicioNecesidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidadesBeneficiarias")
public class CBeneficiarias {

    private final ServicioBeneficiarias servicioBeneficiarias;
    private final ServicioNecesidades servicioNecesidades;
    private final ServicioDonacionAsignada servicioDonacionAsignada;

    // Inicializamos con algunos datos
    public CBeneficiarias(ServicioBeneficiarias servicioBeneficiarias, ServicioNecesidades servicioNecesidades,
                          ServicioDonacionAsignada servicioDonacionAsignada) {
        this.servicioBeneficiarias = servicioBeneficiarias;
        this.servicioNecesidades = servicioNecesidades;
        this.servicioDonacionAsignada = servicioDonacionAsignada;
    }

    // READ - Obtener todas las Entidades Beneficiaria
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiaria>> getAllEntidades() {
        return ResponseEntity.ok(servicioBeneficiarias.getAllEntidades());
    }


    // READ - Obtener una Entidad Beneficiaria por id
    @GetMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> getEntidadById(@PathVariable Long idEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.getEntidadById(idEntidad));
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiaria> createEntidad(@RequestBody EntidadBeneficiaria entidad) {
        return ResponseEntity.ok(servicioBeneficiarias.createEntidad(entidad));
    }

    // CREATE - Agregar una nueva Necesidad
    @PostMapping("/{idEntidad}/necesidades")
    public ResponseEntity<Necesidad> createNecesidad(@PathVariable Long idEntidad, @RequestBody Necesidad necesidad) {
        return ResponseEntity.ok(servicioNecesidades.createNecesidad(idEntidad, necesidad));
    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> updateEntidad(@PathVariable Long idEntidad, @RequestBody EntidadBeneficiaria updatedEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.updateEntidad(idEntidad, updatedEntidad));
    }

    // DELETE - Eliminar una Entidad Beneficiaria
    @DeleteMapping("/{idEntidad}")
    public ResponseEntity<String> deleteEntidad(@PathVariable Long idEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.deleteEntidad(idEntidad));
    }

    // DADA una entidad, se le asigna finalmente la donacion segmentada a su necesidad.
    @PostMapping("/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}")
    public ResponseEntity<DonacionAsignada> asignarDonacionAEntidadPorNecesidad(@PathVariable Long idEntidad, @PathVariable Long idNecesidad, @PathVariable Long idDonacion) {
        return ResponseEntity.ok(servicioDonacionAsignada.asignarDonacionAEntidadConNecesidad(idDonacion, idEntidad, idNecesidad));
    }

    //Se le agrega una donacion segmentada a la asignada para ir completando
    @PostMapping("/donacionesAsignadas/{idDonacionAsignada}/donaciones")
    public ResponseEntity<String> agregarDonacionADonacionAsignadaIncompleta(@PathVariable Long idDonacionAsignada, @RequestBody Long idDonacionSegmentada) {
        return ResponseEntity.ok(servicioDonacionAsignada.agregarDonacionADonacionAsignadaIncompleta(idDonacionAsignada, idDonacionSegmentada));
    }


}