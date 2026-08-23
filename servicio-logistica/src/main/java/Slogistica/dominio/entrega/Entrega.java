package Slogistica.dominio.entrega;

import Slogistica.dominio.camion.Camion;
import Slogistica.dominio.donacion.DonacionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registro de trazabilidad de la entrega de una donación asignada a un
 * camión determinado.
 */
@Data
@NoArgsConstructor
public class Entrega {

    private Integer id;
    private DonacionDTO donacion;
    private Camion camion;
    private EstadoEntrega estado;
    private LocalDateTime fechaHoraEntrega;
    private String motivoNoRecibida;
    private List<String> fotosRecepcion = new ArrayList<>();

    public Entrega(Integer id, DonacionDTO donacion, Camion camion) {
        this.id = id;
        this.donacion = donacion;
        this.camion = camion;
        this.estado = EstadoEntrega.LISTA_PARA_ENTREGAR;
    }

    public void iniciarTraslado() {
        this.estado = EstadoEntrega.EN_TRASLADO;
    }

    public void confirmarRecepcion(List<String> fotos) {
        this.estado = EstadoEntrega.ENTREGADA;
        this.fechaHoraEntrega = LocalDateTime.now();
        if (fotos != null) {
            this.fotosRecepcion.addAll(fotos);
        }
    }

    public void marcarNoRecibida(String motivo) {
        this.estado = EstadoEntrega.NO_RECIBIDA;
        this.motivoNoRecibida = motivo;
    }
}
