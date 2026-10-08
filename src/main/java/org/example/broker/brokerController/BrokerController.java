package org.example.broker.brokerController;

import org.example.broker.comunicador.Comunicador;
import org.example.broker.dominio.donacion.DonacionAsignadaDTO;
import org.example.broker.dominio.mensajes.MensajeConfirmacionEntrega;
import org.example.broker.dominio.mensajes.MensajeEntregaFallida;
import org.example.broker.dominio.mensajes.MensajeInicioDeRuta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/donacionesAsignadas")
public class BrokerController {
    private Comunicador comunicador;
    @GetMapping("/donacionesAsignadas")
    public ResponseEntity<Void> enviarDonacionAsignada(@RequestBody DonacionAsignadaDTO donacionAsignada) {
        comunicador.enviarDonacionAsignada(donacionAsignada);
        return ResponseEntity.ok().build();
    }

    // #-- Recibir de logistica --#
    @PostMapping("/iniciosDeRutas")
    public ResponseEntity<String> comenzarRuta(@RequestBody MensajeInicioDeRuta mensajeInicioDeRuta) {
        comunicador.enviarInicioDeRuta(mensajeInicioDeRuta);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasConfirmadas")
    public ResponseEntity<String> avisarEntregaRealizada(@RequestBody MensajeConfirmacionEntrega mensajeConfirmacionEntrega) {
        comunicador.enviarEntregaRealizada(mensajeConfirmacionEntrega);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasFallidas")
    public ResponseEntity<String> avisarEntregaFallida(@RequestBody MensajeEntregaFallida mensajeEntregaFallida) {
        comunicador.enviarEntregaFallida(mensajeEntregaFallida);
        return ResponseEntity.ok("OK");
    }

}
