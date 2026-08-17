package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Insignia {
        private long ID;
        private String nombre;
        private String descripcion;
        private String imagenURL;
        private boolean esVisible;

        public void toggleVisibilidad() {this.esVisible = !(this.esVisible);}

}

