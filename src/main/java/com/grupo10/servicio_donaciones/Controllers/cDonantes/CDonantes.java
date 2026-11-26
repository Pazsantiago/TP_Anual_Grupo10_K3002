package com.grupo10.servicio_donaciones.Controllers.cDonantes;

import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.dtos.request.DonanteRequestDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/donantes")
@RequiredArgsConstructor
public class CDonantes {

    private final DTOSMapper mapper;
    private final ServicioDonantes servicioDonantes;

    // READ - Obtener todos los Donantes
    @GetMapping("")
    public ResponseEntity<?> getAllPersonas(
            @RequestParam(value = "tipoD", required = false) String tipoD,
            @RequestParam(value = "doc", required = false) String doc) {
        try {
            if (tipoD != null && doc != null) {
                return ResponseEntity.ok(List.of(mapper.deDominioAResponse(servicioDonantes.obtenerPersonaPorDocumento(tipoD, doc))));
            }
            return ResponseEntity.ok(
                    servicioDonantes.getAllPersonas().stream()
                            .map(mapper::deDominioAResponse)
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // READ - Obtener Donante por email
    @GetMapping("/email")
    public ResponseEntity<?> getPersonaByEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioDonantes.obtenerPorEmail(email)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // READ - Obtener Donante por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioDonantes.obtenerPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // CREATE - Agregar un nuevo donante
    @PostMapping("")
    public ResponseEntity<?> createPersona(@RequestBody DonanteRequestDTO dto) {
        try {
            Donante donante = mapper.deDTORequestADominio(dto);
            Donante creado = servicioDonantes.createPersona(donante);
            return ResponseEntity.ok(mapper.deDominioAResponse(creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Alta por el admin
    @PostMapping("/admin")
    public ResponseEntity<?> createPersonaByAdmin(
            @RequestBody DonanteRequestDTO dto,
            @RequestParam("email") String email) {
        try {
            Donante donante = mapper.deDTORequestADominio(dto);
            Donante creado = servicioDonantes.createPersona(donante);
            servicioDonantes.crearUsuarioKeycloak(creado, email);
            return ResponseEntity.ok(mapper.deDominioAResponse(creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Importar CSV
    @PostMapping("/importador")
    public ResponseEntity<?> importarCSV(@RequestParam("archivo") MultipartFile archivo) {
        try {
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío.");
            }
            return ResponseEntity.ok(servicioDonantes.importarCSV(archivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    // UPDATE - Actualizar donante existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonante(@PathVariable Long id, @RequestBody DonanteRequestDTO dto) {
        try {
            Donante donante = mapper.deDTORequestADominio(dto);
            Donante actualizado = servicioDonantes.updateDonante(id, donante);
            return ResponseEntity.ok(mapper.deDominioAResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // DELETE - Eliminar un donante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(servicioDonantes.deletePersona(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}