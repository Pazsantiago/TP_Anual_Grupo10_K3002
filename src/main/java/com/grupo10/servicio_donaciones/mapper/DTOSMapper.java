package com.grupo10.servicio_donaciones.mapper;

import com.grupo10.servicio_donaciones.donaciones.dominio.bien.Bien;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.PersonaHumana;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.PersonaJuridica;
import com.grupo10.servicio_donaciones.donaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.dtos.frontend.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DTOSMapper {

    public DonanteDTO aDonanteDTO(Donante donante) {
        if (donante == null) return null;

        DonanteDTO dto = new DonanteDTO();
        dto.setId(donante.getId());

        if (donante.getPersona() instanceof PersonaHumana ph) {
            dto.setTipoPersona("HUMANA");
            dto.setNombre(ph.getNombre());
            if (ph.getDocumento() != null) {
                dto.setTipoDocumento(ph.getDocumento().getTipoDocumento());
                dto.setDocumento(ph.getDocumento().getDocumento());
            }
        } else if (donante.getPersona() instanceof PersonaJuridica pj) {
            dto.setTipoPersona("JURIDICA");
            dto.setRazonSocial(pj.getRazonSocial());
            dto.setRubro(pj.getRubro());
        }

        if (donante.obtenerContactoPredeterminado() != null) {
            dto.setCorreoElectronico(donante.obtenerContactoPredeterminado().getCorreoElectronico());
            dto.setTelefono(donante.obtenerContactoPredeterminado().getTelefono());
        }

        return dto;
    }

    public DonacionDTO aDonacionDTO(Donacion donacion) {
        if (donacion == null) return null;

        DonacionDTO dto = new DonacionDTO();
        dto.setId(donacion.getId());
        dto.setDescripcionGeneral(donacion.getDescripcionGeneral());

        if (donacion.getDonante() != null) {
            dto.setIdDonante(donacion.getDonante().getId());
        }

        if (donacion.getBienesDeEntrada() != null) {
            dto.setBienesDeEntrada(
                    donacion.getBienesDeEntrada().stream()
                            .map(this::aBienDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }


    public BienDTO aBienDTO(Bien bien) {
        if (bien == null) return null;

        BienDTO dto = new BienDTO();
        dto.setDescripcion(bien.getDescripcion());
        dto.setCantidadOriginal(bien.getCantidadOriginal());
        if (bien.getUnidad() != null) {
            dto.setUnidad(bien.getUnidad().toString());
        }
        return dto;
    }

    public EntidadBeneficiariaDTO aEntidadDTO(EntidadBeneficiaria entidad) {
        if (entidad == null) return null;

        EntidadBeneficiariaDTO dto = new EntidadBeneficiariaDTO();
        dto.setId(entidad.getId());
        dto.setRazonSocial(entidad.getRazonSocial());
        dto.setDireccion(entidad.getDireccion());
        dto.setTelefono(entidad.getTelefono());
        dto.setCorreoRepresentante(entidad.getCorreoRepresentante());

        return dto;
    }

    public NecesidadDTO aNecesidadDTO(Necesidad necesidad) {
        if (necesidad == null) return null;

        NecesidadDTO dto = new NecesidadDTO();
        dto.setId(necesidad.getId());
        dto.setDescripcion(necesidad.getDescripcion());
        dto.setCantidadObjetivo(necesidad.getCantidadObjetivo());
        dto.setEstaSatisfechaConDonacion(necesidad.getEstaSatisfechaConDonacion());

        if (necesidad.getEntidadBeneficiaria() != null) {
            dto.setIdEntidadBeneficiaria(necesidad.getEntidadBeneficiaria().getId());
        }

        return dto;
    }


}