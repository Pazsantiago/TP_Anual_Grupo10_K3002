package org.example.donacionesfrontend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionAsignadaResponseDTO {
    private Long idAsignada;
    private Long idEntidad;
    private String direccionEntidad;
    private List<DonacionSegmentadaResponseDTO> donacionesSegmentadas = new ArrayList<>();
}