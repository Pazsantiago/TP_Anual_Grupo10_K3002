package com.grupo10.servicio_donaciones.Services.ServiceDonaciones;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.TipoEstadoDonacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import com.grupo10.servicio_donaciones.comunicador.Comunicador;
import com.grupo10.servicio_donaciones.repositorios.RepoDonaciones;
import com.grupo10.servicio_donaciones.repositorios.RepoDonacionesAsignadas;
import com.grupo10.servicio_donaciones.repositorios.RepoDonantes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioDonacion {
    private final RepoDonaciones repoDonaciones;
    private final RepoDonacionesAsignadas repoDonacionesAsignadas;
    private final RepoDonantes repoDonantes;
    private final ServicioDonantes servicioDonantes;
    @Qualifier("restClientIncentivos")
    private final RestClient restClientIncentivos;
    private final Comunicador comunicador;

    public List<Donacion> getAllDonaciones() {
        return repoDonaciones.findAll();
    }


    public Donacion getDonacionById(Long idDonacion) {
        return repoDonaciones.findById(idDonacion).orElse(null);
    }

    public Donacion buscarDonacionPorIdSegmentada(Long idSegmentada) {
        return repoDonaciones.findByDonacionesSegmentadasId(idSegmentada).orElse(null);
    }


    public List<DonacionSegmentada> obtenerDonacionesSegmentadasEnDeposito() {
        return repoDonaciones.obtenerDonacionesSegmentadasPorEstado(TipoEstadoDonacion.EN_DEPOSITO);
    }


    public List<DonacionAsignada> obtenerDonacionesAsignadas() {
        return repoDonacionesAsignadas.findAll();
    }

    public List<DonacionSegmentada> obtenerDonacionesSegmentadas() {
        return repoDonaciones.getAllSegmentadas();
    }

    public DonacionSegmentada obtenerDonacionSegmentada(Long idSegmentada) {
        return repoDonaciones.findByDonacionSegmentadaId(idSegmentada).orElse(null);
    }

    @Transactional
    public Donacion createDonacion(Donacion donacion, Long idDonante) {
        Donante donante = repoDonantes.findById(idDonante).orElse(null);
        donacion.setDonante(donante);
        donante.agregarDonacion(donacion);
        donacion.setearRelaciones();
        repoDonaciones.save(donacion);
        servicioDonantes.setearCategorias(donante);
        repoDonantes.save(donante);
        comunicador.enviarDonante(donante, restClientIncentivos);
        return donacion;

    }

    @Transactional
    public Donacion updateDonacion(Long idDonacion, Donacion updateDonacion) {
        Donacion oldDonacion = repoDonaciones.findById(idDonacion).orElse(null);
        Donante donante = repoDonantes.findById(oldDonacion.getDonante().getId()).orElse(null);
        oldDonacion.setId(idDonacion);
        oldDonacion.eliminarSegmentadas();
        oldDonacion.setDonacionesSegmentadas(updateDonacion.getDonacionesSegmentadas());
        oldDonacion.setDonante(updateDonacion.getDonante());
        oldDonacion.setDescripcionGeneral(updateDonacion.getDescripcionGeneral());
        oldDonacion.setFechaRegistro(updateDonacion.getFechaRegistro());
        oldDonacion.setBienesDeEntrada(updateDonacion.getBienesDeEntrada());
        donante.actualizarDonacion(oldDonacion);
        repoDonaciones.save(oldDonacion);
        servicioDonantes.setearCategorias(donante);
        repoDonantes.save(donante);
        comunicador.enviarDonanteActualizado(donante, restClientIncentivos);
        return oldDonacion;
    }

    @Transactional
    public String deleteDonacion(Long idDonacion) {
        Donante donante = repoDonaciones.findById(idDonacion).map(d -> d.getDonante()).orElse(null);
        donante.eliminarDonacion(idDonacion);
        repoDonaciones.deleteById(idDonacion);
        comunicador.enviarDonanteActualizado(donante, restClientIncentivos);
        return "Donacion borrada";
    }


}
