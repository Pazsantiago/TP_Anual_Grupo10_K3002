package com.Donatrack.Logistica.dto;

import java.util.List;

/** Lo que el proveedor externo devuelve en el callback. */
public record ResultadoPlanificacionDTO(String idLote, List<RutaCamionDTO> rutas) {
    public record RutaCamionDTO(String patente, Double distanciaTotal, String tiempoEstimado,
                                List<DestinoDTO> destinos) {}
    public record DestinoDTO(Long idEntidad, String direccion, String razonSocial,
                             List<Long> idDonaciones, String distancia, String duracion) {}
}