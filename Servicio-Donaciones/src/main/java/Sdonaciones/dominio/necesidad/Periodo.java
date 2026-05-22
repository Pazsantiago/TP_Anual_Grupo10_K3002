package Sdonaciones.dominio.necesidad;


public class Periodo {
    private final int value;

    // Constructor to assign the custom value
    private Periodo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}