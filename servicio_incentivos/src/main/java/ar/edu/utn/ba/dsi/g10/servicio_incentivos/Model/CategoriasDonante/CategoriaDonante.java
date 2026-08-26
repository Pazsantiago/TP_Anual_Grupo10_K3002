package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.List;


@Getter
@Setter
public class CategoriaDonante {
    // Se agrego una asignación por defecto al declarar el atributo
    private Categoria categoria = Categoria.COLABORADOR;
    private List<Mision> misiones = new ArrayList<>();

    // Constructor por defecto explícito
    public CategoriaDonante() {
        this.categoria = Categoria.COLABORADOR;
    }

    public void avanzarASiguienteCategoria() {
        if (this.categoria != null) {
            this.categoria = this.categoria.siguiente();
        }
    }

    public void agregarMision(Mision mision) {
        if (mision != null) {
            this.misiones.add(mision);
        }
    }

    // 2. Control de nulos al obtener el nombre
    public String getCategoria() {
        //return categoria.getNombre();
        return (this.categoria != null) ? this.categoria.getNombre() : Categoria.COLABORADOR.getNombre();
    }

    public void initCategoria(){
        this.categoria = Categoria.COLABORADOR;
    }

}
