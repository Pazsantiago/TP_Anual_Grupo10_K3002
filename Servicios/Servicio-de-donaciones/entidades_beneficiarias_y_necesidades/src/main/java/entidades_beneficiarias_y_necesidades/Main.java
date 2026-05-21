package entidades_beneficiarias_y_necesidades;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

class EntidadBeneficiaria {

    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    private List<Necesidad> necesidades;

    public EntidadBeneficiaria(
            String razonSocial,
            String telefono,
            String correoRepresentante,
            String direccion
    ) {
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.correoRepresentante = correoRepresentante;
        this.direccion = direccion;

        this.necesidades = new ArrayList<>();
    }

    public void agregarNecesidad(Necesidad necesidad) { necesidades.add(necesidad); }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoRepresentante() { return correoRepresentante; }
    public void setCorreoRepresentante(String correoRepresentante) { this.correoRepresentante = correoRepresentante; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Necesidad> getNecesidades() { return necesidades; }
    public void setNecesidades(List<Necesidad> necesidades) { this.necesidades = necesidades; }
}

abstract class Necesidad {

    private String descripcion;
    //private Subcategoria subcategoria;
    private int cantidadObjetivo;
    private int cantidadRecibida;

    public Necesidad(
            String descripcion,
            //Subcategoria subcategoria,
            int cantidadObjetivo
    ) {
        this.descripcion = descripcion;
        //this.subcategoria = subcategoria;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadRecibida = 0;
    }

    public abstract boolean estaSatisfecha();

    public void recibirBienes(int cantidad) {
        this.cantidadRecibida += cantidad;
    }

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    //public Subcategoria getSubcategoria() {return subcategoria;}
    //public void setSubcategoria(Subcategoria subcategoria) {this.subcategoria = subcategoria;}
    public int getCantidadObjetivo() {return cantidadObjetivo;}
    public void setCantidadObjetivo(int cantidadObjetivo) {this.cantidadObjetivo = cantidadObjetivo;}
    public int getCantidadRecibida() {return cantidadRecibida;}
    public void setCantidadRecibida(int cantidadRecibida) {this.cantidadRecibida = cantidadRecibida;}
}

class Recurrente extends Necesidad {
    private Periodo periodo;
    private LocalDate fechaInicioPeriodo;
    private int cantidadRecibidaEnPeriodo;

    public Recurrente(
            String descripcion,
            //Subcategoria subcategoria,
            int cantidadObjetivo,
            Periodo periodo,
            LocalDate fechaInicioPeriodo
    ) {
        super(descripcion, /*subcategoria,*/ cantidadObjetivo);

        this.periodo = periodo;
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.cantidadRecibidaEnPeriodo = 0;
    }

    @Override
    public void recibirBienes(int cantidad) {

        // si el período ya venció, reinicia el conteo
        if (!periodoVigente()) {
            reiniciarPeriodo();
        }

        this.cantidadRecibidaEnPeriodo += cantidad;

        // mantiene también el acumulado general
        super.recibirBienes(cantidad);
    }

    public boolean periodoVigente() {

        LocalDate finPeriodo =
                fechaInicioPeriodo.plusDays(periodo.getCantidadDias());

        return !LocalDate.now().isAfter(finPeriodo);
    }

    private void reiniciarPeriodo() {

        this.cantidadRecibidaEnPeriodo = 0;
        this.fechaInicioPeriodo =  
            this.fechaInicioPeriodo.plusDays(periodo.getCantidadDias());
    }

    @Override
    public boolean estaSatisfecha() {

        return periodoVigente()
                && cantidadRecibidaEnPeriodo >= getCantidadObjetivo();
    }

    public Periodo getPeriodo() {return periodo;}
    public void setPeriodo(Periodo periodo) {this.periodo = periodo;}

    public LocalDate getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(LocalDate fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public int getCantidadRecibidaEnPeriodo() {
        return cantidadRecibidaEnPeriodo;
    }

    public void setCantidadRecibidaEnPeriodo(int cantidadRecibidaEnPeriodo) {
        this.cantidadRecibidaEnPeriodo = cantidadRecibidaEnPeriodo;
    }
}

class Extraordinaria extends Necesidad {

    public Extraordinaria(
            String descripcion,
            //Subcategoria subcategoria,
            int cantidadObjetivo
    ) {
        super(descripcion, /*subcategoria,*/ cantidadObjetivo);
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}

class Periodo {

    private int cantidadDias;

    public Periodo(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }
}

/*
enum Categoria {
    MOBILIARIO,
    ALIMENTICIO,
    VESTIMENTA
}

class Subcategoria {

    private String nombre;
    private int cantidadMinima;
    private String unidad;
    private Categoria categoria;

    public Subcategoria(
            String nombre,
            int cantidadMinima,
            String unidad,
            Categoria categoria
    ) {
        this.nombre = nombre;
        this.cantidadMinima = cantidadMinima;
        this.unidad = unidad;
        this.categoria = categoria;
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getCantidadMinima() {return cantidadMinima;}
    public void setCantidadMinima(int cantidadMinima) { this.cantidadMinima = cantidadMinima;}

    public String getUnidad() {return unidad;}
    public void setUnidad(String unidad) {this.unidad = unidad;}

    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
}
*/

