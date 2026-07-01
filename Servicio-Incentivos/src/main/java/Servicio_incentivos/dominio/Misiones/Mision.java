package Servicio_incentivos.dominio.Misiones;
import Servicio_incentivos.dominio.CategoriasDonante.CategoriaDonante;
import Servicio_incentivos.dominio.DonacionImportada;

import java.util.List;

public abstract class Mision {
   private long ID;
   private String nombre;
   private String descripcion;
   private CategoriaDonante Categoria;
   private int orden;
   private Insignia Insignia;

    // public boolean estaCompletada (ProgresoMision progreso){ return progreso.getCompletada();  } // VER
     public abstract double calcularProgreso (List<DonacionImportada> historialProgreso);
     public String getObjetivo (){return descripcion;}

    public Insignia getInsignia() { return this.Insignia;}
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

