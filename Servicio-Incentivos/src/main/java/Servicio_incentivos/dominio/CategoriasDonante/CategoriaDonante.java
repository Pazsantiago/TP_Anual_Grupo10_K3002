package Servicio_incentivos.dominio.CategoriasDonante;

import Servicio_incentivos.dominio.Misiones.Mision;
import java.util.List;

public class CategoriaDonante {
    private Categoria categoria;
    private List<Mision> Misiones;

    public void avanzarASiguienteCategoria() {
        if (this.categoria != null) {
            this.categoria = this.categoria.siguiente();
        }
    }

    public void agregarMision(Mision mision) {
        if (mision != null) {
            this.Misiones.add(mision);
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Mision> getMisiones() {
        return Misiones;
    }

    public void setMisiones(List<Mision> Misiones) {
        this.Misiones = Misiones;
    }
}

