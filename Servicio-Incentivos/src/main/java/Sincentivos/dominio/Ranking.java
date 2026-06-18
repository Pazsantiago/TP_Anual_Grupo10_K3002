package Sincentivos.dominio;

import java.util.List;

public class Ranking {
    public List<Int> getPosicionesHistoricas() {
        return posicionesHistoricas;
    }

    public void setPosicionesHistoricas(List<Int> posicionesHistoricas) {
        this.posicionesHistoricas = posicionesHistoricas;
    }

    private List<Int> posicionesHistoricas;

}
