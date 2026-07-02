package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.donante.Donante;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Donacion {

    private static int contadorId = 1;

    private final int id;
    private final Donante donante;
//    private final String administrador;
    private String descripcionGeneral;
    private final Date fechaRegistro;
    private List<Bien> bienesDeEntrada = new ArrayList<>();

    public Donacion(Donante donante, String administrador, String descripcion,List<Bien> bienes) {
        if (donante == null)
            throw new IllegalArgumentException("El donante es obligatorio.");
        if (administrador == null || administrador.isBlank())
            throw new IllegalArgumentException("El administrador es obligatorio.");
        if (bienes == null || bienes.isEmpty())
            throw new IllegalArgumentException("Al menos debe donarse 1 bien");
        this.id = contadorId++;
        this.donante = donante;
//        this.administrador = administrador;
//        this.bienes = bienes;
//        this.descripcion = descripcion;
        this.fechaRegistro = new Date();
    }

//    public void agregarBien(Bien bien) {
//        bienes.add(bien);
//    }

    public void segmentarse(){
        //todo
    }


    public int getId()                                         { return id; }
    public Donante getDonante()                                { return donante; }
//    public String getAdministrador()                           { return administrador; }
//    public String getDescripcion()                             { return descripcion; }
//    public LocalDateTime getFechaRegistro()                    { return fechaRegistro; }
//    public List<Bien>getBienes() { return bienes; }
}
