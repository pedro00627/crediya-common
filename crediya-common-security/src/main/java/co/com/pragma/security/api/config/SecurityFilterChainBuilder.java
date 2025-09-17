package co.com.pragma.security.api.config;

import co.com.pragma.model.log.gateways.LoggerPort;
import co.com.pragma.security.api.JWTProperties;
import co.com.pragma.security.api.SecurityRulesProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Componente reutilizable para construir dinámicamente las reglas de autorización
 * de Spring Security a partir de un conjunto de propiedades.
 * <p>
 * Utiliza un enfoque funcional y basado en estrategias para aplicar las reglas
 * definidas en SecurityRulesProvider al {@link ServerHttpSecurity}.
 */
@Component
public class SecurityFilterChainBuilder {

    private final ApplicationContext applicationContext;
    private final AuthorizationRuleTypeResolver ruleTypeResolver;
    private final LoggerPort logger; // Inyectar LoggerPort

    // --- Propiedades y Resolvers de Reglas ---
    private final Map<AuthorizationRuleType, BiConsumer<ServerHttpSecurity.AuthorizeExchangeSpec, AuthorizationRule>> authorizationStrategies;

    /**
     * Constructor para SecurityFilterChainBuilder.
     *
     * @param applicationContext El contexto de la aplicación para resolver beans dinámicamente.
     * @param ruleTypeResolver   El resolver de tipos de reglas de autorización.
     * @param logger             El puerto de logging para registrar eventos.
     */
    public SecurityFilterChainBuilder(ApplicationContext applicationContext,
                                      AuthorizationRuleTypeResolver ruleTypeResolver,
                                      LoggerPort logger) {
        this.applicationContext = applicationContext;
        this.ruleTypeResolver = ruleTypeResolver;
        this.logger = logger;

        this.authorizationStrategies = Map.of(
                AuthorizationRuleType.MANAGER, this::applyManagerAuthorization,
                AuthorizationRuleType.ROLE, this::applyRoleAuthorization,
                AuthorizationRuleType.DENY_ALL, this::applyDenyAllAuthorization,
                AuthorizationRuleType.PERMIT_ALL, this::applyPermitAllAuthorization
        );
    }


    // Método actualizado usando los métodos del record
    private List<AuthorizationRule> createAutomaticRulesFromJwtExcludedPaths(JWTProperties jwtProperties) {
        logger.debug("Creating automatic ANONYMOUS rules from JWT excluded-paths: {}",
                jwtProperties.excludedPaths());

        return jwtProperties.excludedPaths().stream()
                .map(path -> new AuthorizationRule(null, path, null, List.of("ROLE_ANONYMOUS")))
                .collect(Collectors.toList());
    }

    // Método auxiliar actualizado
    private boolean isPathCoveredByJwtExcluded(String rulePath, JWTProperties jwtProperties) {
        return jwtProperties.isPathCoveredByExcluded(rulePath);
    }
    // --- Estrategias de Autorización --- //

