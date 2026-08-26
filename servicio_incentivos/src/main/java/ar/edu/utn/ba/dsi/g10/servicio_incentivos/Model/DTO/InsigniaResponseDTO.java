package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import lombok.Getter;

@Getter
public class InsigniaResponseDTO {
    private final long id;
    private final String nombre;
    private final String descripcion;
    //private final String imagenUrl;

    public InsigniaResponseDTO(Insignia insignia) {
        this.id = insignia.getID();
        this.nombre = insignia.getNombre();
        this.descripcion = insignia.getDescripcion();
        //this.imagenUrl = insignia.getImagenURL();
    }
}
