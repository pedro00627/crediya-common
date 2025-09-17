package co.com.pragma.security.model.gateways;

/**
 * Puerto para el enmascaramiento de datos sensibles.
 * Define las operaciones necesarias para ocultar información como correos electrónicos
 * antes de ser registrada o expuesta.
 */
public interface DataMaskerPort {
    /**
     * Enmascara una dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico a enmascarar.
     * @return La dirección de correo electrónico enmascarada.
     */
    String maskEmail(String email);
}