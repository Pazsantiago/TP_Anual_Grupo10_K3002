package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;

import java.util.List;

@Getter
public class MisionResponseDTO {
    private final long id;
    private final String nombre;
    private final String descripcion;
    private final String categoria;
    private final String insigniaNombre;
    private final double progreso;

    public MisionResponseDTO(Mision mision, List<DonacionImportada> historial) {
        this.id = mision.getId();
        this.nombre = mision.getNombre();
        this.descripcion = mision.getDescripcion();
        this.categoria = mision.getCategoria();
        this.insigniaNombre = mision.getInsignia() != null ? mision.getInsignia().getNombre() : null;
        this.progreso = mision.calcularProgreso(historial); // Calcula el progreso real
    }
}