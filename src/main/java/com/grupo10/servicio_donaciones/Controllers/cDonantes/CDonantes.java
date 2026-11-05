package com.grupo10.servicio_donaciones.Controllers.cDonantes;

import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.dtos.frontend.DonanteDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/donantes")
@RequiredArgsConstructor
public class CDonantes {
    private final DTOSMapper mapper;
    private final ServicioDonantes servicioDonantes;

    // READ - Obtener todas las Personas
    @GetMapping("")
    public ResponseEntity<List<DonanteDTO>> getAllPersonas(@RequestParam(value = "tipoD", required = false) String tipoD, @RequestParam(value = "doc", required = false) String doc) {
        try {
            if (tipoD != null && doc != null) {
                return ResponseEntity.ok(List.of(mapper.aDonanteDTO(servicioDonantes.obtenerPersonaPorDocumento(tipoD, doc))));
            }
            return ResponseEntity.ok(servicioDonantes.getAllPersonas().stream().map(mapper::aDonanteDTO).toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonanteDTO> getPersonaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mapper.aDonanteDTO(servicioDonantes.obtenerPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar un nueva persona
    @PostMapping("")
    public ResponseEntity<DonanteDTO> createPersona(@RequestBody Donante persona) {
        try {
            return ResponseEntity.ok(mapper.aDonanteDTO(servicioDonantes.createPersona(persona)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Import
    @PostMapping("/importador")
    public ResponseEntity<String> importarCSV(@RequestParam String rutaArchivo) {
        try {
            return ResponseEntity.ok(servicioDonantes.importarCSV(rutaArchivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // UPDATE - Actualizar una persona existente
    //Con estos datos (tipo persona, tipo doc y nro doc) debe ser posible ubicar a la
    //persona donante en el sistema. En caso
    //contrario, se le debe crear un usuario --> Una vez creado el usuario/donante en el sistema
    @PutMapping("/{id}")
    public ResponseEntity<DonanteDTO> updateDonante(@PathVariable Long id, @RequestBody Donante updateDonante) {
        try {
            return ResponseEntity.ok(mapper.aDonanteDTO(servicioDonantes.updateDonante(id, updateDonante)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar una Persona
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(servicioDonantes.deletePersona(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
