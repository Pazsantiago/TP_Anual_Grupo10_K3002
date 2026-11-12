package utn.donatrack.donatrack_frontend.viewmodel;

import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.NecesidadRequest;

@Getter
@Setter
public class NecesidadForm {

    private String tipo = "RECURRENTE";
    private String subcategoria;
    private String descripcion;
    private Integer cantidadObjetivo;
    private String periodo;

    public boolean esRecurrente() {
        return !"EXTRAORDINARIA".equals(tipo);
    }

    public String validar() {
        if (Validaciones.vacio(subcategoria) || Validaciones.vacio(descripcion)) {
            return "Indicá la subcategoría y una descripción breve.";
        }
        if (cantidadObjetivo == null || cantidadObjetivo < 1) {
            return "La cantidad requerida debe ser al menos 1.";
        }
        if (esRecurrente() && Validaciones.vacio(periodo)) {
            return "Elegí el período de la necesidad recurrente.";
        }
        return null;
    }

    public NecesidadRequest toRequest() {
        return new NecesidadRequest(esRecurrente() ? "RECURRENTE" : "EXTRAORDINARIA", Validaciones.limpio(subcategoria),
            Validaciones.limpio(descripcion), cantidadObjetivo, esRecurrente() ? periodo : null);
    }
}
