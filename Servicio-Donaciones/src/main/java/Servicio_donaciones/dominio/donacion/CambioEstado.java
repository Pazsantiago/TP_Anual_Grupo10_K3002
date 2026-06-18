package Servicio_donaciones.dominio.donacion;

import java.time.LocalDateTime;

public class CambioEstado {

    private EstadoDonacion estado;
    private LocalDateTime fechaHora;
    private String justificacion;
    private String responsable;
    

    // Constructores
    public CambioEstado(EstadoDonacion estado, LocalDateTime fechaHora){
    this.estado = estado;
    this. fechaHora = fechaHora;
}

    public CambioEstado(EstadoDonacion estado, LocalDateTime fechaHora, String responsable){
    this.estado = estado;
    this. fechaHora = fechaHora;
    this.responsable = responsable;
}

public CambioEstado(EstadoDonacion estado, LocalDateTime fechaHora, String responsable, String justificacion){
    this.estado = estado;
    this. fechaHora = fechaHora;
    this. responsable = responsable;
    this.justificacion = justificacion;
}

//Setters
public void setEstado(EstadoDonacion estado){
    this.estado = estado;
}

public void setFechaHora(LocalDateTime fechaHora){
    this.fechaHora = fechaHora;
}

public void setJustificacion(String justificacion){
    this.justificacion = justificacion;
}

public void setResponsable(String responsable){
    this.responsable = responsable;
}

//getters
public EstadoDonacion getEstado(){
    return this.estado;
}

public LocalDateTime getFechaHora(){
    return this.fechaHora;
}

public String getResponsable(){
    return this.responsable;
}

public String getJustificacion(){
    return this.justificacion;
}

}


