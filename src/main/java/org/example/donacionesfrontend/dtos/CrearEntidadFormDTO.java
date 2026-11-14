package org.example.donacionesfrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearEntidadFormDTO {

    // Datos de la Entidad
    private String razonSocial;
    private String telefonoContacto;
    private String correoElectronicoRepresentante;

    // Dirección plana para el formulario
    private String calle;
    private String altura;
    private String localidad;
}