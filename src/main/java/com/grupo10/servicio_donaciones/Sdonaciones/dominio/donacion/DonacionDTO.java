package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada = new Date();
    private List<Categoria> categoriasIncluidas = new ArrayList<>();
    ;
    private Integer cantBienes;
    private Integer cantidadSegmentadasEntregadas;
}
