package utn.donatrack.donatrack_frontend.viewmodel;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonacionRequest;

/** Formulario de carga de donacion: datos generales y una lista de bienes (bienes[0].descripcion, bienes[1].descripcion, ...). */
@Getter
@Setter
public class DonacionForm {

    private Long idDonante;
    private String descripcionGeneral;
    private List<BienForm> bienes = new ArrayList<>();

    public String validar() {
        if (idDonante == null) {
            return "Elegí la persona donante.";
        }
        if (Validaciones.vacio(descripcionGeneral)) {
            return "Escribí una descripción general de la donación.";
        }
        if (bienes.isEmpty()) {
            return "Cargá al menos un bien.";
        }
        for (int i = 0; i < bienes.size(); i++) {
            String error = bienes.get(i).validar(i + 1);
            if (error != null) {
                return error;
            }
        }
        return null;
    }

    public DonacionRequest toRequest() {
        return new DonacionRequest(idDonante, descripcionGeneral.strip(), bienes.stream().map(BienForm::toRequest).toList());
    }
}
