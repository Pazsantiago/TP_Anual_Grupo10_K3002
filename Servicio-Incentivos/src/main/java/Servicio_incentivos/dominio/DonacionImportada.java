package Servicio_incentivos.dominio;

import java.time.LocalDate;
import java.time.LocalTime;

public class DonacionImportada {
    private int cantidadDonada;
    private String categoria;
    private boolean exitosa;
    private LocalDate fechaDonacion; 


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public int getCantidadDonada() {
        return cantidadDonada;
    }

    public void setCantidadDonada(int cantidadDonada) {
        this.cantidadDonada = cantidadDonada;
    }


    public boolean isExitosa() {
        return exitosa;
    }

    public void setExitosa(boolean exitosa) {
        this.exitosa = exitosa;
    }
    public LocalDate getFechaDonacion() {
        return this.fechaDonacion;
    }
}