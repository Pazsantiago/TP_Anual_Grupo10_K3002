package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donacionSegmentada"})
@Entity
@NoArgsConstructor
public class BienAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadEntregada;
    private Long idNecesidadAsignada;
    @ManyToOne()
    @JsonIgnore
    private DonacionSegmentada donacionSegmentada;

    public BienAsignado(Integer cantidadEntregada, Long idNecesidadAsignada, DonacionSegmentada donacionSegmentada) {
        this.cantidadEntregada = cantidadEntregada;
        this.idNecesidadAsignada = idNecesidadAsignada;
        this.donacionSegmentada = donacionSegmentada;
    }
}
