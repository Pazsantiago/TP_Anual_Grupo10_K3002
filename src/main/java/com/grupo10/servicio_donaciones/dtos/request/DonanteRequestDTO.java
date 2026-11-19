package com.grupo10.servicio_donaciones.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonanteRequestDTO {
    private List<DonacionRequestDTO> donaciones = new ArrayList<>();
    private PersonaRequestDTO persona;
    private List<MedioContactoRequestDTO> mediosDeContacto = new ArrayList<>();

}
