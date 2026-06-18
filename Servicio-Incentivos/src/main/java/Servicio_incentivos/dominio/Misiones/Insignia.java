package Servicio_incentivos.dominio.Misiones;

public class Insignia {
    private long ID;
    private String nombre;
    private String descripcion;
    private String imagenURL;
    private boolean esVisible;

    public void toggleVisibilidad() {this.esVisible = !(this.esVisible);}

}
