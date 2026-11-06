package com.Donatrack.Logistica.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Entrega;

@Service
public class DistribucionService {

    public record Resultado(Map<String, List<Entrega>> porCamion, List<Entrega> sinAsignar) {
        public int totalAsignadas() { return porCamion.values().stream().mapToInt(List::size).sum(); }
    }

    /** Propuesta de reparto (First Fit Decreasing). No modifica los camiones. */
    public Resultado distribuir(List<Camion> camiones, List<Entrega> entregas) {
        if (camiones == null || camiones.isEmpty())
            throw new IllegalStateException("No hay camiones disponibles");

        Map<String, double[]> libre = new LinkedHashMap<>();
        Map<String, Camion> porPatente = new LinkedHashMap<>();
        for (Camion c : camiones) {
            libre.put(c.getPatente(), new double[]{
                    c.getCapacidadCarga() - c.getCargaActual(),
                    c.getCapacidadVolumenM3() - c.getVolumenActual()});
            porPatente.put(c.getPatente(), c);
        }

        Map<String, List<Entrega>> asignadas = new LinkedHashMap<>();
        camiones.forEach(c -> asignadas.put(c.getPatente(), new ArrayList<>()));
        List<Entrega> sinAsignar = new ArrayList<>();

        List<Entrega> ordenadas = new ArrayList<>(entregas);
        ordenadas.sort(Comparator.comparingDouble(Entrega::pesoTotal).reversed());

        for (Entrega e : ordenadas) {
            if (e.getBultos().isEmpty()) { sinAsignar.add(e); continue; }
            double peso = e.pesoTotal(), vol = e.volumenTotal(), alto = e.alturaMaxima();
            boolean ubicada = false;
            for (Map.Entry<String, double[]> en : libre.entrySet()) {
                Camion c = porPatente.get(en.getKey());
                double[] l = en.getValue();
                if (peso <= l[0] && vol <= l[1] && alto <= c.getAlturaM()) {
                    l[0] -= peso;
                    l[1] -= vol;
                    asignadas.get(en.getKey()).add(e);
                    ubicada = true;
                    break;
                }
            }
            if (!ubicada) sinAsignar.add(e);
        }
        asignadas.values().removeIf(List::isEmpty);
        return new Resultado(asignadas, sinAsignar);
    }
}