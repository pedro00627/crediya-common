package co.com.pragma.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro de headers de seguridad común para todos los microservicios.
 * Aplica headers de seguridad estándar como CSP, HSTS, etc.
 */
@Component
@ConditionalOnMissingBean(name = "securityHeadersFilter")
public class SecurityHeadersFilter implements WebFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'; form-action 'self'");
        headers.set("Strict-Transport-Security", "max-age=31536000;");
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("Server", "");
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Referrer-Policy", "strict-origin-when-cross-origin");
        return chain.filter(exchange);
    }
}