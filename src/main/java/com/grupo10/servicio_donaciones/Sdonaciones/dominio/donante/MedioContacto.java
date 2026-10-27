package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donante"})
@Entity
@NoArgsConstructor
public class MedioContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_donante")
    private Donante donante;
}
