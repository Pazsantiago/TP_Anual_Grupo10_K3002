package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import java.util.List;


public class Mapper {
    // parametros donante
    private long Id;
    private String categoriaActual;
    private int cantidadInsignias;
    private int misionesCompletadas;
    //private int totalDonacionesHistoricas;
    //private int totalOrganizacionesAyudadas;
    //private int rachaActual;
    //private int rankingHistorico;

    //parametros mision e insignia
    private String nombre; 
    private String descripcion;
    private String categoria;
    private String insignia;  


    public Mapper() {
        // Constructor vacío
    }
    //mapper de metricas
    public Mapper(PerfilDonante perfil) {
        this.Id = perfil.getID();
        this.categoriaActual = perfil.getCategoria();
        this.cantidadInsignias = perfil.getInsignias().size();
        this.misionesCompletadas = perfil.getMisionesCompletadas();
    }


    //mapper de misiones 
    public Mapper(Mision mision) {
        this.Id = mision.getId();
        this.nombre = mision.getNombre();
        this.descripcion = mision.getDescripcion();
        this.categoria = mision.getCategoria();
        this.insignia = mision.getInsignia().getNombre();
        
    }
    
    public Mapper transformarInsignia(Insignia insignia) {
        this.Id = insignia.getID();
        this.nombre = insignia.getNombre();
        this.descripcion = insignia.getDescripcion();
        //por ahora no vemos el url 
        return this;
    }



    public int getId() {
        return (int) Id;
    }
    public String getCategoriaActual() {
        return categoriaActual;
    }
    public int getCantidadInsignias() {
        return cantidadInsignias;
    }
    public int getMisionesCompletadas() {
        return misionesCompletadas;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getInsignia() {
        return insignia;
    }
}


