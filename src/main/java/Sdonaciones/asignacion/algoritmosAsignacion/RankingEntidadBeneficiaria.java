package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

public class RankingEntidadBeneficiaria {
	private EntidadBeneficiaria entidadBeneficiaria;
	private Integer posicion;
	private double puntaje;
	//private date dia;

	public RankingEntidadBeneficiaria(EntidadBeneficiaria entidad, Integer pos) {
		this.entidadBeneficiaria = entidad;
        this.posicion = pos;
	}


    public EntidadBeneficiaria getEntidad() {
        return entidadBeneficiaria;
    }
}