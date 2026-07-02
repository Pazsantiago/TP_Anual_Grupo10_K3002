package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.donante.Donante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donacion {

    private static Integer contadorId = 1;

    private Integer id;
    private Donante donante;

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public List<Bien> getBienesDeEntrada() {
        return bienesDeEntrada;
    }

    public void setBienesDeEntrada(List<Bien> bienesDeEntrada) {
        this.bienesDeEntrada = bienesDeEntrada;
    }

    public static Integer getContadorId() {
        return contadorId;
    }

    public static void setContadorId(Integer contadorId) {
        Donacion.contadorId = contadorId;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    // private final String administrador;
    private String descripcionGeneral;
    private Date fechaRegistro;
    private List<Bien> bienesDeEntrada = new ArrayList<>();

//    public Donacion(Donante donante, String descripcion,List<Bien> bienes) {
////        if (donante == null)
////            throw new IllegalArgumentException("El donante es obligatorio.");
////        if (bienes == null || bienes.isEmpty())
////            throw new IllegalArgumentException("Al menos debe donarse 1 bien");
//        this.id = contadorId++;
//        this.donante = donante;
////        this.administrador = administrador;
////        this.bienes = bienes;
////        this.descripcion = descripcion;
//        this.fechaRegistro = new Date();
//    }

//    public void agregarBien(Bien bien) {
//        bienes.add(bien);
//    }

    public void segmentarse(){
        //todo
    }


    public Integer getId()                                         { return id; }
    public Donante getDonante()                                { return donante; }
//    public String getAdministrador()                           { return administrador; }
//    public String getDescripcion()                             { return descripcion; }
//    public LocalDateTime getFechaRegistro()                    { return fechaRegistro; }
//    public List<Bien>getBienes() { return bienes; }
}
