package org.example.donacionesfrontend.dominio.donante;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Direccion {
    private Long id;
    private String calle;
    private Integer altura;
    private String localidad;
    private String codigoPostal;
}
