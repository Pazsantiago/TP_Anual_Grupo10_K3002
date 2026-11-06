package com.Donatrack.Logistica.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.repository.CamionRepository;

@Service
public class CamionService {
    private final CamionRepository repo;

    public CamionService(CamionRepository repo) { this.repo = repo; }

    public Camion crear(String patente, double kg, double m3, double altura) {
        if (patente == null || patente.isBlank()) throw new IllegalArgumentException("La patente es obligatoria");
        if (kg <= 0 || m3 <= 0 || altura <= 0)
            throw new IllegalArgumentException("Las capacidades deben ser mayores a cero");
        return repo.save(new Camion(patente.trim().toUpperCase(), kg, m3, altura));
    }

    public List<Camion> listar() { return repo.findAll(); }

    public List<Camion> listarDisponibles() { return repo.findByDisponibleTrue(); }

    public Camion actualizarUbicacion(String patente, double lat, double lng) {
        Camion c = buscar(patente);
        c.actualizarUbicacion(lat, lng);
        return repo.save(c);
    }

    public Camion setDisponible(String patente, boolean disponible) {
        Camion c = buscar(patente);
        c.setDisponible(disponible);
        return repo.save(c);
    }

    private Camion buscar(String patente) {
        return repo.findByPatente(patente.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException("Camión no encontrado: " + patente));
    }
}