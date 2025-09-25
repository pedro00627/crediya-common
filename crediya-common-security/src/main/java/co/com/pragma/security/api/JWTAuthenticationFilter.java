package co.com.pragma.security.api;

import co.com.pragma.model.log.gateways.LoggerPort;
import co.com.pragma.security.util.JWTUtil;
import co.com.pragma.security.util.PathMatcher;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * Filtro de autenticación JWT para aplicaciones web reactivas.
 * Este filtro se aplica a las rutas seguras para validar los tokens JWT.
 */

// Removed @Component annotation
public class JWTAuthenticationFilter implements WebFilter {

    /**
     * Clave para almacenar el token de autorización en el contexto reactivo.
     */

    public static final String AUTH_TOKEN_KEY = "Authorization";
    private final JWTUtil jwtUtil;
    private final LoggerPort logger;
    private final JWTProperties jwtProperties;

    /**
     * Constructor para el filtro de autenticación JWT.
     *
     * @param jwtUtil       Utilidad para el manejo de tokens JWT
     * @param logger        Puerto de logging para registrar eventos
     * @param jwtProperties Propiedades de configuración JWT
     */

    public JWTAuthenticationFilter(final JWTUtil jwtUtil, final LoggerPort logger, final JWTProperties jwtProperties) {
        this.jwtUtil = jwtUtil;
        this.logger = logger;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> filter(@NonNull final ServerWebExchange exchange, @NonNull final WebFilterChain chain) {
        final String path = exchange.getRequest().getPath().value();
        this.logger.debug("JWTAuthenticationFilter.filter() - Processing path: {}", path);

        // Verificar si la ruta está excluida de la autenticación JWT
        if (this.isPathExcluded(path)) {
            this.logger.debug("JWTAuthenticationFilter.filter() - Path {} is excluded from JWT authentication. Proceeding without authentication.", path);
            return chain.filter(exchange);
        }

        return this.extractToken(exchange)
                .doOnNext(token -> this.logger.debug("JWTAuthenticationFilter.extractToken() - Token extracted for path: {}, token length: {}", path, token.length()))
                .doOnError(e -> this.logger.warn("JWTAuthenticationFilter.extractToken() - Failed to extract token for path: {}, error: {}", path, e.getMessage()))
                .flatMap(this::validateAndCreateAuthentication)
                .doOnNext(auth -> this.logger.debug("JWTAuthenticationFilter.validateAndCreateAuthentication() - Authentication successful for user: {} with authorities: {}", auth.getName(), auth.getAuthorities()))
                .doOnError(e -> this.logger.warn("JWTAuthenticationFilter.validateAndCreateAuthentication() - Token validation failed for path: {}, error: {}", path, e.getMessage()))
                .flatMap(authentication -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                .onErrorResume(e -> this.handleAuthenticationError(e, path, exchange));
    }

    private Mono<String> extractToken(final ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    private Mono<Authentication> validateAndCreateAuthentication(final String token) {
        return Mono.fromCallable(() -> this.jwtUtil.extractAllClaims(token))
                .map(this::createAuthFromClaims)
                .flatMap(optional -> optional.map(Mono::just).orElse(Mono.empty()))
                .onErrorResume(e -> {
                    this.logger.warn("Token validation failed: {}", e.getMessage());
                    return Mono.error(e);
                });
    }

    @SuppressWarnings("unchecked")
    private Optional<Authentication> createAuthFromClaims(final Claims claims) {
        this.logger.debug("Claims extracted: Subject={}, Roles={}", claims.getSubject(), claims.get(JWTUtil.ROLES_CLAIM, List.class));

        return Optional.ofNullable(claims.getSubject())
                .flatMap(username -> {
                    final List<String> roles = claims.get(JWTUtil.ROLES_CLAIM, List.class);
                    if (null == roles || roles.isEmpty()) {
                        this.logger.warn("No roles found for user {}. Authentication will fail.", username);
                        return Optional.empty();
                    }
                    final List<SimpleGrantedAuthority> authorities = roles.stream()
                            .peek(role -> {
                                if (!role.matches("^[A-Z_]+$")) {
                                    this.logger.warn("Invalid role format: {}. Roles should contain only uppercase letters and underscores.", role);
                                }
                            })
                            .map(role -> new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role))
                            .toList();
                    final Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    this.logger.debug("Authentication object created for user {}: Authorities={}", username, authorities);
                    return Optional.of(auth);
                });
    }

    private boolean isPathExcluded(final String path) {
        return PathMatcher.matchesAny(path, this.jwtProperties.excludedPaths());
    }

    /**
     * Maneja errores de autenticación usando paradigma funcional.
     * Solo captura errores relacionados con JWT/Authentication,
     * propaga otros errores al GlobalExceptionHandler.
     */
    private Mono<Void> handleAuthenticationError(final Throwable error, final String path, final ServerWebExchange exchange) {
        return Optional.ofNullable(error.getMessage())
                .filter(this::isJwtRelatedError)
                .map(message -> {
                    this.logger.warn("JWTAuthenticationFilter.filter() - JWT authentication failed for path: {}, error: {}. Returning UNAUTHORIZED.", path, message);
                    return this.setUnauthorized(exchange);
                })
                .orElseGet(() -> {
                    this.logger.debug("JWTAuthenticationFilter.filter() - Non-JWT error for path: {}, propagating to GlobalExceptionHandler: {}", path, error.getMessage());
                    return Mono.error(error);
                });
    }

    /**
     * Verifica si el error está relacionado con JWT usando paradigma funcional.
     */
    private boolean isJwtRelatedError(final String errorMessage) {
        return List.of("JWT", "Token", "Authentication", "HMAC", "key byte array")
                .stream()
                .anyMatch(errorMessage::contains);
    }

    private Mono<Void> setUnauthorized(final ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
