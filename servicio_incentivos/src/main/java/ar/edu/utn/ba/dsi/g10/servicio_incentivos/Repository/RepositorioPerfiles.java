package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.RankingPerfil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
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

    
      public RepositorioPerfiles() {
        
        CategoriaDonante categoriaInicial = new CategoriaDonante(); 
        categoriaInicial.initCategoria(); 

        MisionCompletitud misionInicial = new MisionCompletitud();
        RankingPerfil rankingInicial = new RankingPerfil();

     
        PerfilDonante donanteDePrueba = new PerfilDonante(
            1L, 
            categoriaInicial, 
            misionInicial, 
            rankingInicial
        );

   
        this.perfilDonantes.add(donanteDePrueba);
    }

}
