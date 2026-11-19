package com.grupo10.servicio_donaciones.dtos.frontend;

import java.util.List;

/**
 * Cuerpo de POST /api/donaciones: una unica carga; el servicio la segmenta por subcategoria y vencimiento.
 */
public record DonacionRequest(Long idDonante, String descripcionGeneral, List<BienRequest> bienes) {
}
