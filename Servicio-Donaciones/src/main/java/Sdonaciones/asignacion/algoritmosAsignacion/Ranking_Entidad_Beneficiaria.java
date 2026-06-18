package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

public class Ranking_Entidad_Beneficiaria{
	private EntidadBeneficiaria entidadBeneficiaria;
	private int posicion;
	//private date dia;

	public Ranking_Entidad_Beneficiaria(EntidadBeneficiaria entidad, int pos) {
		this.entidadBeneficiaria = entidad;
        this.posicion = pos;
	}


    public EntidadBeneficiaria getEntidad() {
        return entidadBeneficiaria;
    }
}