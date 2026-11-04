package com.grupo10.servicio_donaciones.donaciones.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DestinatarioRequestDTO {
    String nombre;
    String telefono;
    String email;
    String whatsapp;
}
