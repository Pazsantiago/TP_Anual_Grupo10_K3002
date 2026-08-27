package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.categoria.Subcategoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonacionSegmentada {
    private Integer id;
    @JsonIgnore
    private Donacion donacionInicial;
    private Bien bien;
    private EstadoDonacion estadoActual;
    private List<EstadoDonacion> donacionEstadosHistorico;
    private Subcategoria subcategoria;

    public void cambiarEstadoActual(EstadoDonacion nuevoEstadoActual) {
        donacionEstadosHistorico.add(estadoActual);
        estadoActual = nuevoEstadoActual;
    }

}
