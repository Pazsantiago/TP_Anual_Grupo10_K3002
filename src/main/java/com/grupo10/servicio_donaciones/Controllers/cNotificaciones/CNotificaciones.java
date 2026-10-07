package com.grupo10.servicio_donaciones.Controllers.cNotificaciones;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeConfirmacionEntrega;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeEntregaFallida;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeInicioDeRuta;
import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.Services.ServiceDonacionAsignada.ServicioDonacionAsignada;
import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
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
