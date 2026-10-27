package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien.Bien;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien.BienPerecedero;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"donacionInicial", "donacionesAsignadas"})
@Entity
@NoArgsConstructor
public class DonacionSegmentada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JsonIgnore
    private Donacion donacionInicial;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Bien bien;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private EstadoDonacion estadoActual;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "donacionSegmentada")
    @SQLRestriction("es_historico = true")
    private List<EstadoDonacion> donacionEstadosHistorico = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Subcategoria subcategoria;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            mappedBy = "donacionSegmentada")
    private List<BienAsignado> bienesAsignados = new ArrayList<>();

    @ManyToMany(mappedBy = "donacionesSegmentadas", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<DonacionAsignada> donacionesAsignadas = new ArrayList<>();

    public Boolean estaVencida(Integer diasTolerablesDeVencimiento) {
        return (bien instanceof BienPerecedero) && ((BienPerecedero) this.bien).estaVencido(diasTolerablesDeVencimiento);
    }

    public void desvincularAsignadas() {
        this.donacionesAsignadas.forEach(a -> a.eliminarSegmentada(this.getId()));
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
