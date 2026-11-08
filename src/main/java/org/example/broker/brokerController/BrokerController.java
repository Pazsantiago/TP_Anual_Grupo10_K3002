package org.example.broker.brokerController;

import lombok.RequiredArgsConstructor;
import org.example.broker.comunicador.Comunicador;
import org.example.broker.dominio.donacion.*;
import org.example.broker.dominio.mensajes.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BrokerController {
    private final Comunicador comunicador;
    @PostMapping("/donacionesAsignadas")
    public ResponseEntity<Void> enviarDonacionAsignada(@RequestBody DonacionAsignadaDTO donacionAsignada) {
        System.out.println("DonacionAsignada: " + donacionAsignada);
        comunicador.enviarDonacionAsignada(donacionAsignada);
        return ResponseEntity.ok().build();
    }

    // #-- Recibir de logistica --#
    @PostMapping("/iniciosDeRutas")
    public ResponseEntity<String> comenzarRuta(@RequestBody MensajeInicioDeRuta mensajeInicioDeRuta) {
        System.out.println("Ruta: " + mensajeInicioDeRuta);
        comunicador.enviarInicioDeRuta(mensajeInicioDeRuta);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasConfirmadas")
    public ResponseEntity<String> avisarEntregaRealizada(@RequestBody MensajeConfirmacionEntrega mensajeConfirmacionEntrega) {
        System.out.println("Entrega: " + mensajeConfirmacionEntrega);
        comunicador.enviarEntregaRealizada(mensajeConfirmacionEntrega);
        comunicador.comunicarEntregaIncentivos(new PaqueteEntregado(
                mensajeConfirmacionEntrega.getIdAsignada(),
                mensajeConfirmacionEntrega.getIdEntidad(), 
                mensajeConfirmacionEntrega.getIdSegmentadasIncluidos().stream().toList()
        ));
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/entregasFallidas")
    public ResponseEntity<String> avisarEntregaFallida(@RequestBody MensajeEntregaFallida mensajeEntregaFallida) {
        System.out.println("Fallida: " + mensajeEntregaFallida);
        comunicador.enviarEntregaFallida(mensajeEntregaFallida);
        return ResponseEntity.ok("OK");
    }

}
