package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Donacion;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.repository.DonacionRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    private final DonacionRepository donacionRepository;

    public DonacionController(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    @GetMapping("/pendientes")
    public List<Donacion> obtenerPendientes() {
        return donacionRepository.findByEstadoDonacion(EstadoEntrega.PENDIENTE);
    }
}
