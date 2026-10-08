package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada;
    private List<Categoria> categoriasIncluidas;
    private Integer cantBienes;
    private Integer cantidadSegmentadasEntregadas;
}
