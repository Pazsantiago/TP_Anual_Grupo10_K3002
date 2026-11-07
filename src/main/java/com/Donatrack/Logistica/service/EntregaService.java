package com.Donatrack.Logistica.service;

import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.dto.DonacionAsignadaDTO;
import com.Donatrack.Logistica.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntregaService {
    private final EntregaRepository repo;

    public EntregaService(EntregaRepository repo) { this.repo = repo; }

    /** Idempotente: si la donación ya existe, devuelve la existente. */
    public Entrega registrar(DonacionAsignadaDTO dto) {
        if (dto == null || dto.idAsignada() == null)
            throw new IllegalArgumentException("idAsignada es obligatorio");
        if (dto.idEntidad() == null)
            throw new IllegalArgumentException("idEntidad es obligatorio");
        if (dto.direccionEntidad() == null || dto.direccionEntidad().isBlank())
            throw new IllegalArgumentException("direccionEntidad es obligatoria");

        return repo.findByIdDonacion(dto.idAsignada()).orElseGet(() -> {
            Entrega e = new Entrega(new Direccion(dto.direccionEntidad(), ""));
            e.setIdDonacion(dto.idAsignada());
            e.setIdEntidad(dto.idEntidad());
            if (dto.donacionesSegmentadas() != null)
                e.setIdSegmentadas(dto.donacionesSegmentadas().stream()
                        .map(DonacionAsignadaDTO.DonacionSegmentadaDTO::idSegmentada).toList());
            return repo.save(e);
        });
    }

    public List<Entrega> registrarTodas(List<DonacionAsignadaDTO> dtos) {
        List<Entrega> out = new ArrayList<>();
        dtos.forEach(d -> out.add(registrar(d)));
        return out;
    }
}