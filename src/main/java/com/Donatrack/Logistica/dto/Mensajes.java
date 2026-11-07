package com.Donatrack.Logistica.dto;

import java.util.Date;
import java.util.List;

public final class Mensajes {
    private Mensajes() {}

    public record MensajeInicioDeRuta(Long idAsignada) {}

    public record MensajeEntregaFallida(Long idAsignada, String justificacion, Boolean sePuedeReplanificar) {}

    public record MensajeConfirmacionEntrega(Long idAsignada, Long idEntidad, List<Long> idSegmentadasIncluidos,
                                             Integer nroComprobante, Date fecha, Long idCamionAsignado) {}
}