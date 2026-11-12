package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Cuerpo de POST /api/donantes. Humana: nombre, apellido, edad, tipoDocumento, documento, genero, direccion.
 * Juridica: razonSocial, tipoOrganizacion, rubro, documento (CUIT) y representante. Los nulos no se envian.
 * "clave" solo viaja en el auto-registro (la crea quien se registra); en el alta en deposito va vacia.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DonanteRequest(
    String tipoPersona, String nombre, String apellido, Integer edad, String tipoDocumento, String documento,
    String genero, String direccion, String razonSocial, String tipoOrganizacion, String rubro,
    String representanteNombre, String representanteCorreo,
    String correoElectronico, String telefono, String whatsapp, String medioPredeterminado, String clave) {}
