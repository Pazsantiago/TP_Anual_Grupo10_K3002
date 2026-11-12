package utn.donatrack.donatrack_frontend.viewmodel;

import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.logistica.CamionRequest;

@Getter
@Setter
public class CamionForm {

    private String patente;
    private Double capacidadCarga;
    private Double capacidadVolumenM3;
    private Double alturaM;
    private Boolean disponible;

    public String validar() {
        if (Validaciones.vacio(patente)) {
            return "Ingresá la patente del camión.";
        }
        if (capacidadCarga == null || capacidadCarga <= 0 || capacidadVolumenM3 == null || capacidadVolumenM3 <= 0
            || alturaM == null || alturaM <= 0) {
            return "La capacidad de carga, el volumen y la altura deben ser mayores a cero.";
        }
        return null;
    }

    public CamionRequest toRequest() {
        return new CamionRequest(patente.strip().toUpperCase(), capacidadCarga, capacidadVolumenM3, alturaM,
            Boolean.TRUE.equals(disponible));
    }
}
