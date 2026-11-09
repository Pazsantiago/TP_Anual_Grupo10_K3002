package org.example.donacionesfrontend.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.donacionesfrontend.dominio.bien.Bien;
import org.example.donacionesfrontend.dominio.bien.BienPerecedero;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"donacionInicial", "donacionesAsignadas"})
@NoArgsConstructor
public class DonacionSegmentada {
    private Long id;
    @JsonIgnore
    private Donacion donacionInicial;
    private Bien bien;
    private EstadoDonacion estadoActual;
    private List<EstadoDonacion> donacionEstadosHistorico = new ArrayList<>();
    private Subcategoria subcategoria;

    private List<BienAsignado> bienesAsignados = new ArrayList<>();

    @JsonIgnore
    private List<DonacionAsignada> donacionesAsignadas = new ArrayList<>();

    public Boolean estaVencida(Integer diasTolerablesDeVencimiento) {
        return (bien instanceof BienPerecedero) && ((BienPerecedero) this.bien).estaVencido(diasTolerablesDeVencimiento);
    }

    public void desvincularAsignadas() {
        if (!donacionesAsignadas.isEmpty()) this.donacionesAsignadas.forEach(a -> a.eliminarSegmentada(this.getId()));
    }

    public void eliminarAsignada(Long idAsignada) {
        this.donacionesAsignadas.removeIf(a -> a.getId().equals(idAsignada));
    }

    public void eliminarBienesAsignados(Long idNecesidad) {
        this.bienesAsignados.removeIf(bien -> bien.getIdNecesidadAsignada().equals(idNecesidad));
    }

    public void setDonacionEstadosHistorico(List<EstadoDonacion> donacionEstadosHistorico) {
        this.donacionEstadosHistorico = donacionEstadosHistorico;
        setearEstadosHistoricos();
    }

    public void setBienesAsignados(List<BienAsignado> bienesAsignados) {
        this.bienesAsignados = bienesAsignados;
        setearBienesAsignados();
    }

    public void agregarDonacionAsignada(DonacionAsignada donacionAsignada) {
        this.donacionesAsignadas.add(donacionAsignada);
    }

    public void setearRelaciones() {
        setearEstadosHistoricos();
        setearBienesAsignados();
    }

    public void setearEstadosHistoricos() {
        getDonacionEstadosHistorico().forEach(b -> {
            b.setDonacionSegmentada(this);
        });
    }

    public void setearBienesAsignados() {
        getBienesAsignados().forEach(b -> {
            b.setDonacionSegmentada(this);
        });
    }

    public void cambiarEstadoActual(EstadoDonacion nuevoEstadoActual) {
        if (this.estadoActual != null) {
            estadoActual.setEsHistorico(true);
            this.donacionEstadosHistorico.add(this.estadoActual);
        }
        this.estadoActual = nuevoEstadoActual;
    }

    public void agregarCantidadDeBienDonado(BienAsignado bien) {
        bienesAsignados.add(bien);
        bien.setDonacionSegmentada(this);
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
