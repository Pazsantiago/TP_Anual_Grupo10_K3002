package com.Donatrack.Logistica.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.domain.UbicacionCamion;
import com.Donatrack.Logistica.dto.SeguimientoDTO;
import com.Donatrack.Logistica.repository.CamionRepository;
import com.Donatrack.Logistica.repository.EntregaRepository;

@Service
public class SeguimientoService {
    private final EntregaRepository entregaRepo;
    private final CamionRepository camionRepo;

    public SeguimientoService(EntregaRepository entregaRepo, CamionRepository camionRepo) {
        this.entregaRepo = entregaRepo;
        this.camionRepo = camionRepo;
    }

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