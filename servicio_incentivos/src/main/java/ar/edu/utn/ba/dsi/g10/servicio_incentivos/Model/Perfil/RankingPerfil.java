package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

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

