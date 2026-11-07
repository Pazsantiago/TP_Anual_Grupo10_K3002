package com.Donatrack.Logistica.dto;

import java.util.List;

public record DonacionAsignadaDTO(Long idAsignada, List<DonacionSegmentadaDTO> donacionesSegmentadas,
                                  Long idEntidad, String direccionEntidad) {
    public record DonacionSegmentadaDTO(Long idSegmentada, Integer cantidad, String unidad) {}
}