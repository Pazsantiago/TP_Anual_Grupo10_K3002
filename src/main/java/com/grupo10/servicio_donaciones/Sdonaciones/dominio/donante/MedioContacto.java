package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = {"donante"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedioContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoMedioContacto tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_donante")
    private Donante donante;
}
