package com.grupo10.servicio_donaciones.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoRequestDTO {
    private String tipoDocumento;
    private String documento;
}