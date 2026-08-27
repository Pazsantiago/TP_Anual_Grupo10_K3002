package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MisionCompletitud extends Mision {
    private double categoriasDistintasRequeridas;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        // Aquí guardamos únicamente las categorías que son distintas
        Set<String> categoriasVistas = new HashSet<>();

        for (DonacionImportada donacionInstancia :historialMision) {
            // Condición de parada rápida: si ya encontramos 10 distintas, frenamos el bucle
            if (categoriasVistas.size() >= categoriasDistintasRequeridas) {
                break;
            }
            String categoriaActual = donacionInstancia.getCategoria();
            if (categoriaActual != null) {
                categoriasVistas.add(categoriaActual);
            }
        }

        if (categoriasDistintasRequeridas == 0) {
            return 0;
        }

        return (((double)categoriasVistas.size() / categoriasDistintasRequeridas)*100);
    }
}
