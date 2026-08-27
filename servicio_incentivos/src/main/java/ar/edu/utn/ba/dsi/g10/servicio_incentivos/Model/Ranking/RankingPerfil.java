package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking;


import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;

import java.util.List;
import java.util.ArrayList;


//TODO: revision mensual y calculo
public class RankingPerfil {
    
    private List<PosicionRanking> posicionesHistoricas = new ArrayList<>();
    
    public List<PosicionRanking> getPosicionesHistoricas() {
        return posicionesHistoricas;
    }

    public void setPosicionesHistoricas(List<PosicionRanking> posicionesHistoricas) { //revisar si es necesario el seteo de posicionesHistoricas
        this.posicionesHistoricas = posicionesHistoricas;
    }
    
   



    // ranking: Al final de cada mes, aquellos usuarios con mas misiones cumplidas en ese periodo apareceran en un ranking, debe mostrarse el top 3 y persistir a lo largo del mes
    public void actualizarPerfil(PerfilDonante perfil){
        if (perfil == null) return;
        long IdPerfil = perfil.getID();
        PosicionRanking PosicionPerfil =  posicionesHistoricas.stream()
                                        .filter(p -> p.getId() == IdPerfil)
                                        .findFirst()
                                        .orElse(null);
        if (PosicionPerfil == null){
            PosicionRanking nuevaPosicion = new PosicionRanking(IdPerfil, 1);
            posicionesHistoricas.add(nuevaPosicion);
        } else {
            PosicionPerfil.setMisionesCompletadas(PosicionPerfil.getMisionesCompletadas() + 1);
        }
        this.actualizarRanking();
    }

    public void actualizarRanking(){
        posicionesHistoricas.sort((p1, p2) -> Integer.compare(p2.getMisionesCompletadas(), p1.getMisionesCompletadas()));
    }

    public void reiniciarRanking(){
        posicionesHistoricas.clear();
    }   
}

