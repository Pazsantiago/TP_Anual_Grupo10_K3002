package utn.donatrack.donatrack_frontend.viewmodel;

import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.EntidadRequest;

/** Formulario de entidad beneficiaria (auto-registro y alta/edicion por administracion). */
@Getter
@Setter
public class EntidadForm {

    private String razonSocial;
    private String tipoEntidad;
    private String telefono;
    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private String representanteNombre;
    private String correoRepresentante;
    private String clave;
    private String claveRepetida;

    public String validar(boolean exigeClave) {
        if (Validaciones.vacio(razonSocial) || Validaciones.vacio(telefono)) {
            return "Completá la razón social y el teléfono de la entidad.";
        }
        if (Validaciones.vacio(calle) || Validaciones.vacio(numero) || Validaciones.vacio(ciudad) || Validaciones.vacio(provincia)) {
            return "Completá la dirección: calle, número, ciudad y provincia.";
        }
        if (Validaciones.vacio(representanteNombre) || !Validaciones.correoValido(correoRepresentante)) {
            return "Indicá nombre y un correo válido de la persona representante.";
        }
        return exigeClave ? Validaciones.clave(clave, claveRepetida) : null;
    }

    public EntidadRequest toRequest(boolean conClave) {
        return new EntidadRequest(Validaciones.limpio(razonSocial), Validaciones.limpio(tipoEntidad), Validaciones.limpio(telefono),
            Validaciones.limpio(calle), Validaciones.limpio(numero), Validaciones.limpio(ciudad), Validaciones.limpio(provincia),
            Validaciones.limpio(codigoPostal), Validaciones.limpio(representanteNombre), Validaciones.limpio(correoRepresentante),
            conClave ? clave : null);
    }
}
