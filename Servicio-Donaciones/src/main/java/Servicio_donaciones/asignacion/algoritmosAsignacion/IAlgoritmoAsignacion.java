package Servicio_donaciones.asignacion.algoritmosAsignacion;

import Servicio_donaciones.dominio.donacion.DonacionSegmentada;
import Servicio_donaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public interface IAlgoritmoAsignacion{
    public List<Ranking_Entidad_Beneficiaria> rankEntidad(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion);
}