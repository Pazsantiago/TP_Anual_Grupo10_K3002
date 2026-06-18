package Servicio_incentivos.dominio;

import java.util.List;

public class RankingPerfil {
    public List<Integer> getPosicionesHistoricas() {
        return posicionesHistoricas;
    }

    public void setPosicionesHistoricas(List<Integer> posicionesHistoricas) {
        this.posicionesHistoricas = posicionesHistoricas;
    }

    private List<Integer> posicionesHistoricas;

}
