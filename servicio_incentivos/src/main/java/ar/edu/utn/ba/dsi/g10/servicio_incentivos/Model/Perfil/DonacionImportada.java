package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DonacionImportada {
    private long donacionId; //evitamos procesar dos veces la misma donacion
    private long donanteId; // para localizarlo luego
    private int cantidadDonada;
    private String categoria; //entendiendo que procesamos una sola categoria en la donacion
    private boolean exitosa;
    private LocalDate fechaDonacion;

}
