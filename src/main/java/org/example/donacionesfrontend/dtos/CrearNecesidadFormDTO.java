package org.example.donacionesfrontend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearNecesidadFormDTO {

    private Long idEntidad;
    private String tipoNecesidad; // "EXTRAORDINARIA" o "RECURRENTE"
    private String descripcion;

    // Categorización
    private String subcategoriaNombre;
    private Long idCategoriaPadre;

    // Cantidad y medida
    private Integer cantidadObjetivo;
    private String unidadMedida; // "KILOGRAMOS", "UNIDADES", "LITROS", etc.

    // Parámetros exclusivos para necesidades RECURRENTES
    private Integer diasPeriodo;
    private String fechaInicioPeriodo; // Formato YYYY-MM-DD
}