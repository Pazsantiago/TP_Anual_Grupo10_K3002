package Sdonaciones.dominio.donador;

public class MedioContacto {

    public enum Tipo {
        CORREO_ELECTRONICO,
        TELEFONO,
        WHATSAPP
    }

    private final Tipo tipo;
    private final String valor;

    public MedioContacto(Tipo tipo, String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El valor del medio de contacto no puede estar vacío.");
        }
        this.tipo = tipo;
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return tipo + ": " + valor;
    }
}
