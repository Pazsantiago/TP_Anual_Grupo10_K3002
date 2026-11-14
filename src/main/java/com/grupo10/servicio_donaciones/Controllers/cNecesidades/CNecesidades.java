package com.grupo10.servicio_donaciones.Controllers.cNecesidades;

import com.grupo10.servicio_donaciones.Services.ServiceNecesidades.ServicioNecesidades;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.dtos.request.NecesidadRequestDTO;
import com.grupo10.servicio_donaciones.dtos.response.NecesidadResponseDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidadesBeneficiarias/{idEntidad}/necesidades")
@RequiredArgsConstructor
public class CNecesidades {

    private final DTOSMapper mapper;
    private final ServicioNecesidades servicioNecesidades;

    // READ - Obtener todas las Necesidades
    @GetMapping("")
    public ResponseEntity<List<NecesidadResponseDTO>> getAllNecesidades(@PathVariable Long idEntidad) {
        try {
            return ResponseEntity.ok(
                    servicioNecesidades.getAllNecesidades(idEntidad).stream()
                            .map(mapper::deDominioAResponse)
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Obtener una Necesidad por ID
    @GetMapping("/{idNecesidad}")
    public ResponseEntity<NecesidadResponseDTO> getNecesidadById(
            @PathVariable Long idEntidad,
            @PathVariable Long idNecesidad) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioNecesidades.getNecesidadById(idNecesidad)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar una nueva Necesidad
    @PostMapping("")
    public ResponseEntity<NecesidadResponseDTO> createNecesidad(
            @PathVariable Long idEntidad,
            @RequestBody NecesidadRequestDTO dto) {
        try {
            Necesidad necesidad = mapper.deDTORequestADominio(dto);
            Necesidad creada = servicioNecesidades.createNecesidad(idEntidad, necesidad);
            return ResponseEntity.ok(mapper.deDominioAResponse(creada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Actualizar una Necesidad existente de una entidad
    @PutMapping("/{idNecesidad}")
    public ResponseEntity<NecesidadResponseDTO> updateNecesidad(
            @PathVariable Long idEntidad,
            @PathVariable Long idNecesidad,
            @RequestBody NecesidadRequestDTO dto) {
        try {
            Necesidad necesidad = mapper.deDTORequestADominio(dto);
            Necesidad actualizada = servicioNecesidades.updateNecesidad(idNecesidad, necesidad);
            return ResponseEntity.ok(mapper.deDominioAResponse(actualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar una Necesidad
    @DeleteMapping("/{idNecesidad}")
    public ResponseEntity<String> deleteNecesidad(
            @PathVariable Long idEntidad,
            @PathVariable Long idNecesidad) {
        try {
            return ResponseEntity.ok(servicioNecesidades.deleteNecesidad(idNecesidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}