package Sincentivos.dominio;
import java.util.List;

public class Mision {
   private long ID;
   private String nombre;
   private String descripcion;
   private CategoriaDonante Categoria;
   private int orden;
   private Insignia Insignia;

     public boolean estaCompletada (ProgresoMision progreso){ return progreso.getCompletada();  }
     public double calcularProgreso (eventos List<EventoDonacion>) {return 0;} //placeholder
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

    class MisionDonacionesExitosas extends Mision{
                private int donacionesExitosasRequeridas = 10;
            }
    class MisionHabilDonador extends Mision{
                private int CantidadBienesRequerida = 10;
            }
    class MisionCompletitud extends Mision{
                private int categoriasDistintasRequeridas = 10;
            }
    class MisionRacha extends Mision{
                private int mesesConsecutivosRequeridos = 10;
            }


}
