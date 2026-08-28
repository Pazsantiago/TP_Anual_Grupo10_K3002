package Sdonaciones.dominio.donante;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = PersonaHumana.class,
                name = "HUMANA"
        ),
        @JsonSubTypes.Type(
                value = PersonaJuridica.class,
                name = "JURIDICA"
        )
})
public class Persona {
    private Documento documento;
    private Direccion direccion;
}
