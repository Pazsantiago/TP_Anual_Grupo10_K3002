package com.grupo10.servicio_donaciones.dtos.request;

import com.grupo10.servicio_donaciones.donaciones.dominio.bien.Estado;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.UnidadMedida;
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
    private UnidadMedida unidad;
    private Integer cantidadOriginal;
    private Integer cantidadActual;
    private Estado estado;
    private LocalDate fechaVencimiento;

}
