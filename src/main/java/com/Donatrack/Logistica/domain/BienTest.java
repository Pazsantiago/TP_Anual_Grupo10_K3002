/*package com.Donatrack.Logistica.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BienTest {
    @Test void creaBienValido() {
        Bien b = new Bien("Arroz", 10, "alimento");
        assertEquals("Arroz", b.getNombre());
        assertEquals(10, b.getCantidad());
    }
    @Test void rechazaNombreVacioONulo() {
        assertThrows(IllegalArgumentException.class, () -> new Bien("  ", 1, "x"));
        assertThrows(IllegalArgumentException.class, () -> new Bien(null, 1, "x"));
    }
    @Test void rechazaCantidadCeroONegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Bien("Arroz", 0, "x"));
        assertThrows(IllegalArgumentException.class, () -> new Bien("Arroz", -5, "x"));
    }
}*/