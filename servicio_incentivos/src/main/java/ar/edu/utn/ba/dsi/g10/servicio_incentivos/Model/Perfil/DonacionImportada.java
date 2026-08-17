package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DonacionImportada {
    private int cantidadDonada;
    private String categoria;
    private boolean exitosa;
    private LocalDate fechaDonacion;

}
