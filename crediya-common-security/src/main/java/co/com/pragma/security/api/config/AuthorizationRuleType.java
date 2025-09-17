package co.com.pragma.security.api.config;

/**
 * Enumeración que define los tipos de reglas de autorización soportados.
 * Utilizada para clasificar y aplicar dinámicamente las reglas de seguridad.
 */
public enum AuthorizationRuleType {
    /**
     * Regla de autorización por roles
     */
    ROLE,
    /**
     * Regla de autorización utiliza un gestor de autorización personalizado (bean).
     */
    MANAGER,
    /**
     * Regla de autorización deniega explícitamente el acceso.
     */
    PERMIT_ALL,
    /**
     * Regla de autorización deniega explícitamente el acceso.
     */
    DENY_ALL
}
