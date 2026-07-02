package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.categoria.Subcategoria;

import java.util.List;

public class DonacionSegmentada {
    private Donacion donacionInicial;

    public Donacion getDonacionInicial() {
        return donacionInicial;
    }

    public void setDonacionInicial(Donacion donacionInicial) {
        this.donacionInicial = donacionInicial;
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public List<EstadoDonacion> getDonacionEstadoHistorico() {
        return donacionEstadoHistorico;
    }

    public void setDonacionEstadoHistorico(List<EstadoDonacion> donacionEstadoHistorico) {
        this.donacionEstadoHistorico = donacionEstadoHistorico;
    }

    private Subcategoria subcategoria;

    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }

    private List<Bien> bienes;
    private List<EstadoDonacion> donacionEstadoHistorico;

    public EstadoDonacion obtenerEstadoActual(){
        return this.donacionEstadoHistorico.getLast();
    }
}
