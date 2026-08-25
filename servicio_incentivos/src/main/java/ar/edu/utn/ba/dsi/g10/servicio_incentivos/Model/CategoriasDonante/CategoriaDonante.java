package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CategoriaDonante {
    private Categoria categoria;
    private List<Mision> misiones = new ArrayList<>();

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


}
