package Sdonaciones.repositorios;


import Sdonaciones.dominio.donante.Donante;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepoDonantes {

    //private final Map<String, Donante> donantes = new ConcurrentHashMap<>();
    private List<Donante> donantes = new ArrayList<>();


//    public void guardar(Donante donante) {
//        donantes.put(donante.obtenerContactoPredeterminado().getCorreoElectronico().toLowerCase(), donante);
//    }
//
//
//    public Optional<Donante> buscarPorCorreo(String correo) {
//        return Optional.ofNullable(donantes.get(correo.toLowerCase()));
//    }
//
//
//    public List<Donante> listarTodos() {
//        return List.copyOf(donantes.values());
//    }
//
//
//    public boolean existePorCorreo(String correo) {
//        return donantes.containsKey(correo.toLowerCase());
//    }
}
