package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;

public abstract class Necesidad {

    private String descripcion;
    private Subcategoria subcategoria;
    private int cantidadObjetivo;
    private int cantidadRecibida;

    public Necesidad(
            String descripcion,
            Subcategoria subcategoria,
            int cantidadObjetivo
    ) {
        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadRecibida = 0;
    }

    public abstract boolean estaSatisfecha();

    public void recibirBienes(int cantidad) {
        this.cantidadRecibida += cantidad;
    }

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public Subcategoria getSubcategoria() {return subcategoria;}
    public void setSubcategoria(Subcategoria subcategoria) {this.subcategoria = subcategoria;}
    public int getCantidadObjetivo() {return cantidadObjetivo;}
    public void setCantidadObjetivo(int cantidadObjetivo) {this.cantidadObjetivo = cantidadObjetivo;}
    public int getCantidadRecibida() {return cantidadRecibida;}
    public void setCantidadRecibida(int cantidadRecibida) {this.cantidadRecibida = cantidadRecibida;}
}
