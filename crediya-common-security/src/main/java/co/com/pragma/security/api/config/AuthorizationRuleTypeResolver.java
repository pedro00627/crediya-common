package co.com.pragma.security.api.config;

import co.com.pragma.model.log.gateways.LoggerPort;
import org.springframework.stereotype.Component;

/**
 * Componente que se encarga de determinar el tipo de una regla de autorización
 * basándose en sus propiedades. Utiliza una cadena de responsabilidad funcional.
 */
@Component
public class AuthorizationRuleTypeResolver {

    private final LoggerPort logger; // Inyectar LoggerPort

    /**
     * Constructor para AuthorizationRuleTypeResolver.
     *
     * @param logger El puerto de logging para registrar eventos.
     */
    public AuthorizationRuleTypeResolver(LoggerPort logger) {
        this.logger = logger;
    }

    /**
     * Determina el tipo de regla de autorización basándose en sus propiedades.
     * La prioridad de las reglas es la siguiente:
     * 1. MANAGER: Si la regla especifica un nombre de bean de manager.
     * 2. DENY_ALL: Por defecto, si ninguna de las condiciones anteriores coincide.
     *
     * @param rule La regla de autorización a evaluar.
     * @return El {@link AuthorizationRuleType} que corresponde a la regla.
     */
    public AuthorizationRuleType determineRuleType(AuthorizationRule rule) {
        logger.debug("Determining rule type for path: {} method: {} manager: {}", rule.path(), rule.method(), rule.managerBeanName());

        if (isManagerRule(rule)) {
            logger.debug("Rule for path {} is MANAGER.", rule.path());
            return AuthorizationRuleType.MANAGER;
        }

        if (isRoleRule(rule)) {
            logger.debug("Rule for path {} is ROLE.", rule.path());
            return AuthorizationRuleType.ROLE;

        }

        logger.debug("Rule for path {} is DENY_ALL (fallback).", rule.path());
        return AuthorizationRuleType.DENY_ALL;
    }

    /**
     * Verifica si la regla es de tipo MANAGER.
     *
     * @param rule La regla de autorización.
     * @return true si es una regla MANAGER, false en caso contrario.
     */
    private boolean isManagerRule(AuthorizationRule rule) {
        boolean isManager = rule.managerBeanName() != null && !rule.managerBeanName().isEmpty();
        logger.debug("isManagerRule for path {}: {}", rule.path(), isManager);
        return isManager;
    }

    /**
     * Verifica si la regla es de tipo MANAGER.
     *
     * @param rule La regla de autorización.
     * @return true si es una regla MANAGER, false en caso contrario.
     */
    private boolean isRoleRule(AuthorizationRule rule) {
        boolean isRole = rule.roles() != null && !rule.roles().isEmpty();
        logger.debug("isManagerRule for path {}: {}", rule.path(), isRole);
        return isRole;
    }
}
