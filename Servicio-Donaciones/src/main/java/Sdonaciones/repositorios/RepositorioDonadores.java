package Sdonaciones.repositorios;

import Sdonaciones.dominio.donante.Donante;

import java.util.*;

public class RepositorioDonadores  {

    private final List<Donante> donadores = new ArrayList<>();


    public void guardar(Donante donante) {
        if (existePorCorreo(donante.getCorreoElectronico())) {
            throw new IllegalStateException(
                    "Ya existe un donador con el correo: " + donante.getCorreoElectronico());
        }
        donadores.add(donante);
    }


    public Optional<Donante> buscarPorCorreo(String correo) {
        return donadores.stream().filter(d -> Objects.equals(d.getCorreoElectronico(), correo)).findFirst();
    }


    public List<Donante> getDonadores() {
        return donadores;
    }

    public boolean existePorCorreo(String correoElectronico) {
        return donadores.stream().anyMatch(donante -> Objects.equals(donante.getCorreoElectronico(), correoElectronico));
    }
}
