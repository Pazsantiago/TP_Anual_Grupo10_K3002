package org.example.donacionesfrontend.dtos;

@Data
public class CrearDonacionFormDTO {
    private Long idDonante;
    private String descripcionGeneral;
    private List<BienInputDTO> bienesDeEntrada;
}

@Data
public class BienInputDTO {
    private String descripcion;
    private String subcategoriaNombre;
    private Long idCategoria;
    private String unidad; // "KILOGRAMOS", "UNIDADES", etc.
    private Integer cantidadOriginal;
    private String tipo; // "CON_ESTADO", "PERECEDERO"
    private String estado; // "NUEVO", "USADO"
    private String fechaVencimiento;
}