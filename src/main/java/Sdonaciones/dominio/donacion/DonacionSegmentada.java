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
    private Long id;
    @JsonIgnore
    private Donacion donacionInicial;
    private Bien bien;
    private EstadoDonacion estadoActual;
    private List<EstadoDonacion> donacionEstadosHistorico;
    private Subcategoria subcategoria;
    private List<BienAsignado> bienesAsignados;

    public void cambiarEstadoActual(EstadoDonacion nuevoEstadoActual) {
        donacionEstadosHistorico.add(estadoActual);
        estadoActual = nuevoEstadoActual;
    }

    public void agregarCantidadDeBienDonado(BienAsignado bien) {
        bienesAsignados.add(bien);
    }

    public Integer getCantidadBienAsignadoPorId(Long idNecesidad) {
        return bienesAsignados.stream().filter(b -> b.getIdNecesidadAsignada().equals(idNecesidad)).findFirst().orElse(null).getCantidadEntregada();
    }

    public void restarBienesAsignados(Integer cantidad) {
        bien.restarCantidad(cantidad);
    }

    public void sumarBienesAsignados(Integer cantidad) {
        bien.sumarCantidad(cantidad);
    }
}
