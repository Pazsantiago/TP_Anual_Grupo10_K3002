package Servicio_donaciones.dominio.donacion;

import Servicio_donaciones.dominio.bien.Bien;
import Servicio_donaciones.dominio.donante.Donante;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Donacion {

    private static int contadorId = 1;

    private final int id;
    private final Donante donante;
    private final String administrador;
    private final String descripcion;
    private final LocalDateTime fechaRegistro;
    private List<Bien> bienes = new ArrayList<>();
    private List<DonacionSegmentada> donacionSegmentada = new ArrayList<>();

    public Donacion(Donante donante, String administrador, String descripcion,List<Bien> bienes) {
        if (donante == null)
            throw new IllegalArgumentException("El donante es obligatorio.");
        if (administrador == null || administrador.isBlank())
            throw new IllegalArgumentException("El administrador es obligatorio.");
        if (bienes == null || bienes.isEmpty())
            throw new IllegalArgumentException("Al menos debe donarse 1 bien");
        this.id = contadorId++;
        this.donante = donante;
        this.administrador = administrador;
        this.bienes = bienes;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
    }

    public void agregarBien(Bien bien) {
        bienes.add(bien);
    }
    private ArrayList<DonacionSegmentada> segmentarDonacion(ArrayList<Bien> bienes){
        return bienes.stream().map(b-> new DonacionSegmentada(b,new CambioEstado(EstadoDonacion.EN_DEPOSITO, LocalDateTime.now()))).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getId()                                         { return id; }
    public Donante getDonante()                                { return donante; }
    public String getAdministrador()                           { return administrador; }
    public String getDescripcion()                             { return descripcion; }
    public LocalDateTime getFechaRegistro()                    { return fechaRegistro; }
    public List<Bien>getBienes() { return bienes; }
}
