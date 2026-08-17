package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPerfiles {

    private final List<PerfilDonante> perfilDonantes = new ArrayList<>();

    public void guardar(PerfilDonante perfil) {
        if (perfil == null) {
            return;
        }
        perfilDonantes.removeIf(existing -> existing.getID() == perfil.getID());
        perfilDonantes.add(perfil);
    }

    public List<PerfilDonante> getDonaciones() {
        return perfilDonantes;
    }

    public PerfilDonante getxId(long donanteId) {
        return perfilDonantes.stream()
                .filter(p -> p.getID() == donanteId)
                .findFirst()
                .orElse(null);
    }
}
