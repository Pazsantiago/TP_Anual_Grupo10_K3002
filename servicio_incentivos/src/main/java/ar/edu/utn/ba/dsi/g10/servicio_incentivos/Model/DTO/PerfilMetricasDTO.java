package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import lombok.Getter;

@Getter
public class PerfilMetricasDTO {
    private final long id;
    private final String categoriaActual;
    private final int cantidadInsignias;
    private final int misionesCompletadas;

    public PerfilMetricasDTO(PerfilDonante perfil) {
        this.id = perfil.getID();
        this.categoriaActual = perfil.getCategoria();
        this.cantidadInsignias = perfil.getInsignias() != null ? perfil.getInsignias().size() : 0;
        this.misionesCompletadas = perfil.getMisionesCompletadas();
    }
}
