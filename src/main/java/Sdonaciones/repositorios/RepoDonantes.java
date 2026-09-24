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
    private Long id = 0L;
    private List<Donante> donantes = new ArrayList<>();


    public void guardar(Donante donante) {
        donante.setId(++id);
        donantes.add(donante);
    }


    public Donante buscarPorId(Long id) {
        return donantes.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    public Donante buscarPorCorreo(String correoElectronico) {
        return donantes.stream()
                .filter(p -> p.getMediosDeContacto().stream().anyMatch(m -> m.getCorreoElectronico().equals(correoElectronico)))
                .findFirst()
                .orElse(null);
    }

    public Donante buscarPorDocumento(String tipoD, String doc) {
        return donantes.stream().filter(d -> d.getPersona().getDocumento().getTipoDocumento().equalsIgnoreCase(tipoD) &&
                d.getPersona().getDocumento().getDocumento().equalsIgnoreCase(doc)).findFirst().orElse(null);
    }


    public List<Donante> listarTodos() {
        return List.copyOf(donantes);
    }

    public void actualizarDonante(Donante donanteNuevo) {
        eliminarDonante(donanteNuevo.getId());
        guardar(donanteNuevo);
    }

    public void eliminarDonante(Long id) {
        donantes.remove(donantes.stream()
                .filter(p -> p.getId().equals(id)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Donante no encontrado")));
    }

//    public boolean existePorCorreo(String correo) {
//        return donantes.stream().anyMatch(d -> d.obtenerContactoPredeterminado().getCorreoElectronico().equalsIgnoreCase(correo.toLowerCase()));
//    }
}
