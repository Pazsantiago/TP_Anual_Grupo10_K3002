package org.example.donacionesfrontend.dominio.donante;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Documento {
    private Long id;
    private String tipoDocumento;
    private String documento;

}