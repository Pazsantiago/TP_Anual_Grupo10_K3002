package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;
import java.util.Collections;

public interface IAlgoritmoAsignacion{
    public List<Ranking_Entidad_Beneficiaria> rankEntidad(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion);
}