package com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "categoria")
    @JsonIgnore
    private List<Subcategoria> subcategorias = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private TipoCategoria tipo;

    public Categoria(String nombre, TipoCategoria tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public void agregarSubCategoria(Subcategoria subcategoria) {
        this.subcategorias.add(subcategoria);
    }
    
}

