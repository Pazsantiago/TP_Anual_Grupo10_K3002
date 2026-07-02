package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

public class RankingEntidadBeneficiaria {
	private EntidadBeneficiaria entidadBeneficiaria;
	private int posicion;
	private double puntaje;
	//private date dia;

	public RankingEntidadBeneficiaria(EntidadBeneficiaria entidad, int pos) {
		this.entidadBeneficiaria = entidad;
        this.posicion = pos;
	}


    public EntidadBeneficiaria getEntidad() {
        return entidadBeneficiaria;
    }
}