    private void applyManagerAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec, AuthorizationRule rule) {
        logger.debug("Applying MANAGER authorization for path: {}", rule.path());
        ReactiveAuthorizationManager<AuthorizationContext> manager = resolveAuthorizationManager(rule.managerBeanName());
        getAccessConfigurer(authorizeExchangeSpec, rule).access(manager);
    }

    private void applyDenyAllAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec, AuthorizationRule rule) {
        logger.debug("Applying DENY_ALL authorization for path: {}", rule.path());
        getAccessConfigurer(authorizeExchangeSpec, rule).denyAll();
    }

    private void applyPermitAllAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec, AuthorizationRule authorizationRule) {
        logger.debug("Applying PERMIT_ALL authorization for path: {}", authorizationRule.path());
        getAccessConfigurer(authorizeExchangeSpec, authorizationRule).permitAll();
    }

    private void applyRoleAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec, AuthorizationRule rule) {
        logger.debug("Applying ROLE authorization for path: {}", rule.path());

        if (rule.roles() == null || rule.roles().isEmpty()) {
            logger.warn("No roles specified for ROLE authorization on path: {}. Applying denyAll for security.", rule.path());
            getAccessConfigurer(authorizeExchangeSpec, rule).denyAll();
            return;
        }

        // Convertir la lista de roles a array, eliminando el prefijo ROLE_ si existe
        String[] roleArray = rule.roles().stream()
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .toArray(String[]::new);

        logger.debug("Applying roles: {} for path: {}", Arrays.toString(roleArray), rule.path());

        // Caso especial para ANONYMOUS - permitir acceso sin autenticación
        if (roleArray.length == 1 && "ANONYMOUS".equalsIgnoreCase(roleArray[0])) {
            logger.debug("Applying anonymous access for path: {}", rule.path());
            getAccessConfigurer(authorizeExchangeSpec, rule).permitAll();
            return;
        }

        // Aplicar autorización por roles normales
        if (roleArray.length == 1) {
            getAccessConfigurer(authorizeExchangeSpec, rule).hasRole(roleArray[0]);
        } else {
            getAccessConfigurer(authorizeExchangeSpec, rule).hasAnyRole(roleArray);
        }
    }

    /**
     * Aplica las reglas de autorización al ServerHttpSecurity.AuthorizeExchangeSpec basándose en las propiedades proporcionadas.
     * Itera sobre cada regla y aplica la estrategia de autorización correspondiente.
     *
     * @param exchanges             El objeto ServerHttpSecurity.AuthorizeExchangeSpec a configurar.
     * @param securityRulesProvider El proveedor que contiene las reglas de autorización.
     * @param jwtProperties         Las propiedades JWT que incluyen las rutas excluidas.
     */
    public void applyAuthorizationRules(
            ServerHttpSecurity.AuthorizeExchangeSpec exchanges,
            SecurityRulesProvider securityRulesProvider,
            JWTProperties jwtProperties) {

        logger.info("Applying integrated authorization rules. JWT excluded-paths: {}, Security rules: {}",
                jwtProperties.excludedPaths().size(),
                securityRulesProvider.authorization().size());

        // 1. Primero aplicar reglas automáticas desde JWT excluded-paths
        List<AuthorizationRule> jwtAutomaticRules = createAutomaticRulesFromJwtExcludedPaths(jwtProperties);
        Set<String> jwtExcludedPaths = new HashSet<>();

        jwtAutomaticRules.forEach(rule -> {
            logger.debug("Applying automatic JWT excluded rule: Path={}", rule.path());
            jwtExcludedPaths.add(rule.path());
            AuthorizationRuleType ruleType = ruleTypeResolver.determineRuleType(rule);
            authorizationStrategies.get(ruleType).accept(exchanges, rule);
        });

        // 2. Luego aplicar reglas explícitas (pero evitar duplicados)
        securityRulesProvider.authorization().forEach(rule -> {
            // Verificar si esta ruta ya fue procesada por JWT excluded-paths
            boolean alreadyProcessedByJwt = jwtExcludedPaths.contains(rule.path()) ||
                    isPathCoveredByJwtExcluded(rule.path(), jwtProperties);

            if (alreadyProcessedByJwt) {
                logger.warn("Rule for path {} conflicts with JWT excluded-paths. " +
                        "JWT excluded-paths take precedence. Skipping explicit rule.", rule.path());
                return;
            }

            logger.debug("Processing explicit rule: Path={}, Method={}, Manager={}, Roles={}",
                    rule.path(), rule.method(), rule.managerBeanName(), rule.roles());

            AuthorizationRuleType ruleType = ruleTypeResolver.determineRuleType(rule);
            logger.debug("Rule for Path {} resolved to type: {}", rule.path(), ruleType);
            authorizationStrategies.get(ruleType).accept(exchanges, rule);
        });

        // 3. Regla de fallback
        exchanges.anyExchange().authenticated();
        logger.info("Authorization rules applied. Total JWT excluded: {}, Total explicit: {}",
                jwtAutomaticRules.size(), securityRulesProvider.authorization().size());
    }

    private ServerHttpSecurity.AuthorizeExchangeSpec.Access getAccessConfigurer(
            ServerHttpSecurity.AuthorizeExchangeSpec exchanges, AuthorizationRule rule) {
        logger.debug("SecurityFilterChainBuilder.getAccessConfigurer() - Processing rule: path={}, method={}", rule.path(), rule.method());
        return Optional.ofNullable(rule.method())
                .map(method -> {
                    logger.debug("SecurityFilterChainBuilder.getAccessConfigurer() - Using method-specific matcher: {} {}", method, rule.path());
                    return exchanges.pathMatchers(method, rule.path());
                })
                .orElseGet(() -> {
                    logger.debug("SecurityFilterChainBuilder.getAccessConfigurer() - Using path-only matcher: {}", rule.path());
                    return exchanges.pathMatchers(rule.path());
                });
    }

    /**
     * Resuelve un bean de {@link ReactiveAuthorizationManager} del contexto de la aplicación por su nombre.
     *
     * @param beanName El nombre del bean del gestor de autorización.
     * @return La instancia del {@link ReactiveAuthorizationManager}.
     * @throws IllegalArgumentException si el nombre del bean es nulo/vacío o el bean no puede ser resuelto.
     */
    @SuppressWarnings("unchecked")
    private ReactiveAuthorizationManager<AuthorizationContext> resolveAuthorizationManager(String beanName) {
        // Ahora siempre obtenemos el bean del ApplicationContext
        return applicationContext.getBean(beanName, ReactiveAuthorizationManager.class);
    }
}
