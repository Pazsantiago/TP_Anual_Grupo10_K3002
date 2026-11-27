package com.grupo10.servicio_donaciones.mapper;

import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.RankingEntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.algoritmosAsignacion.SegmentadaRankings;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.Bien;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.BienConEstado;
import com.grupo10.servicio_donaciones.donaciones.dominio.bien.BienPerecedero;
import com.grupo10.servicio_donaciones.donaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.donaciones.dominio.categoria.Subcategoria;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.EstadoDonacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.*;
import com.grupo10.servicio_donaciones.donaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.NecesidadExtraordinaria;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.NecesidadRecurrente;
import com.grupo10.servicio_donaciones.donaciones.dominio.necesidad.Periodo;
import com.grupo10.servicio_donaciones.dtos.request.*;
import com.grupo10.servicio_donaciones.dtos.response.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DTOSMapper {

    public DonanteResponseDTO deDominioAResponse(Donante donante) {
        if (donante == null) return null;

        DonanteResponseDTO dto = new DonanteResponseDTO();
        dto.setId(donante.getId());

        if (donante.getPersona() instanceof PersonaHumana ph) {
            dto.setNombre(ph.getNombre());
        } else if (donante.getPersona() instanceof PersonaJuridica pj) {
            dto.setNombre(pj.getRazonSocial());
        }

        MedioContactoResponseDTO contactoDTO = new MedioContactoResponseDTO();
        MedioContacto contactoPred = donante.obtenerContactoPredeterminado();
        if (contactoPred != null) {
            if (contactoPred.getTipo() != null) {
                contactoDTO.setTipo(contactoPred.getTipo().toString());
            }
            contactoDTO.setCorreoElectronico(contactoPred.getCorreoElectronico());
            contactoDTO.setTelefono(contactoPred.getTelefono());
            dto.setContactoPredeterminado(contactoDTO);
        } else if (donante.getMediosDeContacto() != null && !donante.getMediosDeContacto().isEmpty()) {
            MedioContacto primerContacto = donante.getMediosDeContacto().get(0);
            if (primerContacto.getTipo() != null) {
                contactoDTO.setTipo(primerContacto.getTipo().toString());
            }
            contactoDTO.setCorreoElectronico(primerContacto.getCorreoElectronico());
            contactoDTO.setTelefono(primerContacto.getTelefono());
            dto.setContactoPredeterminado(contactoDTO);
        }

        if (donante.getDonaciones() != null) {
            dto.setDonaciones(
                    donante.getDonaciones().stream()
                            .map(this::deDominioAResponse)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public DonacionResponseDTO deDominioAResponse(Donacion donacion) {
        if (donacion == null) return null;

        DonacionResponseDTO dto = new DonacionResponseDTO();
        dto.setId(donacion.getId());
        dto.setFechaEntrada(donacion.getFechaRegistro());

        if (donacion.getBienesDeEntrada() != null) {
            dto.setCantBienes(donacion.getBienesDeEntrada().size());
            dto.setCategoriasIncluidas(
                    donacion.getBienesDeEntrada().stream()
                            .map(b -> b.getSubcategoria() != null && b.getSubcategoria().getCategoria() != null
                                    ? b.getSubcategoria().getCategoria().getNombre() : "General")
                            .distinct()
                            .collect(Collectors.toList())
            );
        }

        if (donacion.getDonacionesSegmentadas() != null) {
            dto.setSegmentadasIncluidas(
                    donacion.getDonacionesSegmentadas().stream()
                            .map(DonacionSegmentada::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public DonacionSegmentadaResponseDTO deDominioAResponse(DonacionSegmentada segmentada) {
        if (segmentada == null) return null;

        DonacionSegmentadaResponseDTO dto = new DonacionSegmentadaResponseDTO();
        dto.setIdSegmentada(segmentada.getId());
        if (segmentada.getBien() != null) {
            dto.setCantidadOriginal(segmentada.getBien().getCantidadOriginal());
            dto.setCantidadActual(segmentada.getBien().getCantidadActual());
            if (segmentada.getEstadoActual() != null && segmentada.getEstadoActual().getTipoEstado() != null) {
                dto.setEstadoActual(segmentada.getEstadoActual().getTipoEstado().toString());
            }
            if (segmentada.getBien().getSubcategoria() != null) {
                dto.setSubcategoria(segmentada.getBien().getSubcategoria().getNombre());
                if (segmentada.getBien().getSubcategoria().getCategoria() != null) {
                    dto.setCategoria(segmentada.getBien().getSubcategoria().getCategoria().getNombre());
                }
            }
            if (segmentada.getBien().getUnidad() != null) {
                dto.setUnidad(segmentada.getBien().getUnidad().toString());
            }
        }
        return dto;
    }

    public DonacionAsignadaResponseDTO deDominioAResponse(DonacionAsignada asignada) {
        if (asignada == null) return null;

        DonacionAsignadaResponseDTO dto = new DonacionAsignadaResponseDTO();
        dto.setIdAsignada(asignada.getId());
        if (asignada.getNecesidadResuelta() != null && asignada.getNecesidadResuelta().getEntidadBeneficiaria() != null) {
            dto.setIdEntidad(asignada.getNecesidadResuelta().getEntidadBeneficiaria().getId());
            dto.setDireccionEntidad(asignada.getNecesidadResuelta().getEntidadBeneficiaria().getDireccion());
        }
        if (asignada.getDonacionesSegmentadas() != null) {
            dto.setDonacionesSegmentadas(
                    asignada.getDonacionesSegmentadas().stream()
                            .map(this::deDominioAResponse)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public EntidadBeneficiariaResponseDTO deDominioAResponse(EntidadBeneficiaria entidad) {
        if (entidad == null) return null;

        EntidadBeneficiariaResponseDTO dto = new EntidadBeneficiariaResponseDTO();
        dto.setId(entidad.getId());
        dto.setRazonSocial(entidad.getRazonSocial());
        dto.setDireccion(entidad.getDireccion());
        dto.setTelefono(entidad.getTelefono());
        dto.setCorreoRepresentante(entidad.getCorreoRepresentante());

        return dto;
    }

    public NecesidadResponseDTO deDominioAResponse(Necesidad necesidad) {
        if (necesidad == null) return null;

        NecesidadResponseDTO dto = new NecesidadResponseDTO();
        dto.setId(necesidad.getId());
        dto.setDescripcion(necesidad.getDescripcion());
        dto.setCantidadObjetivo(necesidad.getCantidadObjetivo());
        dto.setEstaSatisfechaConDonacion(necesidad.getEstaSatisfechaConDonacion());
        dto.setCantidadAsignada(necesidad.getCantidadRecibida());
        if (necesidad.getEntidadBeneficiaria() != null) {
            dto.setIdEntidadBeneficiaria(necesidad.getEntidadBeneficiaria().getId());
        }

        return dto;
    }

    public SegmentadaRankingsResponseDTO deDominioAResponse(SegmentadaRankings segmentadaRankings) {
        if (segmentadaRankings == null) return null;
        SegmentadaRankingsResponseDTO dto = new SegmentadaRankingsResponseDTO();
        dto.setSegmentada(deDominioAResponse(segmentadaRankings.getSegmentada()));
        if (segmentadaRankings.getRankings() != null) {
            dto.setRankings(segmentadaRankings.getRankings().stream().map(this::deDominioAResponse).collect(Collectors.toList()));
        }
        return dto;
    }

    public RankingEntidadBeneficiariaResponseDTO deDominioAResponse(RankingEntidadBeneficiaria ranking) {
        if (ranking == null) return null;

        RankingEntidadBeneficiariaResponseDTO dto = new RankingEntidadBeneficiariaResponseDTO();
        dto.setPosicion(ranking.getPosicion());
        dto.setPuntaje(ranking.getPuntaje());
        if (ranking.getAlgoritmoUsado() != null) {
            dto.setAlgoritmoUsado(ranking.getAlgoritmoUsado().toString());
        }
        dto.setEntidadBeneficiaria(deDominioAResponse(ranking.getEntidadBeneficiaria()));
        return dto;
    }

    public CategoriaResponseDTO deDominioAResponse(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        if (categoria.getTipo() != null) {
            dto.setTipo(categoria.getTipo().toString());
        }

        return dto;
    }

    public Documento deDTORequestADominio(DocumentoRequestDTO dto) {
        if (dto == null) return null;
        Documento doc = new Documento();
        doc.setTipoDocumento(dto.getTipoDocumento());
        doc.setDocumento(dto.getDocumento());
        return doc;
    }

    public Direccion deDTORequestADominio(DireccionRequestDTO dto) {
        if (dto == null) return null;
        Direccion dir = new Direccion();
        dir.setCalle(dto.getCalle());
        dir.setAltura(dto.getAltura());
        dir.setLocalidad(dto.getLocalidad());
        dir.setCodigoPostal(dto.getCodigoPostal());
        return dir;
    }

    public Persona deDTORequestADominio(PersonaRequestDTO dto) {
        if (dto == null) return null;

        Persona p;
        if ("JURIDICA".equalsIgnoreCase(dto.getTipo()) || dto.getRazonSocial() != null || dto.getTipoJuridica() != null) {
            PersonaJuridica pj = new PersonaJuridica();
            pj.setRazonSocial(dto.getRazonSocial());
            pj.setTipoJuridica(dto.getTipoJuridica());
            pj.setRubro(dto.getRubro());
            p = pj;
        } else {
            PersonaHumana ph = new PersonaHumana();
            ph.setNombre(dto.getNombre());
            ph.setEdad(dto.getEdad());
            ph.setGenero(dto.getGenero());
            p = ph;
        }

        if (dto.getDocumento() != null) {
            p.setDocumento(deDTORequestADominio(dto.getDocumento()));
        }
        if (dto.getDireccion() != null) {
            p.setDireccion(deDTORequestADominio(dto.getDireccion()));
        }

        return p;
    }

    public MedioContacto deDTORequestADominio(MedioContactoRequestDTO dto) {
        if (dto == null) return null;
        MedioContacto mc = new MedioContacto();
        mc.setTipo(dto.getTipo());
        mc.setCorreoElectronico(dto.getCorreoElectronico());
        mc.setTelefono(dto.getTelefono());
        mc.setEsPredeterminado(dto.isEsPredeterminado());
        return mc;
    }

    public Donante deDTORequestADominio(DonanteRequestDTO dto) {
        if (dto == null) return null;
        Donante donante = new Donante();

        if (dto.getPersona() != null) {
            donante.setPersona(deDTORequestADominio(dto.getPersona()));
        }

        if (dto.getMediosDeContacto() != null) {
            for (MedioContactoRequestDTO mcDto : dto.getMediosDeContacto()) {
                MedioContacto mc = deDTORequestADominio(mcDto);

                // 1. Agregar SIEMPRE a la colección de medios de contacto
                donante.agregarMedioContacto(mc);

                // 2. Si es predeterminado, actualizar la marca/referencia
                if (mcDto.isEsPredeterminado()) {
                    donante.cambiarContactoPredeterminado(mc);
                }
            }
        }
        
        if (dto.getDonaciones() != null) {
            donante.setDonaciones(
                    dto.getDonaciones().stream()
                            .map(this::deDTORequestADominio)
                            .collect(Collectors.toList())
            );
        }

        return donante;
    }

    public Subcategoria deDTORequestADominio(SubcategoriaRequestDTO dto) {
        if (dto == null) return null;
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(dto.getNombre());
        if (dto.getIdCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getIdCategoria());
            subcategoria.setCategoria(categoria);
        }
        return subcategoria;
    }

    public Bien deDTORequestADominio(BienRequestDTO dto) {
        if (dto == null) return null;

        Bien bien;
        if ("PERECEDERO".equalsIgnoreCase(dto.getTipo()) || (dto.getTipo() == null && dto.getFechaVencimiento() != null)) {
            BienPerecedero bp = new BienPerecedero();
            bp.setFechaVencimiento(dto.getFechaVencimiento());
            bien = bp;
        } else {
            BienConEstado be = new BienConEstado();
            be.setEstado(dto.getEstado());
            bien = be;
        }

        bien.setDescripcion(dto.getDescripcion());
        if (dto.getSubcategoria() != null) {
            bien.setSubcategoria(deDTORequestADominio(dto.getSubcategoria()));
        }
        bien.setFoto(dto.getFoto());
        bien.setUnidad(dto.getUnidad());
        bien.setCantidadOriginal(dto.getCantidadOriginal());
        bien.setCantidadActual(dto.getCantidadOriginal());

        return bien;
    }

    public Donacion deDTORequestADominio(DonacionRequestDTO dto) {
        if (dto == null) return null;
        Donacion donacion = new Donacion();
        donacion.setDescripcionGeneral(dto.getDescripcionGeneral());
        if (dto.getFechaRegistro() != null) {
            donacion.setFechaRegistro(dto.getFechaRegistro());
        }
        if (dto.getBienesDeEntrada() != null) {
            donacion.setBienesDeEntrada(dto.getBienesDeEntrada().stream().map(this::deDTORequestADominio).collect(Collectors.toList()));
        }
        return donacion;
    }

    public EstadoDonacion deDTORequestADominio(EstadoDonacionRequestDTO dto) {
        if (dto == null) return null;
        EstadoDonacion estado = new EstadoDonacion();
        estado.setTipoEstado(dto.getTipoEstado());
        if (dto.getJustificacion() != null) {
            estado.setJustificacion(dto.getJustificacion());
        }
        return estado;
    }

    public Periodo deDTORequestADominio(PeriodoRequestDTO dto) {
        if (dto == null) return null;
        Periodo periodo = new Periodo();
        periodo.setPeriodoDias(dto.getPeriodoDias());
        periodo.setInicioPeriodo(dto.getInicioPeriodo());
        return periodo;
    }

    public Necesidad deDTORequestADominio(NecesidadRequestDTO dto) {
        if (dto == null) return null;

        Necesidad necesidad;
        if ("RECURRENTE".equalsIgnoreCase(dto.getTipo()) || (dto.getTipo() == null && dto.getPeriodoRequestDTO() != null)) {
            NecesidadRecurrente nr = new NecesidadRecurrente();
            if (dto.getPeriodoRequestDTO() != null) {
                nr.setPeriodo(deDTORequestADominio(dto.getPeriodoRequestDTO()));
            }
            if (dto.getCantidadRecibidaEnPeriodo() != null) {
                nr.setCantidadRecibidaEnPeriodo(dto.getCantidadRecibidaEnPeriodo());
            }
            necesidad = nr;
        } else {
            necesidad = new NecesidadExtraordinaria();
        }
        necesidad.setDescripcion(dto.getDescripcion());
        if (dto.getSubcategoria() != null) {
            necesidad.setSubcategoria(deDTORequestADominio(dto.getSubcategoria()));
        }
        necesidad.setCantidadObjetivo(dto.getCantidadObjetivo());
        if (dto.getCantidadRecibida() != null) {
            necesidad.setCantidadRecibida(dto.getCantidadRecibida());
        }
        if (dto.getEstaSatisfechaConDonacion() != null) {
            necesidad.setEstaSatisfechaConDonacion(dto.getEstaSatisfechaConDonacion());
        }
        necesidad.setSatisfechaEn(dto.getSatisfechaEn());

        if (dto.getEntidadBeneficiaria() != null) {
            necesidad.setEntidadBeneficiaria(deDTORequestADominio(dto.getEntidadBeneficiaria()));
        }

        return necesidad;
    }

    public EntidadBeneficiaria deDTORequestADominio(EntidadBeneficiariaRequestDTO dto) {
        if (dto == null) return null;
        EntidadBeneficiaria entidad = new EntidadBeneficiaria();
        entidad.setRazonSocial(dto.getRazonSocial());
        entidad.setTelefono(dto.getTelefono());
        entidad.setCorreoRepresentante(dto.getCorreoRepresentante());
        entidad.setDireccion(dto.getDireccion());

        if (dto.getNecesidades() != null) {
            entidad.setNecesidades(
                    dto.getNecesidades().stream()
                            .map(this::deDTORequestADominio)
                            .collect(Collectors.toList())
            );
        }

        return entidad;
    }
}
