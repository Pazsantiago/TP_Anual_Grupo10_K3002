package com.grupo10.servicio_donaciones.Sdonaciones.asignacion.algoritmosAsignacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public interface IAlgoritmoAsignacion {
    public List<RankingEntidadBeneficiaria> rankear(DonacionSegmentada donacion, List<EntidadBeneficiaria> entidades);
}