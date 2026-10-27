package com.grupo10.servicio_donaciones.Controllers.cNecesidades;

//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.Services.ServiceNecesidades.ServicioNecesidades;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
public class CNecesidades {

    private final ServicioNecesidades servicioNecesidades;

    // Inicializamos con algunos datos
    public CNecesidades(ServicioNecesidades servicioNecesidades) {
        this.servicioNecesidades = servicioNecesidades;
    }

    // READ - Obtener todas las Necesidades
    @GetMapping("")
    public ResponseEntity<List<Necesidad>> getAllNecesidades() {
        try {
            return ResponseEntity.ok(servicioNecesidades.getAllNecesidades());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Obtener una Necesidad  por ID
    @GetMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> getNecesidadById(@PathVariable Long idNecesidad) {
        try {
            return ResponseEntity.ok(servicioNecesidades.getNecesidadById(idNecesidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // UPDATE - Actualizar una Necesidad existente de una entidad
    @PutMapping("/{idNecesidad}")
    public ResponseEntity<Necesidad> updateNecesidad(@PathVariable Long idNecesidad, @RequestBody Necesidad updatedNecesidad) {
        try {
            return ResponseEntity.ok(servicioNecesidades.updateNecesidad(idNecesidad, updatedNecesidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar una Necesidad
    @DeleteMapping("/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(@PathVariable Long idNecesidad) {
        try {
            return ResponseEntity.ok(servicioNecesidades.deleteNecesidad(idNecesidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
