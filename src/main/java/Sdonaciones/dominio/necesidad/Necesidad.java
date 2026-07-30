package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class Necesidad {
    private Integer id;
    private String descripcion;
    private Subcategoria subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida;
    private EntidadBeneficiaria entidadBeneficiaria;

    public abstract LocalDate getSatisfechaEn();

    //    public Necesidad(
//            String descripcion,
//            Subcategoria subcategoria,
//            Integer cantidadObjetivo
//    ) {
//        this.descripcion = descripcion;
//        this.subcategoria = subcategoria;
//        this.cantidadObjetivo = cantidadObjetivo;
//        this.cantidadRecibida = 0;
//    }
//
    public abstract boolean estaSatisfecha();

    public void recibirBienes(Integer cantidad) {
        this.cantidadRecibida += cantidad;
    }
//
//    public String getDescripcion() {
//        return descripcion;
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion = descripcion;
//    }
//
//    public Subcategoria getSubcategoria() {
//        return subcategoria;
//    }
//
//    public void setSubcategoria(Subcategoria subcategoria) {
//        this.subcategoria = subcategoria;
//    }
//
//    public Integer getCantidadObjetivo() {
//        return cantidadObjetivo;
//    }
//
//    public void setCantidadObjetivo(Integer cantidadObjetivo) {
//        this.cantidadObjetivo = cantidadObjetivo;
//    }
//
//    public Integer getCantidadRecibida() {
//        return cantidadRecibida;
//    }
//
//    public void setCantidadRecibida(Integer cantidadRecibida) {
//        this.cantidadRecibida = cantidadRecibida;
//    }
}
