package com.Donatrack.Logistica.DonaTrackTest.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DonacionServiceTest {

    @Mock
    private DonacionRepository donacionRepository;

    @InjectMocks
    private DonacionService donacionService; // tu servicio que usa el repositorio

    public DonacionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEstado() {
        // 🔹 Datos mock
        Donacion d1 = new Donacion(1, List.of(), new Direccion(), "Juan", EstadoEntrega.PENDIENTE);
        Donacion d2 = new Donacion(2, List.of(), new Direccion(), "Maria", EstadoEntrega.PENDIENTE);

        // 🔹 Simular comportamiento del repositorio
        when(donacionRepository.findByEstado("PENDIENTE"))
                .thenReturn(Arrays.asList(d1, d2));

        // 🔹 Llamar al servicio
        List<Donacion> resultado = donacionService.obtenerDonacionesPorEstado("PENDIENTE");

        // 🔹 Verificar resultados
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getDestinatario());
        verify(donacionRepository, times(1)).findByEstado("PENDIENTE");
    }
}
