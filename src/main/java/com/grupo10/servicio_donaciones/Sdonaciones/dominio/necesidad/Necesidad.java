package com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString(exclude = {"entidadBeneficiaria"})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = NecesidadExtraordinaria.class,
                name = "EXTRAORDINARIA"
        ),
        @JsonSubTypes.Type(
                value = NecesidadRecurrente.class,
                name = "RECURRENTE"
        )
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Necesidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Subcategoria subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida = 0;
    @ManyToOne()
    @JsonIgnore
    private EntidadBeneficiaria entidadBeneficiaria;
    private Boolean estaSatisfechaConDonacion;

    public Necesidad(String descripcion, Subcategoria subcategoria, Integer cantidadObjetivo, Integer cantidadRecibida, EntidadBeneficiaria entidadBeneficiaria, Boolean estaSatisfecha) {
        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadObjetivo = cantidadObjetivo;
        this.setCantidadRecibida(cantidadRecibida);
        this.entidadBeneficiaria = entidadBeneficiaria;
        this.estaSatisfechaConDonacion = estaSatisfecha;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida == null ? 0 : cantidadRecibida;
    }

    public void aplicarActualizacion(Necesidad necesidad) {
        this.setId(necesidad.getId());
        this.setCantidadRecibida(necesidad.getCantidadRecibida());
        this.setDescripcion(necesidad.getDescripcion());
        this.setEntidadBeneficiaria(necesidad.getEntidadBeneficiaria());
        this.setSubcategoria(necesidad.getSubcategoria());
        this.setCantidadObjetivo(necesidad.getCantidadObjetivo());
        this.setCantidadRecibida(necesidad.getCantidadRecibida());
    }


    public abstract LocalDate getSatisfechaEn();

    // Las subclases tienen su manera de conocer si esta estan satisfechas con su override
    // Pero si o si requieren que ademas la necesidad ya tenga la donacion realizada
    // Para eso esta el booleano de estaSatisfechaConDonacion
    // Debe cumplir sus caracteristicas Y que existe la donacion asignada y entregada.
    public abstract boolean estaSatisfecha();

    public void recibirBienes(Integer cantidad) {
        this.cantidadRecibida += cantidad;
    }

    public void quitarBienes(Integer cantidad) {
        this.cantidadRecibida -= cantidad;
    }


}
