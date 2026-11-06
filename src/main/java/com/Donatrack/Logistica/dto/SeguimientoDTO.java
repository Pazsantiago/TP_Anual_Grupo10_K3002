package com.Donatrack.Logistica.dto;

import com.Donatrack.Logistica.domain.UbicacionCamion;

public record SeguimientoDTO(String idEntrega, Long idDonacion, String patente, UbicacionCamion ubicacion) {}