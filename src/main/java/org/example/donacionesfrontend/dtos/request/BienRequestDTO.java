package org.example.donacionesfrontend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BienRequestDTO {
    private String descripcion;
    private String tipo;
    private SubcategoriaRequestDTO subcategoria;
    private URL foto;
    private String unidad;
    private Integer cantidadOriginal;
    private Integer cantidadActual;
    private String estado;
    private LocalDate fechaVencimiento;

}
