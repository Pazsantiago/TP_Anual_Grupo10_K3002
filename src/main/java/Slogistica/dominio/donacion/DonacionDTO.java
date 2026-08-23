package Slogistica.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa, del lado de Logística, una donación segmentada que ya fue
 * asignada a una entidad beneficiaria (estado "Asignación Realizada" en el
 * Servicio de Donaciones) y que está lista para ser entregada.
 * <p>
 * En el diagrama de dominio original esta información viaja como
 * {@code DonacionRegistrada -> DonacionSegmentada -> Bien} más una lista de
 * {@code Direccion}. Acá se comprime todo en un único DTO plano a propósito:
 * Logística es un servicio distinto al de Donaciones y no debe conocer ni
 * depender de sus clases de dominio (Bien, Subcategoria, Donante, etc.).
 * Este DTO es lo único que Logística necesita para planificar y trazar una
 * entrega.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionDTO {

    private Integer idDonacionSegmentada;
    private String subcategoria;
    private String descripcionBien;
    private Integer cantidad;
    private String unidadMedida;

    private Integer idEntidadBeneficiaria;
    private String razonSocialEntidadBeneficiaria;
    private String direccionEntrega;
}
