package com.grupo10.servicio_donaciones.Controllers.cDonaciones;
//import donatrack.dominio.donacion.Donacion;

import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionAsignada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones-asignadas")
public class CDonacionAsignada {

    private final ServicioDonacion servicioDonacion;

    // Inicializamos con algunos datos
    public CDonacionAsignada(ServicioDonacion servicioDonacion) {
        this.servicioDonacion = servicioDonacion;
    }

    // Conocer las donaciones ya asignadas
    @GetMapping("")
    public ResponseEntity<List<DonacionAsignada>> obtenerDonacionesAsignadas() {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesAsignadas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
