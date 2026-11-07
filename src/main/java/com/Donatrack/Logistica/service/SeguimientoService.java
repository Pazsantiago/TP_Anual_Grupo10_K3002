package com.Donatrack.Logistica.service;

import com.Donatrack.Logistica.domain.*;
import com.Donatrack.Logistica.dto.SeguimientoDTO;
import com.Donatrack.Logistica.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeguimientoService {
    private final EntregaRepository entregaRepo;
    private final CamionRepository camionRepo;

    public SeguimientoService(EntregaRepository entregaRepo, CamionRepository camionRepo) {
        this.entregaRepo = entregaRepo;
        this.camionRepo = camionRepo;
    }

    /** Donaciones ya no envía donantes: queda vacío salvo que se carguen. */
    public List<SeguimientoDTO> porDonante(Long idDonante) {
        return armar(entregaRepo.findByIdDonantesAndEstado(idDonante, EstadoEntrega.EN_TRASLADO));
    }

    public List<SeguimientoDTO> porEntidad(Long idEntidad) {
        return armar(entregaRepo.findByIdEntidadAndEstado(idEntidad, EstadoEntrega.EN_TRASLADO));
    }

    private List<SeguimientoDTO> armar(List<Entrega> entregas) {
        Map<String, UbicacionCamion> cache = new HashMap<>();
        List<SeguimientoDTO> out = new ArrayList<>();
        for (Entrega e : entregas) {
            UbicacionCamion u = cache.computeIfAbsent(e.getPatenteCamion(),
                    p -> camionRepo.findByPatente(p).map(Camion::getUltimaUbicacion).orElse(null));
            out.add(new SeguimientoDTO(e.getId(), e.getIdDonacion(), e.getPatenteCamion(), u));
        }
        return out;
    }
}