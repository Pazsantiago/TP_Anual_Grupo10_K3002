package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
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
    private Integer id;
    private String descripcion;
    private Subcategoria subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida;
    
    @JsonIgnore
    private EntidadBeneficiaria entidadBeneficiaria;

    public abstract LocalDate getSatisfechaEn();

    //    public Necesidad(
//            String descripcion,
//            Subcategoria subcategoria,
//            Integer cantidadObjetivo
//    ) {
//        this.descripcion = descripcion;
//        this.subcategoria = subcategoria;
//        this.cantidadObjetivo = cantidadObjetivo;
//        this.cantidadRecibida = 0;
//    }
//
    public abstract boolean estaSatisfecha();

    public void recibirBienes(Integer cantidad) {
        this.cantidadRecibida += cantidad;
    }

}
