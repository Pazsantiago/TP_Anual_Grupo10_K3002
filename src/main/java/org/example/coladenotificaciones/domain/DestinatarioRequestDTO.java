package org.example.coladenotificaciones.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DestinatarioRequestDTO {
  String nombre;
  String telefono;
  String email;
  String whatsapp;
}
