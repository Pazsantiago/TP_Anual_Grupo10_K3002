package org.example.donacionesfrontend.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DonacionDTO {
    private Long id;
    private Long idDonante;
    private String descripcionGeneral;
    private List<BienDTO> bienesDeEntrada = new ArrayList<>();
}
