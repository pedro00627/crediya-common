package co.com.pragma.security.util;

/**
 * Constantes para los nombres de los claims (reclamaciones) utilizados en los tokens JWT.
 * Estas constantes aseguran la consistencia en el acceso a la información dentro del token.
 */
public final class JwtClaimsConstants {
    /**
     * Nombre del claim que contiene los roles del usuario en el token JWT.
     */
    public static final String ROLES = "roles";

    /**
     * Constructor privado para evitar la instanciación de esta clase de constantes.
     */
    private JwtClaimsConstants() {
        // Private constructor to prevent instantiation
    }
}
