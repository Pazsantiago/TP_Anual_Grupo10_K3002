package org.example.donacionesfrontend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntidadBeneficiariaResponseDTO {
    private Long id;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String correoRepresentante;
}