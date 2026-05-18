package org.example.servicios;

import org.example.dominio.donador.*;
import org.example.servicios.ServicioNotificaciones;
import ar.edu.utn.donatrack.repositorios.RepositorioDonadores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de importación masiva de personas donantes desde un archivo CSV.
 *
 * Formato del CSV:
 * TipoPersona | TipoDoc | Documento | Nombre/RazonSocial | Email | Teléfono
 *
 * Reglas:
 * - Si el correo ya existe → se actualiza la información del donador.
 * - Si no existe → se crea el usuario y se envían sus credenciales.
 *
 * Soporta archivos de más de 10.000 filas mediante procesamiento por lotes (streaming).
 */
public class ServicioImportacionCSV {

    private static final int LOTE = 500;
    private static final String SEPARADOR = ",";

    private final RepositorioDonadores repositorioDonadores;
    private final ServicioNotificaciones servicioNotificaciones;

    public ServicioImportacionCSV(RepositorioDonadores repositorioDonadores,
                                  ServicioNotificaciones servicioNotificaciones) {
        this.repositorioDonadores = repositorioDonadores;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    /**
     * Procesa el CSV en streaming para manejar archivos grandes (>10.000 filas).
     * @return resultado con contadores de creados, actualizados y errores
     */
    public ResultadoImportacion importar(InputStream csvStream) throws IOException {
        int creados = 0, actualizados = 0, errores = 0;
        List<String> erroresDetalle = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {

            String linea;
            int numeroLinea = 0;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                // Saltar encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (linea.isBlank()) continue;

                try {
                    boolean esNuevo = procesarLinea(linea);
                    if (esNuevo) creados++;
                    else actualizados++;
                } catch (Exception e) {
                    errores++;
                    erroresDetalle.add("Línea " + numeroLinea + ": " + e.getMessage());
                }
            }
        }

        return new ResultadoImportacion(creados, actualizados, errores, erroresDetalle);
    }

    /**
     * @return true si se creó un nuevo donador, false si se actualizó uno existente
     */
    private boolean procesarLinea(String linea) {
        String[] campos = linea.split(SEPARADOR, -1);
        if (campos.length < 5) {
            throw new IllegalArgumentException("Formato incorrecto, se esperan al menos 5 columnas.");
        }

        String tipoPersona  = campos[0].trim().toUpperCase();
        String documento    = campos[2].trim();
        String nombreRazon  = campos[3].trim();
        String email        = campos[4].trim().toLowerCase();
        String telefono     = campos.length > 5 ? campos[5].trim() : null;

        boolean existe = repositorioDonadores.existePorCorreo(email);

        if (existe) {
            actualizarDonador(email, nombreRazon, telefono, tipoPersona);
            return false;
        } else {
            crearDonador(tipoPersona, documento, nombreRazon, email, telefono);
            return true;
        }
    }

    private void crearDonador(String tipoPersona, String documento,
                              String nombreRazon, String email, String telefono) {
        Donador donador = switch (tipoPersona) {
            case "HUMANA" -> {
                String[] partes = nombreRazon.split(" ", 2);
                String nombre   = partes[0];
                String apellido = partes.length > 1 ? partes[1] : "";
                var ph = new PersonaHumana(nombre, apellido, 0, documento, "", "", email);
                if (telefono != null && !telefono.isBlank()) {
                    ph.agregarContacto(new MedioContacto(MedioContacto.Tipo.TELEFONO, telefono));
                }
                yield ph;
            }
            case "JURIDICA" -> {
                var contacto = new MedioContacto(MedioContacto.Tipo.CORREO_ELECTRONICO, email);
                var pj = new PersonaJuridica(nombreRazon, TipoEmpresa.EMPRESA, "", contacto);
                if (telefono != null && !telefono.isBlank()) {
                    pj.agregarContacto(new MedioContacto(MedioContacto.Tipo.TELEFONO, telefono));
                }
                yield pj;
            }
            default -> throw new IllegalArgumentException("Tipo de persona desconocido: " + tipoPersona);
        };

        repositorioDonadores.guardar(donador);
        servicioNotificaciones.notificarCredencialesAcceso(donador.getCorreoElectronico());
    }

    private void actualizarDonador(String email, String nombreRazon,
                                   String telefono, String tipoPersona) {
        repositorioDonadores.buscarPorCorreo(email).ifPresent(donador -> {
            // Actualización de campos según tipo
            if (donador instanceof PersonaHumana ph && "HUMANA".equals(tipoPersona)) {
                String[] partes = nombreRazon.split(" ", 2);
                ph.setNombre(partes[0]);
                if (partes.length > 1) ph.setApellido(partes[1]);
            } else if (donador instanceof PersonaJuridica pj && "JURIDICA".equals(tipoPersona)) {
                pj.setRazonSocial(nombreRazon);
            }
            // Re-guardar (el repositorio sobreescribe por correo)
            repositorioDonadores.guardar(donador);
        });
    }

    // ─── Resultado de la importación ─────────────────────────────────────────

    public record ResultadoImportacion(
            int creados,
            int actualizados,
            int errores,
            List<String> erroresDetalle
    ) {
        public int total() { return creados + actualizados + errores; }

        @Override
        public String toString() {
            return String.format(
                "Importación finalizada: %d creados, %d actualizados, %d errores de %d registros procesados.",
                creados, actualizados, errores, total()
            );
        }
    }
}
