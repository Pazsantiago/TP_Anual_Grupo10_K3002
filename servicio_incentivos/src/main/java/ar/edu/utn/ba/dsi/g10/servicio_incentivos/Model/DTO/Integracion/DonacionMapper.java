package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class DonacionMapper {

    public DonacionImportada aDonacionImportada(DonacionDTO dto, Long donanteId) {
        if (dto == null) {
            return null;
        }

        DonacionImportada importada = new DonacionImportada();
        importada.setDonacionId(dto.getId() != null ? dto.getId() : 0L);
        importada.setDonanteId(donanteId != null ? donanteId : 0L);
        importada.setCantidadDonada(dto.getCantBienes() != null ? dto.getCantBienes() : 0);

        // Obtener categoría de la lista recibida
        if (dto.getCategoriasIncluidas() != null && !dto.getCategoriasIncluidas().isEmpty()) {
            importada.setCategoria(dto.getCategoriasIncluidas().get(0).toString());
        } else {
            importada.setCategoria("General");
        }

        // Determinar si la donación se considera entregada/exitosa
        boolean esExitosa = dto.getCantidadSegmentadasEntregadas() != null
                && dto.getCantidadSegmentadasEntregadas() > 0;
        importada.setExitosa(esExitosa);

        // Conversión de Date a LocalDate para la racha
        if (dto.getFechaEntrada() != null) {
            LocalDate fecha = dto.getFechaEntrada().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            importada.setFechaDonacion(fecha);
        } else {
            importada.setFechaDonacion(LocalDate.now());
        }

        return importada;
    }
}
