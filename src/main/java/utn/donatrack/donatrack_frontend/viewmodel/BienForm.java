package utn.donatrack.donatrack_frontend.viewmodel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import utn.donatrack.donatrack_frontend.client.PeticionInvalidaException;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.BienRequest;

/** Un bien de la carga (fila repetible del formulario de donacion). */
@Getter
@Setter
public class BienForm {

    private String descripcion;
    private String categoria;
    private String subcategoria;
    private Integer cantidad;
    private String unidad;
    private String estado;
    private Boolean perecedero;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate vencimiento;
    private MultipartFile foto;

    private boolean esPerecedero() {
        return Boolean.TRUE.equals(perecedero);
    }

    /** El estado (nuevo/usado) es obligatorio en mobiliario y vestimenta. */
    private boolean requiereEstado() {
        String c = categoria == null ? "" : categoria.toLowerCase();
        return c.contains("mobiliario") || c.contains("vestimenta");
    }

    String validar(int numero) {
        String donde = "Bien " + numero + ": ";
        if (Validaciones.vacio(descripcion) || Validaciones.vacio(categoria) || Validaciones.vacio(subcategoria)) {
            return donde + "completá descripción, categoría y subcategoría.";
        }
        if (cantidad == null || cantidad < 1 || Validaciones.vacio(unidad)) {
            return donde + "indicá una cantidad de al menos 1 y su unidad.";
        }
        if (requiereEstado() && Validaciones.vacio(estado)) {
            return donde + "indicá si es nuevo o usado.";
        }
        if (esPerecedero() && vencimiento == null) {
            return donde + "ingresá la fecha de vencimiento.";
        }
        return null;
    }

    BienRequest toRequest() {
        String fotoBase64 = null;
        String fotoTipo = null;
        if (foto != null && !foto.isEmpty()) {
            try {
                fotoBase64 = Base64.getEncoder().encodeToString(foto.getBytes());
                fotoTipo = foto.getContentType();
            } catch (IOException e) {
                throw new PeticionInvalidaException("No pudimos leer la foto de \"" + descripcion + "\".", e);
            }
        }
        return new BienRequest(descripcion.strip(), categoria.strip(), subcategoria.strip(), cantidad, unidad,
            Validaciones.limpio(estado), esPerecedero(), esPerecedero() ? vencimiento : null, fotoBase64, fotoTipo);
    }
}
