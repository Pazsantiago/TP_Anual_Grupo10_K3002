package Controllers.cNotificaciones;

import Sdonaciones.dominio.mensajes.MensajeConfirmacionEntrega;
import Sdonaciones.dominio.mensajes.MensajeEntregaFallida;
import Sdonaciones.dominio.mensajes.MensajeInicioDeRuta;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import Services.ServiceDonacionAsignada.ServicioDonacionAsignada;
import Services.ServiceDonantes.ServicioDonantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificaciones")
public class CNotificaciones {
    private final ServicioDonantes servicioDonantes;
    private final ServicioDonacionAsignada servicioDonacionAsignada;
    private final ServicioBeneficiarias servicioBeneficiarias;

    public CNotificaciones(ServicioDonantes servicioDonantes, ServicioDonacionAsignada servicioDonacionAsignada, ServicioBeneficiarias servicioBeneficiarias) {
        this.servicioDonantes = servicioDonantes;
        this.servicioDonacionAsignada = servicioDonacionAsignada;
        this.servicioBeneficiarias = servicioBeneficiarias;
    }

    @PostMapping("/iniciosDeRutas")
    public ResponseEntity<String> comenzarRuta(@RequestBody MensajeInicioDeRuta mensajeInicioDeRuta) {
        servicioDonacionAsignada.comenzarRuta(mensajeInicioDeRuta);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasConfirmadas")
    public ResponseEntity<String> updateEstadoDonacion(@RequestBody MensajeConfirmacionEntrega mensajeConfirmacionEntrega) {
        servicioDonacionAsignada.entregaConfirmada(mensajeConfirmacionEntrega);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasFallidas")
    public ResponseEntity<String> updateEstadoDonacion(@RequestBody MensajeEntregaFallida mensajeEntregaFallida) {
        servicioDonacionAsignada.entregaFallida(mensajeEntregaFallida);
        return ResponseEntity.ok("OK");
    }

}
