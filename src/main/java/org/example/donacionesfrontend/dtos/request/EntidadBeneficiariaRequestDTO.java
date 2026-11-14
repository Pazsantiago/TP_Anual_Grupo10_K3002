package org.example.donacionesfrontend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntidadBeneficiariaRequestDTO {
    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    private List<NecesidadRequestDTO> necesidades = new ArrayList<>();

}