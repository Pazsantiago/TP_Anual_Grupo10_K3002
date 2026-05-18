package ar.edu.utn.donatrack.repositorios;

import ar.edu.utn.donatrack.dominio.donador.Donador;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RepositorioDonadores  {

    private final Map<String, Donador> donadores = new ConcurrentHashMap<>();


    public void guardar(Donador donador) {
        donadores.put(donador.getCorreoElectronico().toLowerCase(), donador);
    }


    public Optional<Donador> buscarPorCorreo(String correo) {
        return Optional.ofNullable(donadores.get(correo.toLowerCase()));
    }


    public List<Donador> listarTodos() {
        return List.copyOf(donadores.values());
    }


    public boolean existePorCorreo(String correo) {
        return donadores.containsKey(correo.toLowerCase());
    }
}
