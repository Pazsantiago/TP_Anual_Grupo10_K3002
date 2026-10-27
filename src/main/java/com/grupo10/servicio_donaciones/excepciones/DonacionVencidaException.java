package com.grupo10.servicio_donaciones.excepciones;

public class DonacionVencidaException extends RuntimeException {
    public DonacionVencidaException(String message) {
        super(message);
    }
}
