package Sdonaciones.dominio.donante;

public class MedioContacto {

    private TipoMedioContacto tipo;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    private String correoElectronico;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String telefono;
    private boolean esPredeterminado;

    public MedioContacto(String correo, String telefono, boolean esPredeterminado) {
        this.correoElectronico = correo;
        this.telefono = telefono;
        this.esPredeterminado = esPredeterminado;
    }

//
//    @Override
//    public String toString() {
//        return tipo + ": " + valor;
//    }
}
