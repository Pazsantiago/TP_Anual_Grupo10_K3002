package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien.Bien;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"donante"})
@NoArgsConstructor
@Entity
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // private final String administrador;
    private String descripcionGeneral;
    @ManyToOne()
    @JsonIgnore
    private Donante donante;

    private Date fechaRegistro;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "donacionInicial")
    private List<DonacionSegmentada> donacionesSegmentadas;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "donacion")
    private List<Bien> bienesDeEntrada;

    public Donacion(String descripcionGeneral, Donante donante, List<DonacionSegmentada> donacionesSegmentadas, List<Bien> bienesDeEntrada) {
        this.descripcionGeneral = descripcionGeneral;
        this.donante = donante;
        this.fechaRegistro = new Date();
        setDonacionesSegmentadas(donacionesSegmentadas);
        setBienesDeEntrada(bienesDeEntrada);
    }

    public void eliminarSegmentadas() {
        this.donacionesSegmentadas.clear();
    }

    public List<Categoria> conocerCategorias() {
        return this.getBienesDeEntrada().stream().map(b -> b.getSubcategoria().getCategoria()).toList();
    }

    public Integer conocerCantidadDeBienesDonados() {
        Integer cantidad = 0;
        for (DonacionSegmentada donacionesSegmentada : donacionesSegmentadas) {
            cantidad += donacionesSegmentada.getBien().getCantidadOriginal();
        }
        return cantidad;
    }

    public Integer conocerCantSegmentadasEntregadas() {
        Integer cantidad = 0;
        for (DonacionSegmentada donacionesSegmentada : donacionesSegmentadas) {
            for (DonacionAsignada donacionAsignada : donacionesSegmentada.getDonacionesAsignadas()) {
                if (donacionAsignada.getAsignacionCompleta() != null) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public void setDonacionesSegmentadas(List<DonacionSegmentada> donacionesSegmentadas) {
        this.donacionesSegmentadas = donacionesSegmentadas;
        setearDonacionesSegmentadas();
    }

    public void setBienesDeEntrada(List<Bien> bienesDeEntrada) {
        this.bienesDeEntrada = bienesDeEntrada;
        setearBienesDeEntrada();
    }

    public void setearRelaciones() {
        this.segmentarse();
        setearDonacionesSegmentadas();
        setearBienesDeEntrada();
    }

    public void setearDonacionesSegmentadas() {
        getDonacionesSegmentadas().forEach(d -> {
            d.setDonacionInicial(this);
            d.setearRelaciones();
        });
    }

    public void setearBienesDeEntrada() {
        getBienesDeEntrada().forEach(b -> {
            b.setDonacion(this);
        });
    }

    public void segmentarse() {
        this.donacionesSegmentadas = new ArrayList<>();
        for (Integer i = 0; i < bienesDeEntrada.size(); i++) {
            DonacionSegmentada segmentada = new DonacionSegmentada();
            segmentada.setBien(bienesDeEntrada.get(i));
            segmentada.setEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_DEPOSITO, null, segmentada));
            segmentada.setDonacionEstadosHistorico(new ArrayList<>());
            segmentada.setSubcategoria(bienesDeEntrada.get(i).getSubcategoria());
            segmentada.setBienesAsignados(new ArrayList<>());
            segmentada.setDonacionInicial(this);
            donacionesSegmentadas.add(segmentada);
        }
    }

    public DonacionSegmentada buscarPorIdSegmentada(Long idSegmentada) {
        return this.donacionesSegmentadas.stream().filter(s -> s.getId().equals(idSegmentada)).findFirst().orElse(null);
    }

}