package Servicio_donaciones.asignacion.algoritmosAsignacion;

import Servicio_donaciones.dominio.donacion.DonacionSegmentada;
import Servicio_donaciones.dominio.entidad.EntidadBeneficiaria;

import java.util.List;

public class AlgoritmoCompatibilidadSemantica implements IAlgoritmoAsignacion{
    @Override
    public List<Ranking_Entidad_Beneficiaria> rankEntidad(List<EntidadBeneficiaria> entidades, DonacionSegmentada donacion) {
        List<EntidadBeneficiaria> entidadesRankeadas = entidades.stream().filter(e->e.getNecesidades().stream().map(n->n.getSubcategoria().getCategoria()).anyMatch(c-> c==donacion.getSubcategoria().getCategoria())).limit(10).toList() ;
        List<Ranking_Entidad_Beneficiaria> rankings = entidadesRankeadas.stream().map(e-> new Ranking_Entidad_Beneficiaria(e,entidadesRankeadas.indexOf(e))).toList();
        return rankings;
    }

}