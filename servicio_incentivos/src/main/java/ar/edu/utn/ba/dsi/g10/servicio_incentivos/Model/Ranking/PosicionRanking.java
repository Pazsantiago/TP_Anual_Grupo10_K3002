package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosicionRanking {
    private long Id;
    private Integer misionesCompletadas;

    public PosicionRanking(long Id, Integer misionesCompletadas) {
        this.Id = Id;
        this.misionesCompletadas = misionesCompletadas;
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public Integer getMisionesCompletadas() {
        return misionesCompletadas;
    }
    public void setMisionesCompletadas(Integer misionesCompletadas) {
        this.misionesCompletadas = misionesCompletadas;
    }
}