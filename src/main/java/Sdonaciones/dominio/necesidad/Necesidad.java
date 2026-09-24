package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.time.LocalDate;

@Data
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
public abstract class Necesidad {
    private Long id;
    private String descripcion;
    private Subcategoria subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida;

    @JsonIgnore
    private EntidadBeneficiaria entidadBeneficiaria;

    public abstract void aplicarActualizacion(Necesidad necesidad);


    public abstract LocalDate getSatisfechaEn();

    public Necesidad(
            String descripcion,
            Subcategoria subcategoria,
            Integer cantidadObjetivo,
            EntidadBeneficiaria entidad
    ) {
        this.id = null;
        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadRecibida = 0;
        this.entidadBeneficiaria = entidad;
    }

    //
    public abstract boolean estaSatisfecha();

    public void recibirBienes(Integer cantidad) {
        this.cantidadRecibida += cantidad;
    }

    public void quitarBienes(Integer cantidad) {
        this.cantidadRecibida -= cantidad;
    }


}
