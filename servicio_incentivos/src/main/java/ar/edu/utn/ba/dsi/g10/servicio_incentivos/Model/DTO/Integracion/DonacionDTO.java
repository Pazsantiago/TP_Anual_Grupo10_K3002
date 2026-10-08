package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada;
    private List<Object> categoriasIncluidas; // o List<String> / DTO si solo precisan el nombre
    private Integer cantBienes;
    private Integer cantidadSegmentadasEntregadas;
}
