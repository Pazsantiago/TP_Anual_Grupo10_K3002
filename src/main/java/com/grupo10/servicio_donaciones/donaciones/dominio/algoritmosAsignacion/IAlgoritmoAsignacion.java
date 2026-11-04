package com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion;

import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public interface IAlgoritmoAsignacion {
    public List<RankingEntidadBeneficiaria> rankear(DonacionSegmentada donacion, List<EntidadBeneficiaria> entidades);
}