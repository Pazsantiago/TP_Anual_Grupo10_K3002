package Servicio_notificaciones.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import Servicio_notificaciones.dominio.MedioNotificacion;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .reduce((a, b) -> a + "; " + b)
        .orElse("Datos inválidos");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body(mensaje));
  }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonError(
            HttpMessageNotReadableException ex
    ) {
        String mensaje = "JSON inválido";

        Throwable causa = ex.getMostSpecificCause();
        if (causa instanceof InvalidFormatException ife
                && ife.getTargetType().isEnum()) {

            mensaje = "Valor inválido para "
                    + ife.getTargetType().getSimpleName()
                    + ": "
                    + ife.getValue()
            + " disponibles: "+ Arrays.stream(MedioNotificacion.values())
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));;

        }
        else if (causa != null && causa.getMessage() != null) {
            mensaje += " " + causa.getMessage();
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body(mensaje));
    }


  private Map<String, Object> body(String mensaje) {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("timestamp", LocalDateTime.now());
    map.put("error", mensaje);
    return map;
  }

}
