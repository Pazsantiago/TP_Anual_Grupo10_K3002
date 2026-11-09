package org.example.donacionesfrontend.dominio.donante;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
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
@NoArgsConstructor
public class Persona {
    private Long id;
    private Documento documento;
    private Direccion direccion;

    public void desvincularRelaciones() {
    }
}
