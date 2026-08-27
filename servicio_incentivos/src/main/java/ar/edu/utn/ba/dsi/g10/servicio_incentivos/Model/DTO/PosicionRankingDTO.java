package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;

import java.util.List;
import java.util.ArrayList;

public class PosicionRankingDTO {
    private long Id; 
    private Integer Puntaje;


    public PosicionRankingDTO(PosicionRanking posicion) {
        this.Id = posicion.getId();
        this.Puntaje = posicion.getMisionesCompletadas();
    }

   
    public Integer getPuntaje() {
        return this.Puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.Puntaje = puntaje;
    }

    public void setId(long id) {
        this.Id = id;
    }
    public long getId() {
        return this.Id;
    }
}
