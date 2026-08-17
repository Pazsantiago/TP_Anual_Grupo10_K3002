package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter


public abstract class Mision {


    private long id;

    private String nombre;
    private String descripcion;
    private CategoriaDonante Categoria;
    private int orden;
    private Insignia Insignia;

    // public boolean estaCompletada (ProgresoMision progreso){ return progreso.getCompletada();  } // VER
    public abstract double calcularProgreso (List<DonacionImportada> historialProgreso);
    public String getObjetivo (){return descripcion;}


}

