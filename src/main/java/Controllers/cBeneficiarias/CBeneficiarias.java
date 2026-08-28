package Controllers.cBeneficiarias;


import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidadesBeneficiarias")
public class CBeneficiarias {

    private final ServicioBeneficiarias servicioBeneficiarias;

    // Inicializamos con algunos datos
    public CBeneficiarias(ServicioBeneficiarias servicioBeneficiarias) {
        this.servicioBeneficiarias = servicioBeneficiarias;
    }

    // READ - Obtener todas las Entidades Beneficiaria
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiaria>> getAllEntidades() {
        return ResponseEntity.ok(servicioBeneficiarias.getAllEntidades());
    }


    // READ - Obtener una Entidad Beneficiaria por id
    @GetMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> getEntidadById(@PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.getEntidadById(idEntidad));
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiaria> createEntidad(@RequestBody EntidadBeneficiaria entidad) {
        return ResponseEntity.ok(servicioBeneficiarias.createEntidad(entidad));
    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> updateEntidad(@PathVariable Integer idEntidad, @RequestBody EntidadBeneficiaria updatedEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.updateEntidad(idEntidad, updatedEntidad));
    }

    // DELETE - Eliminar una Entidad Beneficiaria
    @DeleteMapping("/{idEntidad}")
    public ResponseEntity<String> deleteEntidad(@PathVariable Integer idEntidad) {
        return ResponseEntity.ok(servicioBeneficiarias.deleteEntidad(idEntidad));
    }
}