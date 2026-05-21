package ar.edu.utn.donatrack.dominio.entidad;

import ar.edu.utn.donatrack.dominio.necesidad.Necesidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad sin fines de lucro que se registra para recibir donaciones.
 * Puede ser una escuela rural, comedor, espacio de tutoría, etc.
 */
public class EntidadBeneficiaria {

    private final String razonSocial;
    private final String direccion;
    private final String telefono;
    private final List<String> correosRepresentantes;
    private final List<Necesidad> necesidades;

    public EntidadBeneficiaria(String razonSocial, String direccion,
                               String telefono, List<String> correosRepresentantes) {
        if (razonSocial == null || razonSocial.isBlank())
            throw new IllegalArgumentException("La razón social es obligatoria.");
        if (direccion == null || direccion.isBlank())
            throw new IllegalArgumentException("La dirección es obligatoria.");
        if (correosRepresentantes == null || correosRepresentantes.isEmpty())
            throw new IllegalArgumentException("Al menos un correo de representante es obligatorio.");

        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correosRepresentantes = new ArrayList<>(correosRepresentantes);
        this.necesidades = new ArrayList<>();
    }

    public void registrarNecesidad(Necesidad necesidad) {
        if (necesidad == null)
            throw new IllegalArgumentException("La necesidad no puede ser nula.");
        this.necesidades.add(necesidad);
    }

    public void eliminarNecesidad(Necesidad necesidad) {
        this.necesidades.remove(necesidad);
    }

    public List<Necesidad> getNecesidadesPendientes() {
        return necesidades.stream()
                .filter(n -> !n.estaSatisfecha())
                .toList();
    }

    public String getRazonSocial() { return razonSocial; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public List<String> getCorreosRepresentantes() { return List.copyOf(correosRepresentantes); }
    public List<Necesidad> getNecesidades() { return List.copyOf(necesidades); }
}
