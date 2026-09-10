package com.Donatrack.Logistica.services.impl;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.repository.CamionRepository;
import com.Donatrack.Logistica.services.CamionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CamionServiceImpl implements CamionService {

    private final CamionRepository camionRepository;

    public CamionServiceImpl(CamionRepository camionRepository) {
        this.camionRepository = camionRepository;
    }

    @Override
    public Camion guardar(Camion camion) {
        return camionRepository.save(camion);
    }

    @Override
    public List<Camion> obtenerCamiones() {
        return camionRepository.findAll();
    }
}
