package ar.edu.utn.donatrack.dominio.donante;

import ar.edu.utn.donatrack.dominio.donacion.Donacion;
import ar.edu.utn.donatrack.excepciones.ContactoObligatorioException;

import java.util.ArrayList;
import java.util.List;

public class PersonaHumana implements Donante {

    private String nombre;
    private String apellido;
    private int edad;
    private String documento;
    private String genero;
    private String direccion;
    private final List<MedioContacto> contactos;
    private MedioContacto contactoPredeterminado;
    private final List<Donacion> donaciones;

    /**
     * El correo electrónico es obligatorio y se agrega automáticamente como primer contacto.
     */
    public PersonaHumana(String nombre, String apellido, int edad, String documento,
                         String genero, String direccion, String correoElectronico) {
        validarCamposObligatorios(nombre, apellido, documento, correoElectronico);
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.documento = documento;
        this.genero = genero;
        this.direccion = direccion;
        this.contactos = new ArrayList<>();
        this.donaciones = new ArrayList<>();

        MedioContacto correo = new MedioContacto(MedioContacto.Tipo.CORREO_ELECTRONICO, correoElectronico);
        this.contactos.add(correo);
        this.contactoPredeterminado = correo;
    }

    private void validarCamposObligatorios(String nombre, String apellido,
                                           String documento, String correo) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        if (apellido == null || apellido.isBlank()) throw new IllegalArgumentException("El apellido es obligatorio.");
        if (documento == null || documento.isBlank()) throw new IllegalArgumentException("El documento es obligatorio.");
        if (correo == null || correo.isBlank()) throw new ContactoObligatorioException("El correo electrónico es obligatorio.");
    }

    public void agregarContacto(MedioContacto contacto) {
        if (contacto.getTipo() == MedioContacto.Tipo.CORREO_ELECTRONICO) {
            throw new IllegalArgumentException("El correo ya está registrado como contacto principal.");
        }
        this.contactos.add(contacto);
    }

    public void setContactoPredeterminado(MedioContacto contacto) {
        if (!contactos.contains(contacto)) {
            throw new IllegalArgumentException("El contacto no pertenece a esta persona.");
        }
        this.contactoPredeterminado = contacto;
    }

    @Override
    public String getCorreoElectronico() {
        return contactos.stream()
                .filter(c -> c.getTipo() == MedioContacto.Tipo.CORREO_ELECTRONICO)
                .map(MedioContacto::getValor)
                .findFirst()
                .orElseThrow(() -> new ContactoObligatorioException("La persona no tiene correo registrado."));
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
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getEdad() { return edad; }
    public String getDocumento() { return documento; }
    public String getGenero() { return genero; }
    public String getDireccion() { return direccion; }

    // Setters para actualización de datos (importación CSV)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setGenero(String genero) { this.genero = genero; }
}
