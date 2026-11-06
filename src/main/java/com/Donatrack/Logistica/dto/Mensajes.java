package com.Donatrack.Logistica.dto;

import java.time.LocalDateTime;
import java.util.List;

/** Mensajes hacia el broker. Los nombres de campo deben coincidir con los de sus clases. */
public final class Mensajes {
    private Mensajes() {}

    public record MensajeInicioDeRuta(String idPlan, String patente, List<Long> idDonaciones,
                                      List<Long> idEntidades, LocalDateTime fechaInicio) {}

    public record MensajeConfirmacionEntrega(Long idAsignada, List<Long> idDonantesIncluidos,
                                             List<Long> idSegmentadasIncluidos, Long idEntidad,
                                             String patente, LocalDateTime fechaEntrega) {}

    public record MensajeEntregaFallida(Long idAsignada, List<Long> idDonantesIncluidos, Long idEntidad,
                                        String motivo, LocalDateTime fecha) {}
}