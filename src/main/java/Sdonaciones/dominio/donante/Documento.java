package Sdonaciones.dominio.donante;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Documento {
    private String tipoDocumento;
    private String documento;

}