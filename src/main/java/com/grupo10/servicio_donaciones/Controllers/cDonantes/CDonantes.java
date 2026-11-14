package com.grupo10.servicio_donaciones.Controllers.cDonantes;

import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.dtos.request.DonanteRequestDTO;
import com.grupo10.servicio_donaciones.dtos.response.DonanteResponseDTO;
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

    // READ - Obtener todos los Donantes
    @GetMapping("")
    public ResponseEntity<List<DonanteResponseDTO>> getAllPersonas(
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Obtener Donante por ID
    @GetMapping("/{id}")
    public ResponseEntity<DonanteResponseDTO> getPersonaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioDonantes.obtenerPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar un nuevo donante
    @PostMapping("")
    public ResponseEntity<DonanteResponseDTO> createPersona(@RequestBody DonanteRequestDTO dto) {
        try {
            Donante donante = mapper.deDTORequestADominio(dto);
            Donante creado = servicioDonantes.createPersona(donante);
            return ResponseEntity.ok(mapper.deDominioAResponse(creado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Importar CSV
    @PostMapping("/importador")
    public ResponseEntity<String> importarCSV(@RequestParam String rutaArchivo) {
        try {
            return ResponseEntity.ok(servicioDonantes.importarCSV(rutaArchivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Actualizar donante existente
    @PutMapping("/{id}")
    public ResponseEntity<DonanteResponseDTO> updateDonante(@PathVariable Long id, @RequestBody DonanteRequestDTO dto) {
        try {
            Donante donante = mapper.deDTORequestADominio(dto);
            Donante actualizado = servicioDonantes.updateDonante(id, donante);
            return ResponseEntity.ok(mapper.deDominioAResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar un donante
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(servicioDonantes.deletePersona(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}