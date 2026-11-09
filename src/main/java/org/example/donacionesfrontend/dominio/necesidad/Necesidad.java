package org.example.donacionesfrontend.dominio.necesidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.entidad.EntidadBeneficiaria;

import java.time.LocalDate;

@Setter
@Getter
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
@NoArgsConstructor
public abstract class Necesidad {
    private Long id;
    private String descripcion;
    private Subcategoria subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida = 0;
    @JsonIgnore
    private EntidadBeneficiaria entidadBeneficiaria;
    private Boolean estaSatisfechaConDonacion = false;
    private LocalDate satisfechaEn;

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
        this.setCantidadRecibida(necesidad.getCantidadRecibida());
        this.setDescripcion(necesidad.getDescripcion());
        this.setEntidadBeneficiaria(necesidad.getEntidadBeneficiaria());
        this.setSubcategoria(necesidad.getSubcategoria());
        this.setCantidadObjetivo(necesidad.getCantidadObjetivo());
        this.setCantidadRecibida(necesidad.getCantidadRecibida());
    }

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
