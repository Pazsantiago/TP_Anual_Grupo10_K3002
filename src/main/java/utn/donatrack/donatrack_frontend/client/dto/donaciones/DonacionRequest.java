package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import java.util.List;

/** Cuerpo de POST /api/donaciones: una unica carga; el servicio la segmenta por subcategoria y vencimiento. */
public record DonacionRequest(Long idDonante, String descripcionGeneral, List<BienRequest> bienes) {}
