package utn.donatrack.donatrack_frontend.client.dto.donaciones.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoRequestDTO {
    private Integer periodoDias;
    private LocalDate inicioPeriodo;
}