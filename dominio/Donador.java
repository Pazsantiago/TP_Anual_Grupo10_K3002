package ar.edu.utn.donatrack.dominio.donador;

import ar.edu.utn.donatrack.dominio.donacion.Donacion;

import java.util.List;

public interface Donador {

    String getCorreoElectronico();

    List<MedioContacto> getContactos();

    MedioContacto getContactoPredeterminado();

    /**
     * Registra una nueva donación asociada a este donador.
     * La lógica de segmentación ocurre en el ServicioDonaciones.
     */
    void agregarDonacion(Donacion donacion);

    List<Donacion> getDonaciones();
}
