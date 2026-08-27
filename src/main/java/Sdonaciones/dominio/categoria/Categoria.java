package Sdonaciones.dominio.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
//
//    public static final Categoria MOBILIARIO = new Categoria(TipoCategoria.MOBILIARIO);
//    public static final Categoria ALIMENTICIO = new Categoria(TipoCategoria.ALIMENTICIO);
//    public static final Categoria PERECEDERO = new Categoria(TipoCategoria.PERECEDERO);
//    public static final Categoria VESTIMENTA = new Categoria(TipoCategoria.VESTIMENTA);

    private String nombre;
    @JsonIgnore
    private List<Subcategoria> subcategorias;
    private TipoCategoria tipo;

}

