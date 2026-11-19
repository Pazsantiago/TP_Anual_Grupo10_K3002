package com.grupo10.servicio_donaciones.mapper;

import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.RankingEntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.SegmentadaRankings;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.Bien;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.BienConEstado;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.BienPerecedero;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.Estado;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.*;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.*;
import com.grupo10.servicio_donaciones.donaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.NecesidadExtraordinaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.NecesidadRecurrente;
import com.grupo10.servicio_donaciones.dtos.frontend.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DTOSMapper {

    public DonanteResponse deDominioAResponse(Donante donante) {
        if (donante == null) return null;

        String tipoPersona = donante.getPersona() instanceof PersonaHumana ? "HUMANA" : "JURIDICA";
        String nombre = null;
        String apellido = null;
        String tipoDocumento = null;
        String documento = null;
        String razonSocial = null;
        String rubro = null;

        if (donante.getPersona() instanceof PersonaHumana ph) {
            nombre = ph.getNombre();
            apellido = null;
            tipoDocumento = ph.getDocumento().getTipoDocumento() != null ? ph.getDocumento().getTipoDocumento() : null;
            documento = ph.getDocumento().getDocumento() != null ? ph.getDocumento().getDocumento() : null;
        } else if (donante.getPersona() instanceof PersonaJuridica pj) {
            razonSocial = pj.getRazonSocial();
            rubro = pj.getRubro();
            tipoDocumento = "CUIT";
            documento = pj.getDocumento().getDocumento() != null ? pj.getDocumento().getDocumento() : null;
        }

        String correo = donante.obtenerContactoPredeterminado() != null ? donante.obtenerContactoPredeterminado().getCorreoElectronico() : null;
        String telefono = donante.obtenerContactoPredeterminado() != null ? donante.obtenerContactoPredeterminado().getTelefono() : null;

        return new DonanteResponse(
                donante.getId(),
                tipoPersona,
                nombre,
                apellido,
                tipoDocumento,
                documento,
                razonSocial,
                rubro,
                correo,
                telefono
        );
    }

    public EntidadResponse deDominioAResponse(EntidadBeneficiaria entidad) {
        if (entidad == null) return null;
        return new EntidadResponse(
                entidad.getId(),
                entidad.getRazonSocial(),
                entidad.getDireccion(),
                entidad.getTelefono(),
                entidad.getCorreoRepresentante()
        );
    }

    public NecesidadResponse deDominioAResponse(Necesidad necesidad) {
        if (necesidad == null) return null;
        Long idEntidad = necesidad.getEntidadBeneficiaria() != null ? necesidad.getEntidadBeneficiaria().getId() : null;
        return new NecesidadResponse(
                necesidad.getId(),
                idEntidad,
                necesidad.getDescripcion(),
                necesidad.getCantidadObjetivo(),
                necesidad.getEstaSatisfechaConDonacion()
        );
    }

    public BienResponse deDominioAResponse(Bien bien) {
        if (bien == null) return null;
        String unidad = bien.getUnidad() != null ? bien.getUnidad().toString() : null;
        return new BienResponse(
                bien.getDescripcion(),
                bien.getCantidadOriginal(),
                unidad
        );
    }

    public DonacionAsignadaResponse deDominioAResponse(DonacionAsignada asignada) {
        if (asignada == null) return null;

        return new DonacionAsignadaResponse(
                asignada.getId(),
                asignada.getDonacionesSegmentadas().stream().map(DonacionSegmentada::getId).toList(),
                asignada.getNecesidadResuelta().getId()
        );
    }

    public DonacionSegmentadaResponse deDominioAResponse(DonacionSegmentada segmentada) {
        if (segmentada == null) return null;

        return new DonacionSegmentadaResponse(
                segmentada.getId(),
                segmentada.getDonacionInicial().getId(),
                segmentada.getEstadoActual().toString(),
                segmentada.getSubcategoria().getNombre()
        );
    }

    public DonacionResponse deDominioAResponse(Donacion donacion) {
        if (donacion == null) return null;
        Long idDonante = donacion.getDonante() != null ? donacion.getDonante().getId() : null;
        var bienes = donacion.getBienesDeEntrada() != null ?
                donacion.getBienesDeEntrada().stream().map(this::deDominioAResponse).collect(Collectors.toList()) :
                java.util.List.<BienResponse>of();

        return new DonacionResponse(
                donacion.getId(),
                idDonante,
                donacion.getDescripcionGeneral(),
                bienes,
                null
        );
    }

    public RankingEntidadResponse deDominioAResponse(RankingEntidadBeneficiaria ranking) {
        if (ranking == null) return null;
        Long idEntidad = ranking.getEntidadBeneficiaria() != null ? ranking.getEntidadBeneficiaria().getId() : null;
        String algoritmo = ranking.getAlgoritmoUsado() != null ? ranking.getAlgoritmoUsado().toString() : null;
        return new RankingEntidadResponse(
                idEntidad,
                ranking.getPosicion(),
                ranking.getPuntaje(),
                algoritmo
        );
    }

    public RankingSegmentadaResponse deDominioAResponse(SegmentadaRankings segmentadaRankings) {
        if (segmentadaRankings == null) return null;
        Long idSegmentada = segmentadaRankings.getSegmentada() != null ? segmentadaRankings.getSegmentada().getId() : null;
        var rankings = segmentadaRankings.getRankings() != null ?
                segmentadaRankings.getRankings().stream().map(this::deDominioAResponse).collect(Collectors.toList()) :
                java.util.List.<RankingEntidadResponse>of();
        return new RankingSegmentadaResponse(idSegmentada, rankings);
    }

    public Donante deDTORequestADominio(DonanteRequest dto) {
        if (dto == null) return null;
        Donante donante = new Donante();

        if ("JURIDICA".equalsIgnoreCase(dto.tipoPersona()) || dto.razonSocial() != null) {
            PersonaJuridica pj = new PersonaJuridica();
            pj.setRazonSocial(dto.razonSocial());
            pj.setTipoJuridica(TipoPersonaJuridica.valueOf(dto.tipoOrganizacion()));
            pj.setRubro(dto.rubro());
            pj.setDocumento(new Documento());
            pj.getDocumento().setTipoDocumento(dto.tipoDocumento());
            pj.getDocumento().setDocumento(dto.documento());
            donante.setPersona(pj);
        } else {
            PersonaHumana ph = new PersonaHumana();
            ph.setNombre(dto.nombre());
            if (dto.edad() != null) ph.setEdad(dto.edad());
            ph.getDocumento().setTipoDocumento(dto.tipoDocumento());
            ph.getDocumento().setDocumento(dto.documento());
            donante.setPersona(ph);
        }

        if (dto.correoElectronico() != null || dto.telefono() != null || dto.whatsapp() != null || dto.direccion() != null) {
            MedioContacto mc = new MedioContacto();
            mc.setCorreoElectronico(dto.correoElectronico());
            mc.setTipo(MedioNotificacion.valueOf(dto.medioPredeterminado()));
            mc.setTelefono(dto.telefono());
            mc.setTelefono(dto.whatsapp());
            donante.cambiarContactoPredeterminado(mc);
        }

        return donante;
    }

    public EntidadBeneficiaria deDTORequestADominio(EntidadRequest dto) {
        if (dto == null) return null;
        EntidadBeneficiaria entidad = new EntidadBeneficiaria();
        entidad.setRazonSocial(dto.razonSocial());
        entidad.setTelefono(dto.telefono());
        entidad.setCorreoRepresentante(dto.correoRepresentante());

        String direccionCompleta = Stream.of(dto.calle(), dto.numero(), dto.ciudad(), dto.provincia(), dto.codigoPostal())
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));
        entidad.setDireccion(direccionCompleta);

        return entidad;
    }

    public Necesidad deDTORequestADominio(NecesidadRequest dto) {
        if (dto == null) return null;
        Necesidad necesidad;
        if ("RECURRENTE".equalsIgnoreCase(dto.tipo()) || dto.periodo() != null) {
            NecesidadRecurrente nr = new NecesidadRecurrente();
            necesidad = nr;
        } else {
            necesidad = new NecesidadExtraordinaria();
        }
        necesidad.setDescripcion(dto.descripcion());
        necesidad.setCantidadObjetivo(dto.cantidadObjetivo());
        return necesidad;
    }

    public Bien deDTORequestADominio(BienRequest dto) {
        if (dto == null) return null;
        Bien bien;
        if (Boolean.TRUE.equals(dto.perecedero()) || dto.fechaVencimiento() != null) {
            BienPerecedero bp = new BienPerecedero();
            bp.setFechaVencimiento(dto.fechaVencimiento());
            bien = bp;
        } else {
            BienConEstado be = new BienConEstado();
            be.setEstado(Estado.valueOf(dto.estado()));
            bien = be;
        }

        bien.setDescripcion(dto.descripcion());
        bien.setCantidadOriginal(dto.cantidad());
        bien.setCantidadActual(dto.cantidad());
        return bien;
    }

    public Donacion deDTORequestADominio(DonacionRequest dto) {
        if (dto == null) return null;
        Donacion donacion = new Donacion();
        donacion.setDescripcionGeneral(dto.descripcionGeneral());
        if (dto.bienes() != null) {
            donacion.setBienesDeEntrada(
                    dto.bienes().stream()
                            .map(this::deDTORequestADominio)
                            .collect(Collectors.toList())
            );
        }
        return donacion;
    }

    public EstadoDonacion deDTORequestADominio(EstadoDonacionRequest dto) {
        if (dto == null) return null;
        EstadoDonacion estado = new EstadoDonacion();
        estado.setTipoEstado(TipoEstadoDonacion.valueOf(dto.estado()));
        if (dto.justificacion() != null) {
            estado.setJustificacion(dto.justificacion());
        }
        return estado;
    }
}