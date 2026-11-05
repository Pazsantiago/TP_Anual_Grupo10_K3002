package com.grupo10.servicio_donaciones.dtos.frontend;

import lombok.Data;

@Data
public class DonanteDTO {
    private Long id;
    private String tipoPersona; // "HUMANA" o "JURIDICA"

    // Campos Persona Humana
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String documento;

    // Campos Persona Jurídica
    private String razonSocial;
    private String rubro;

    // Contacto
    private String correoElectronico;
    private String telefono;
}