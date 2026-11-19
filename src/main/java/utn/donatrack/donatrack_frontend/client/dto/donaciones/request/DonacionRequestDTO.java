package  utn.donatrack.donatrack_frontend.client.dto.donaciones.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionRequestDTO {
    private String descripcionGeneral;
    private Date fechaRegistro;
    private List<BienRequestDTO> bienesDeEntrada = new ArrayList<>();
}