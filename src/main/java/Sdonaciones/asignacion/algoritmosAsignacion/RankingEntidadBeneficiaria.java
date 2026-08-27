package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RankingEntidadBeneficiaria {
    private EntidadBeneficiaria entidadBeneficiaria;
    private Integer posicion;
    private double puntaje;
    private TipoAlgoritmo algoritmoUsado;
    //private date dia;

    public RankingEntidadBeneficiaria(EntidadBeneficiaria entidad, Integer pos, TipoAlgoritmo algoritmoUsado) {
        this.entidadBeneficiaria = entidad;
        this.posicion = pos;
        this.algoritmoUsado = algoritmoUsado;
    }


    public EntidadBeneficiaria getEntidad() {
        return entidadBeneficiaria;
    }
}