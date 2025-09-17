package co.com.pragma.security.api;

import co.com.pragma.security.api.config.AuthorizationRule;

import java.util.List;

/**
 * Interfaz que define el contrato para proporcionar reglas de autorización.
 * Cada microservicio debe implementar esta interfaz para definir sus reglas específicas.
 */
public interface SecurityRulesProvider {
    /**
     * Obtiene la lista de reglas de autorización definidas para el microservicio.
     *
     * @return Lista de reglas de autorización configuradas para el microservicio
     */
    List<AuthorizationRule> authorization();
}