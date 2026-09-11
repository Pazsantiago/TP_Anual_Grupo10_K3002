package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class Destinatario {
    private String email;
    private String telefono;
    private String whatsapp;
}
