package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = {"donacionSegmentada"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BienAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadEntregada;
    private Long idNecesidadAsignada;
    @ManyToOne()
    @JsonIgnore
    private DonacionSegmentada donacionSegmentada;
}
