package com.Donatrack.Logistica;

import com.Donatrack.Logistica.integracion.NotificadorBroker;
import com.Donatrack.Logistica.integracion.ProveedorRutas;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MocksConfig {
    @Bean @Primary NotificadorBroker notificadorMock() { return Mockito.mock(NotificadorBroker.class); }
    @Bean @Primary ProveedorRutas proveedorMock() { return Mockito.mock(ProveedorRutas.class); }
}