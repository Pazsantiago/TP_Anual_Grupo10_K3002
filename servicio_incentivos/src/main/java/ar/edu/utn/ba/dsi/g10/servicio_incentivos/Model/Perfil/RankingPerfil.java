package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import java.util.List;


//TODO: revision mensual y calculo
public class RankingPerfil {
    //List<perfil>??
    public List<Integer> getPosicionesHistoricas() { //getTop3??
        return posicionesHistoricas;
    }

    public void setPosicionesHistoricas(List<Integer> posicionesHistoricas) {
        this.posicionesHistoricas = posicionesHistoricas;
    }

    private List<PosicionRanking> posicionesHistoricas;

    // ranking: Al final de cada mes, aquellos usuarios con mas misiones cumplidas en ese periodo apareceran en un ranking, debe mostrarse el top 3 y persistir a lo largo del mes
    public void actualizarPerfil(PerfilDonante perfil){
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
}

