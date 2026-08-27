package Sdonaciones.repositorios;


import Sdonaciones.dominio.donante.Donante;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class RepoDonantes {

    //private final Map<String, Donante> donantes = new ConcurrentHashMap<>();
    private List<Donante> donantes = new ArrayList<>();


    public void guardar(Donante donante) {
        donantes.add(donante);
    }


    public Donante buscarPorCorreo(String correoElectronico) {
        return donantes.stream()
                .filter(p -> p.getMediosDeContacto().stream().anyMatch(m -> m.getCorreoElectronico().equals(correoElectronico)))
                .findFirst()
                .orElse(null);
    }


    public List<Donante> listarTodos() {
        return List.copyOf(donantes);
    }

    public void actualizarDonante(String tipoD, String doc, Donante donanteNuevo) {
        Donante oldDonante = donantes.stream()
                .filter(p ->
                        p.getPersona().getDocumento().getTipoDocumento().equals(tipoD)
                                && p.getPersona().getDocumento().getDocumento().equals(doc)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Donante no encontrado"));

        int index = donantes.indexOf(oldDonante);
        donantes.set(index, donanteNuevo);
    }

    public String eliminarDonante(String tipoD, String doc) {
        donantes.remove(donantes.stream()
                .filter(p -> p.getPersona().getDocumento().getTipoDocumento().equals(tipoD) &&
                        p.getPersona().getDocumento().getDocumento().equals(doc)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Donante no encontrado")));
        return "Eliminado";
    }
    
//    public boolean existePorCorreo(String correo) {
//        return donantes.stream().anyMatch(d -> d.obtenerContactoPredeterminado().getCorreoElectronico().equalsIgnoreCase(correo.toLowerCase()));
//    }
}
