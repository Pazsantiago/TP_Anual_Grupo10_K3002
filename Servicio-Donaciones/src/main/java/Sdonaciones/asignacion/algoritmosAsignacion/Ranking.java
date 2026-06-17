package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

public class Ranking{
	private EntidadBeneficiaria entidadBeneficiaria;
	private int posicion;
	//private date dia;

	public Ranking(EntidadBeneficiaria entidad, int pos) {
		this.entidadBeneficiaria = entidad;
        this.posicion = pos;
	}


    public EntidadBeneficiaria getEntidad() {
        return entidadBeneficiaria;
    }
}