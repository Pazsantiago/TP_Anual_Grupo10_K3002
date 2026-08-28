package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.donante.Donante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donacion {

    private Integer id;

    // private final String administrador;
    private String descripcionGeneral;
    private Donante donante;
    private Date fechaRegistro;
    private List<DonacionSegmentada> donacionesSegmentadas;
    private List<Bien> bienesDeEntrada;

//    public Donacion(Donante donante, String descripcion,List<Bien> bienes) {
////        if (donante == null)
////            throw new IllegalArgumentException("El donante es obligatorio.");
////        if (bienes == null || bienes.isEmpty())
////            throw new IllegalArgumentException("Al menos debe donarse 1 bien");
//        this.id = contadorId++;
//        this.donante = donante;

    /// /        this.administrador = administrador;
    /// /        this.bienes = bienes;
    /// /        this.descripcion = descripcion;
//        this.fechaRegistro = new Date();
//    }

//    public void agregarBien(Bien bien) {
//        bienes.add(bien);
//    }
    public Integer segmentarse(Integer idActual) {
        this.donacionesSegmentadas = new ArrayList<>();
        for (Integer i = 0; i < bienesDeEntrada.size(); i++) {
            donacionesSegmentadas.add(new DonacionSegmentada(++idActual, this, bienesDeEntrada.get(i), new EstadoDonacion(TipoEstadoDonacion.EN_DEPOSITO, null), new ArrayList<>(), bienesDeEntrada.get(i).getSubcategoria()));
        }
        return idActual;
    }


    public Integer getId() {
        return id;
    }

    public Donante getDonante() {
        return donante;
    }
//    public String getAdministrador()                           { return administrador; }
//    public String getDescripcion()                             { return descripcion; }
//    public LocalDateTime getFechaRegistro()                    { return fechaRegistro; }
//    public List<Bien>getBienes() { return bienes; }
}
