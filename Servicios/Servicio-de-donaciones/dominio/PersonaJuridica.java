package ar.edu.utn.donatrack.dominio.donador;

import ar.edu.utn.donatrack.dominio.donacion.Donacion;
import ar.edu.utn.donatrack.excepciones.ContactoObligatorioException;

import java.util.ArrayList;
import java.util.List;

public class PersonaJuridica implements Donador {

    private String razonSocial;
    private TipoEmpresa tipoEmpresa;
    private String rubro;
    private int cuit;
    private final List<MedioContacto> contactos;
    private MedioContacto contactoPredeterminado;
    private final List<PersonaHumana> representantes;
    private final List<Donacion> donaciones;

    public PersonaJuridica(String razonSocial, TipoEmpresa tipoEmpresa,
                           String rubro,int cuit ,MedioContacto contactoInicial) {
        if (razonSocial == null || razonSocial.isBlank())
            throw new IllegalArgumentException("La razón social es obligatoria.");
        if (tipoEmpresa == null)
            throw new IllegalArgumentException("El tipo de empresa es obligatorio.");
        if (contactoInicial == null)
            throw new ContactoObligatorioException("Al menos un medio de contacto es obligatorio.");

        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.tipoEmpresa = tipoEmpresa;
        this.rubro = rubro;
        this.contactos = new ArrayList<>();
        this.representantes = new ArrayList<>();
        this.donaciones = new ArrayList<>();

        this.contactos.add(contactoInicial);
        this.contactoPredeterminado = contactoInicial;
    }

    public void agregarContacto(MedioContacto contacto) {
        this.contactos.add(contacto);
    }

    public void agregarRepresentante(PersonaHumana representante) {
        if (representante == null)
            throw new IllegalArgumentException("El representante no puede ser nulo.");
        this.representantes.add(representante);
    }

    public void setContactoPredeterminado(MedioContacto contacto) {
        if (!contactos.contains(contacto))
            throw new IllegalArgumentException("El contacto no pertenece a esta organización.");
        this.contactoPredeterminado = contacto;
    }

    @Override
    public String getCorreoElectronico() {
        return contactos.stream()
                .filter(c -> c.getTipo() == MedioContacto.Tipo.CORREO_ELECTRONICO)
                .map(MedioContacto::getValor)
                .findFirst()
                .orElseThrow(() -> new ContactoObligatorioException("La persona jurídica no tiene correo registrado."));
    }

    @Override
    public List<MedioContacto> getContactos() {
        return List.copyOf(contactos);
    }

    @Override
    public MedioContacto getContactoPredeterminado() {
        return contactoPredeterminado;
    }

    @Override
    public void agregarDonacion(Donacion donacion) {
        this.donaciones.add(donacion);
    }

    @Override
    public List<Donacion> getDonaciones() {
        return List.copyOf(donaciones);
    }

    // Getters
    public String getRazonSocial() { return razonSocial; }
    public TipoEmpresa getTipoEmpresa() { return tipoEmpresa; }
    public String getRubro() { return rubro; }
    public List<PersonaHumana> getRepresentantes() { return List.copyOf(representantes); }

    // Setters para actualización (importación CSV)
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) { this.tipoEmpresa = tipoEmpresa; }
    public void setRubro(String rubro) { this.rubro = rubro; }
}
