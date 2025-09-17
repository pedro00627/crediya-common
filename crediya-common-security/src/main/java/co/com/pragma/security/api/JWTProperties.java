package co.com.pragma.security.api;

import co.com.pragma.security.util.PathMatcher;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Interfaz que define las propiedades necesarias para la configuración JWT.
 * Cada microservicio debe implementar esta interfaz para proporcionar
 * su configuración específica de JWT.
 */
public interface JWTProperties {

    /**
     * Obtiene el secreto JWT utilizado para la firma de tokens.
     *
     * @return El secreto JWT para la firma de tokens
     */
    String secret();

    /**
     * Obtiene el tiempo de expiración del token JWT.
     *
     * @return El tiempo de expiración del token en milisegundos
     */
    long expiration();

    /**
     * Obtiene la lista de rutas que están excluidas de la autenticación JWT.
     *
     * @return Lista de rutas excluidas de la autenticación JWT
     */
    List<String> excludedPaths();

    /**
     * Genera y obtiene la clave secreta para JWT basada en el secreto configurado.
     *
     * @return La clave secreta generada para JWT
     */
    default SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Obtiene el tiempo de expiración del JWT en formato Long para compatibilidad.
     *
     * @return El tiempo de expiración del JWT (compatibilidad)
     */
    default Long getJwtExpiration() {
        return expiration();
    }

    /**
     * Verifica si una ruta está cubierta por las rutas excluidas.
     *
     * @param rulePath La ruta a verificar
     * @return true si la ruta está excluida
     */
    default boolean isPathCoveredByExcluded(String rulePath) {
        return PathMatcher.matchesAny(rulePath, excludedPaths());
    }
}
