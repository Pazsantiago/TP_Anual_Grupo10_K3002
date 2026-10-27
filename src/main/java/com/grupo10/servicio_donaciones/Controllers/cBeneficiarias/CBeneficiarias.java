package com.grupo10.servicio_donaciones.Controllers.cBeneficiarias;


import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.Services.ServiceDonacionAsignada.ServicioDonacionAsignada;
import com.grupo10.servicio_donaciones.Services.ServiceNecesidades.ServicioNecesidades;
import com.grupo10.servicio_donaciones.excepciones.DonacionVencidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<String, Object> aDonacionAsignadaREST(DonacionAsignada donacionAsignada) {

        Map<String, Object> dto = new HashMap<>();
        dto.put("id", donacionAsignada.getId());
        dto.put("id_necesidad", donacionAsignada.getNecesidadResuelta().getId());
        dto.put("id_segmentadas", donacionAsignada.getDonacionesSegmentadas().stream().map(DonacionSegmentada::getId));
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

    // READ - Obtener todas las Entidades Beneficiaria
    @GetMapping("")
    public ResponseEntity<List<EntidadBeneficiaria>> getAllEntidades() {
        try {
            return ResponseEntity.ok(servicioBeneficiarias.getAllEntidades());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // READ - Obtener una Entidad Beneficiaria por id
    @GetMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> getEntidadById(@PathVariable Long idEntidad) {
        try {
            return ResponseEntity.ok(servicioBeneficiarias.getEntidadById(idEntidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar una nueva Entidad Beneficiaria
    @PostMapping("")
    public ResponseEntity<EntidadBeneficiaria> createEntidad(@RequestBody EntidadBeneficiaria entidad) {
        try {
            return ResponseEntity.ok(servicioBeneficiarias.createEntidad(entidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Agregar una nueva Necesidad
    @PostMapping("/{idEntidad}/necesidades")
    public ResponseEntity<Necesidad> createNecesidad(@PathVariable Long idEntidad, @RequestBody Necesidad necesidad) {
        try {
            return ResponseEntity.ok(servicioNecesidades.createNecesidad(idEntidad, necesidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Actualizar una Entidad Beneficiaria existente
    @PutMapping("/{idEntidad}")
    public ResponseEntity<EntidadBeneficiaria> updateEntidad(@PathVariable Long idEntidad, @RequestBody EntidadBeneficiaria updatedEntidad) {
        try {
            return ResponseEntity.ok(servicioBeneficiarias.updateEntidad(idEntidad, updatedEntidad));
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

    // DADA una entidad, se le asigna finalmente la donacion segmentada a su necesidad.
    @PostMapping("/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}")
    public ResponseEntity<Map<String, Object>> asignarDonacionAEntidadPorNecesidad(@PathVariable Long idEntidad, @PathVariable Long idNecesidad, @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(aDonacionAsignadaREST(servicioDonacionAsignada.asignarDonacionAEntidadConNecesidad(idDonacion, idEntidad, idNecesidad)));
        } catch (DonacionVencidaException donacionVencidaException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(aDonacionSegmentadaFallidaREST(idDonacion, donacionVencidaException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Se le agrega una donacion segmentada a la asignada para ir completando
    @PostMapping("/donacionesAsignadas/{idDonacionAsignada}/donaciones/{idDonacionSegmentada}")
    public ResponseEntity<Map<String, Object>> agregarDonacionADonacionAsignadaIncompleta(@PathVariable Long idDonacionAsignada, @PathVariable Long idDonacionSegmentada) {
        try {
            return ResponseEntity.ok(aDonacionAsignadaREST(servicioDonacionAsignada.agregarDonacionADonacionAsignadaIncompleta(idDonacionAsignada, idDonacionSegmentada)));
        } catch (DonacionVencidaException donacionVencidaException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(aDonacionSegmentadaFallidaREST(idDonacionSegmentada, donacionVencidaException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}