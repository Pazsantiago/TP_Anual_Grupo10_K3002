package utn.donatrack.donatrack_frontend.viewmodel;

import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.DonanteRequest;

/** Formulario de donante (auto-registro y alta en deposito). Los campos del tipo que no corresponde llegan vacios. */
@Getter
@Setter
public class DonanteForm {

    private String tipoPersona = "HUMANA";
    // humana
    private String nombre;
    private String apellido;
    private Integer edad;
    private String genero;
    private String tipoDocumento;
    private String documento;
    private String direccion;
    // juridica
    private String razonSocial;
    private String tipoOrganizacion;
    private String rubro;
    private String cuit;
    private String representanteNombre;
    private String representanteCorreo;
    // contacto y acceso
    private String correoElectronico;
    private String telefono;
    private String whatsapp;
    private String medioPredeterminado;
    private String clave;
    private String claveRepetida;

    public boolean esJuridica() {
        return "JURIDICA".equals(tipoPersona);
    }

    /** @param exigeClave true en el auto-registro (la persona crea su contrasena); false en el alta en deposito */
    public String validar(boolean exigeClave) {
        if (esJuridica()) {
            if (Validaciones.vacio(razonSocial) || Validaciones.vacio(tipoOrganizacion) || Validaciones.vacio(rubro)
                || Validaciones.vacio(cuit)) {
                return "Completá razón social, tipo, rubro y CUIT de la organización.";
            }
            if (Validaciones.vacio(representanteNombre) || !Validaciones.correoValido(representanteCorreo)) {
                return "Indicá nombre y un correo válido de la persona representante.";
            }
        } else {
            if (Validaciones.vacio(nombre) || Validaciones.vacio(apellido) || Validaciones.vacio(documento)
                || Validaciones.vacio(tipoDocumento) || Validaciones.vacio(genero) || Validaciones.vacio(direccion)) {
                return "Completá nombre, apellido, documento, género y dirección.";
            }
            if (edad == null || edad < 0 || edad > 120) {
                return "Ingresá una edad válida.";
            }
        }
        if (!Validaciones.correoValido(correoElectronico)) {
            return "Ingresá un correo electrónico válido.";
        }
        if (Validaciones.vacio(telefono) && "SMS".equals(medioPredeterminado)) {
            return "Elegiste SMS como medio predeterminado pero no cargaste un teléfono.";
        }
        if (Validaciones.vacio(whatsapp) && "WHATSAPP".equals(medioPredeterminado)) {
            return "Elegiste WhatsApp como medio predeterminado pero no cargaste un número.";
        }
        return exigeClave ? Validaciones.clave(clave, claveRepetida) : null;
    }

    public DonanteRequest toRequest(boolean conClave) {
        String medio = Validaciones.vacio(medioPredeterminado) ? "EMAIL" : medioPredeterminado;
        String clavePropia = conClave ? clave : null;
        if (esJuridica()) {
            return new DonanteRequest("JURIDICA", null, null, null, "CUIT", Validaciones.limpio(cuit), null, null,
                Validaciones.limpio(razonSocial), tipoOrganizacion, Validaciones.limpio(rubro),
                Validaciones.limpio(representanteNombre), Validaciones.limpio(representanteCorreo),
                Validaciones.limpio(correoElectronico), Validaciones.limpio(telefono), Validaciones.limpio(whatsapp), medio, clavePropia);
        }
        return new DonanteRequest("HUMANA", Validaciones.limpio(nombre), Validaciones.limpio(apellido), edad, tipoDocumento,
            Validaciones.limpio(documento), genero, Validaciones.limpio(direccion), null, null, null, null, null,
            Validaciones.limpio(correoElectronico), Validaciones.limpio(telefono), Validaciones.limpio(whatsapp), medio, clavePropia);
    }
}
