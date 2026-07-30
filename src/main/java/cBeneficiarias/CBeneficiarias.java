package cBeneficiarias;


import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.repositorios.RepoEntidades;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidadesBeneficiarias")
public class CBeneficiarias {

    private RepoEntidades repoBeneficiarias;

    // Inicializamos con algunos datos
    public CBeneficiarias() {
        repoBeneficiarias = new RepoEntidades();
        //entidades.add(new entidad("La Carbonilla"));
        //entidades.add(new entidad("Jardin de Infantes Nro 2"));
    }

    // READ - Obtener todas las Entidades Beneficiaria
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiaria>> getAllEntidades() {
        return ResponseEntity.ok(repoBeneficiarias.getEntidadBeneficiarias());
    }

    // READ - Obtener una Entidad Beneficiaria por RazonSocial
    @GetMapping("/")
    public ResponseEntity<EntidadBeneficiaria> getEntidadByEmail(@RequestParam String razonSocial) {
        return ResponseEntity.ok(repoBeneficiarias.getEntidadBeneficiarias().stream()
                .filter(p -> p.getRazonSocial().equals(razonSocial))
                .findFirst()
                .orElse(null));
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiaria> createEntidad(@RequestBody EntidadBeneficiaria entidad) {
        repoBeneficiarias.guardar(entidad);
        return ResponseEntity.ok(entidad);

    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("")
    public ResponseEntity<EntidadBeneficiaria> updateEntidad(@RequestParam String razonSocial, @RequestBody EntidadBeneficiaria updatedEntidad) {
        EntidadBeneficiaria antigua = repoBeneficiarias.getEntidadBeneficiarias().stream().filter(p -> p.getRazonSocial().equals(razonSocial))
                .findFirst().orElse(null);
        repoBeneficiarias.getEntidadBeneficiarias().set(repoBeneficiarias.getEntidadBeneficiarias().indexOf(antigua), updatedEntidad);
        return ResponseEntity.ok(updatedEntidad);
    }

    // DELETE - Eliminar una Entidad Beneficiaria
    @DeleteMapping("")
    public ResponseEntity<String> deleteEntidad(@RequestParam String razonSocial) {
        repoBeneficiarias.getEntidadBeneficiarias().removeIf(e -> e.getRazonSocial().equals(razonSocial));
        return ResponseEntity.ok("Entidad " + razonSocial + " eliminada.");
    }
}