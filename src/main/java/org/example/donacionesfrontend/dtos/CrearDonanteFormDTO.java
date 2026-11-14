package org.example.donacionesfrontend.dtos;

import lombok.Data;

@Data
public class CrearDonanteFormDTO {
    private String tipoPersona; // "HUMANA" o "JURIDICA"
    private String nombre;
    private String tipoDocumento;
    private String documento;
    private String calle;
    private String altura;
    private String localidad;
    private String codigoPostal;
    private String correoElectronico;
    private String telefono;
    private String tipoContacto; // "EMAIL", "WHATSAPP"
}