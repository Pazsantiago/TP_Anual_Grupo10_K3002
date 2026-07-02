package Sdonaciones.asignacion.algoritmosAsignacion;

import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public interface IAlgoritmoAsignacion{
    public List<RankingEntidadBeneficiaria> rankEntidad(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion);
}