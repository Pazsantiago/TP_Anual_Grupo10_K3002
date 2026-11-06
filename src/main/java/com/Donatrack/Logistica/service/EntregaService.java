package com.Donatrack.Logistica.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Donatrack.Logistica.domain.Bien;
import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.dto.DonacionAsignadaDTO;
import com.Donatrack.Logistica.repository.EntregaRepository;

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
            e.setIdDonantes(dto.idDonantes());
            if (dto.bultos() != null) {
                e.setBultos(dto.bultos().stream().map(b -> {
                    Bulto bulto = new Bulto(b.peso(), b.volumen(), b.altura());
                    if (b.bienes() != null)
                        b.bienes().forEach(x -> bulto.getBienes().add(new Bien(x.nombre(), x.cantidad(), x.tipo())));
                    return bulto;
                }).toList());
            }
            return repo.save(e);
        });
    }

    public List<Entrega> registrarTodas(List<DonacionAsignadaDTO> dtos) {
        List<Entrega> out = new ArrayList<>();
        dtos.forEach(d -> out.add(registrar(d)));
        return out;
    }
}