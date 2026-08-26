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
    private CategoriaDonante categoria; // minúscula inicial
    private int orden;
    private Insignia insignia;           // minúscula inicial

    public abstract double calcularProgreso(List<DonacionImportada> historialProgreso);

    public String getObjetivo() {
        return descripcion;
    }
    public long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getCategoria() {
        return categoria.getCategoria();
    }
    public Insignia getInsignia() {
        return insignia;
    } 
}

