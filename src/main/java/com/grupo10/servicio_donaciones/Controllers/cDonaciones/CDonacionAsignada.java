package com.grupo10.servicio_donaciones.Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/donaciones-asignadas")
@RequiredArgsConstructor
public class CDonacionAsignada {

    private final ServicioDonacion servicioDonacion;
    private final DTOSMapper mapper;

    // Conocer las donaciones ya asignadas
    @GetMapping("")
    public ResponseEntity<?> obtenerDonacionesAsignadas() {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesAsignadas().stream().map(mapper::deDominioAResponse).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
