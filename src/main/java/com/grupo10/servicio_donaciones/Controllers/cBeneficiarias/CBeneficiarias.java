package com.grupo10.servicio_donaciones.Controllers.cBeneficiarias;

import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.Services.ServiceDonacionAsignada.ServicioDonacionAsignada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.dtos.request.EntidadBeneficiariaRequestDTO;
import com.grupo10.servicio_donaciones.dtos.response.EntidadBeneficiariaResponseDTO;
import com.grupo10.servicio_donaciones.excepciones.DonacionVencidaException;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entidadesBeneficiarias")
@RequiredArgsConstructor
public class CBeneficiarias {

    private final DTOSMapper mapper;
    private final ServicioBeneficiarias servicioBeneficiarias;
    private final ServicioDonacionAsignada servicioDonacionAsignada;

    public Map<String, Object> aDonacionAsignadaREST(DonacionAsignada donacionAsignada) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", donacionAsignada.getId());
        dto.put("id_necesidad", donacionAsignada.getNecesidadResuelta().getId());
        dto.put("id_segmentadas", donacionAsignada.getDonacionesSegmentadas().stream().map(DonacionSegmentada::getId).toList());
        dto.put("fecha", donacionAsignada.getFechaHora());
        dto.put("asignacionCompleta", donacionAsignada.getAsignacionCompleta());
        return dto;
    }

    public Map<String, Object> aDonacionSegmentadaFallidaREST(Long idSegmentada, String mensaje) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", idSegmentada);
        dto.put("Error: ", mensaje);
        return dto;
    }

    // READ - Obtener todas las Entidades Beneficiarias
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiariaResponseDTO>> getAllEntidades() {
        try {
            return ResponseEntity.ok(
                    servicioBeneficiarias.getAllEntidades().stream()
                            .map(mapper::deDominioAResponse)
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Obtener una Entidad Beneficiaria por id
    @GetMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiariaResponseDTO> getEntidadById(@PathVariable Long idEntidad) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioBeneficiarias.getEntidadById(idEntidad)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiariaResponseDTO> createEntidad(@RequestBody EntidadBeneficiariaRequestDTO dto) {
        try {
            EntidadBeneficiaria entidad = mapper.deDTORequestADominio(dto);
            EntidadBeneficiaria creada = servicioBeneficiarias.createEntidad(entidad);
            return ResponseEntity.ok(mapper.deDominioAResponse(creada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiariaResponseDTO> updateEntidad(@PathVariable Long idEntidad, @RequestBody EntidadBeneficiariaRequestDTO dto) {
        try {
            EntidadBeneficiaria entidad = mapper.deDTORequestADominio(dto);
            EntidadBeneficiaria actualizada = servicioBeneficiarias.updateEntidad(idEntidad, entidad);
            return ResponseEntity.ok(mapper.deDominioAResponse(actualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Eliminar una Entidad Beneficiaria
    @DeleteMapping("/{idEntidad}")
    public ResponseEntity<String> deleteEntidad(@PathVariable Long idEntidad) {
        try {
            return ResponseEntity.ok(servicioBeneficiarias.deleteEntidad(idEntidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Asignar donación segmentada a la necesidad de la entidad
    @PostMapping("/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}")
    public ResponseEntity<Map<String, Object>> asignarDonacionAEntidadPorNecesidad(
            @PathVariable Long idEntidad,
            @PathVariable Long idNecesidad,
            @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(aDonacionAsignadaREST(
                    servicioDonacionAsignada.asignarDonacionAEntidadConNecesidad(idDonacion, idEntidad, idNecesidad)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Agregar una donación segmentada a una asignación incompleta
    @PostMapping("/donacionesAsignadas/{idDonacionAsignada}/donaciones/{idDonacionSegmentada}")
    public ResponseEntity<Map<String, Object>> agregarDonacionADonacionAsignadaIncompleta(
            @PathVariable Long idDonacionAsignada,
            @PathVariable Long idDonacionSegmentada) {
        try {
            return ResponseEntity.ok(aDonacionAsignadaREST(
                    servicioDonacionAsignada.agregarDonacionADonacionAsignadaIncompleta(idDonacionAsignada, idDonacionSegmentada)
            ));
        } catch (DonacionVencidaException donacionVencidaException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(aDonacionSegmentadaFallidaREST(idDonacionSegmentada, donacionVencidaException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}