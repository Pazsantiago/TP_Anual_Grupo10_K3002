package ar.edu.utn.donatrack.repositorios;

import ar.edu.utn.donatrack.dominio.donante.Donante;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioDonantes  {

    private final Map<String, Donante> donante = new ConcurrentHashMap<>();


    public void guardar(Donante donante) {
        donantes.put(donantes.getCorreoElectronico().toLowerCase(), donante);
    }


    public Optional<Donante> buscarPorCorreo(String correo) {
        return Optional.ofNullable(donantes.get(correo.toLowerCase()));
    }


    public List<Donante> listarTodos() {
        return List.copyOf(donantes.values());
    }


    public boolean existePorCorreo(String correo) {
        return donantes.containsKey(correo.toLowerCase());
    }
}
