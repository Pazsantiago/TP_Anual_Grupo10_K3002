package com.Donatrack.Logistica.dto;

import java.util.List;

public record DonacionAsignadaDTO(Long idAsignada, Long idEntidad, List<Long> idDonantes,
                                  String direccionEntidad, List<BultoDTO> bultos) {
    public record BultoDTO(double peso, double volumen, double altura, List<BienDTO> bienes) {}
    public record BienDTO(String nombre, int cantidad, String tipo) {}

    /** Constructor sin bultos, por compatibilidad con el contrato actual de Donaciones. */
    public DonacionAsignadaDTO(Long idAsignada, Long idEntidad, List<Long> idDonantes, String direccionEntidad) {
        this(idAsignada, idEntidad, idDonantes, direccionEntidad, List.of());
    }
}