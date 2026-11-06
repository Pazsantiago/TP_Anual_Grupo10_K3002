package com.Donatrack.Logistica.integracion;

import com.Donatrack.Logistica.dto.Mensajes.*;
import com.Donatrack.Logistica.dto.Mensajes.MensajeConfirmacionEntrega;
import com.Donatrack.Logistica.dto.Mensajes.MensajeEntregaFallida;
import com.Donatrack.Logistica.dto.Mensajes.MensajeInicioDeRuta;

public interface NotificadorBroker {
    void inicioDeRuta(MensajeInicioDeRuta m);
    void entregaConfirmada(MensajeConfirmacionEntrega m);
    void entregaFallida(MensajeEntregaFallida m);
}