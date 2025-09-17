package co.com.pragma.security.model;

/**
 * Clase de constantes que define los roles de usuario utilizados en la aplicación.
 * Esta clase es final y su constructor es privado para evitar instanciación y herencia.
 */
public final class RoleConstants {

    /**
     * Rol de administrador.
     */
    public static final String ADMIN = "ADMIN";
    /**
     * Rol de asesor.
     */
    public static final String ADVISOR = "ADVISOR";
    /**
     * Rol de cliente.
     */
    public static final String CLIENT = "CLIENT";
    /**
     * Rol para usuarios anónimos o no autenticados, para compatibilidad con Spring Security.
     */
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Constructor privado para prevenir la instanciación de esta clase de constantes.
     */
    private RoleConstants() {
        // Private constructor to prevent instantiation
    }
}
