package co.com.pragma.security.api;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro web para añadir cabeceras de seguridad HTTP a las respuestas.
 * Configura cabeceras como Content-Security-Policy, Strict-Transport-Security, etc.,
 * para mejorar la seguridad de la aplicación.
 */

public class SecurityHeadersConfig implements WebFilter {

    /**
     * Constructor por defecto para SecurityHeadersConfig.
     */
    public SecurityHeadersConfig() {
        // Constructor por defecto
    }

    /**
     * Aplica las cabeceras de seguridad a la respuesta HTTP.
     *
     * @param exchange El intercambio web del servidor.
     * @param chain    La cadena de filtros web.
     * @return Un {@link Mono} que indica la finalización del procesamiento del filtro.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
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
