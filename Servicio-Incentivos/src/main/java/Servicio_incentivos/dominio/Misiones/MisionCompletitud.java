package Servicio_incentivos.dominio.Misiones;

import Servicio_incentivos.dominio.DonacionImportada;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MisionCompletitud extends Mision {
    private double categoriasDistintasRequeridas = 10.0;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        // Aquí guardamos únicamente las categorías que son distintas
        Set<String> categoriasVistas = new HashSet<>();

        for (DonacionImportada donacionInstancia :historialMision) {
            // Condición de parada rápida: si ya encontramos 10 distintas, frenamos el bucle
            if (categoriasVistas.size() >= 10) {
                break;
            }
            String categoriaActual = donacionInstancia.getCategoria();
            if (categoriaActual != null) {
                categoriasVistas.add(categoriaActual);
            }
        }
        return (((double)categoriasVistas.size() / categoriasDistintasRequeridas)*100);
    }
}
