package cBeneficiarias;


import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.repositorios.RepoEntidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidadesBeneficiarias")
public class CBeneficiarias {

    private final RepoEntidades repoBeneficiarias;

    // Inicializamos con algunos datos
    public CBeneficiarias(RepoEntidades repoBeneficiarias) {
        this.repoBeneficiarias = repoBeneficiarias;
        //entidades.add(new entidad("La Carbonilla"));
        //entidades.add(new entidad("Jardin de Infantes Nro 2"));
    }

    // READ - Obtener todas las Entidades Beneficiaria
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiaria>> getAllEntidades() {
        return ResponseEntity.ok(repoBeneficiarias.listarTodas());
    }


    // READ - Obtener una Entidad Beneficiaria por RazonSocial
    @GetMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> getEntidadById(@PathVariable Integer id) {
        return ResponseEntity.ok(repoBeneficiarias.obtenerPorId(id));
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiaria> createEntidad(@RequestBody EntidadBeneficiaria entidad) {
        repoBeneficiarias.guardar(entidad);
        return ResponseEntity.ok(entidad);

    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("")
    public ResponseEntity<EntidadBeneficiaria> updateEntidad(@PathVariable Integer id, @RequestBody EntidadBeneficiaria updatedEntidad) {
        return ResponseEntity.ok(repoBeneficiarias.actualizarEntidad(id, updatedEntidad));
    }

    // DELETE - Eliminar una Entidad Beneficiaria
    @DeleteMapping("")
    public ResponseEntity<String> deleteEntidad(@PathVariable Integer id) {
        return ResponseEntity.ok("Entidad " + repoBeneficiarias.eliminarEntidad(id) + " eliminada.");
    }
}