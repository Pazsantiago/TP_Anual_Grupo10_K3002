package org.example.donacionesfrontend.dominio.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Setter
@Getter
@ToString(exclude = {"subcategorias"})
@NoArgsConstructor
public class Categoria {
    private Long id;
    private String nombre;
    @JsonIgnore
    private List<Subcategoria> subcategorias = new ArrayList<>();
    private TipoCategoria tipo;

    public Categoria(String nombre, TipoCategoria tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public void agregarSubCategoria(Subcategoria subcategoria) {
        this.subcategorias.add(subcategoria);
    }

}

