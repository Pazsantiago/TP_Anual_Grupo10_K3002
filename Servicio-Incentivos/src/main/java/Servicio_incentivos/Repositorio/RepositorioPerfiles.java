package Servicio_incentivos.Repositorio;

import Servicio_incentivos.dominio.PerfilDonante;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPerfiles {

    private final  List<PerfilDonante>  perfilDonantes = new ArrayList<>();

    public void guardar(PerfilDonante perfil) {
        perfilDonantes.add(perfil);
    }

    public List<PerfilDonante> getDonaciones() {
        return perfilDonantes;
    }
    public PerfilDonante getxId(long donanteId) {
        return perfilDonantes.stream().filter(p->p.getID() == donanteId ).findFirst().get();
    }
}
