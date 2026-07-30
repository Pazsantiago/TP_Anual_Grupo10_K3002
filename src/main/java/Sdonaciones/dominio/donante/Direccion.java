package Sdonaciones.dominio.donante;

import lombok.Data;

@Data
public class Direccion {
    private String calle;
    private Integer altura;
    private String localidad;
    private String codigoPostal;
}
