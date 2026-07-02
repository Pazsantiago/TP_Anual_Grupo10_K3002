package Sdonaciones.repositorios;

import Sdonaciones.dominio.donacion.Donacion;

import java.util.*;

public class RepositorioDonaciones {

    private final  List<Donacion>  donaciones = new ArrayList<>();

    public void guardar(Donacion donacion) {
        donaciones.add(donacion);
    }

    public Optional<Donacion> buscarPorId(int id) {
        return donaciones.stream().filter(donacion -> donacion.getId()==id).findFirst();
    }

    public List<Donacion> getDonaciones() {
        return donaciones;
    }
}
