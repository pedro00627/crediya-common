package co.com.pragma.security.util;

import co.com.pragma.security.api.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilidad para la generación, validación y extracción de información de tokens JWT.
 * Esta clase encapsula la lógica de interacción con la librería JJWT.
 */
@Component
public class JWTUtil {

    /**
     * Nombre del claim que contiene los roles del usuario en el token JWT.
     */
    public static final String ROLES_CLAIM = "roles";
    private final JWTProperties jwtConfig;

    /**
     * Constructor para JWTUtil.
     *
     * @param jwtConfig Configuración de JWT que contiene la clave secreta y el tiempo de expiración.
     */
    public JWTUtil(JWTProperties jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Genera un token JWT para un usuario con roles específicos.
     *
     * @param username El nombre de usuario (subject) del token.
     * @param roles    La lista de roles a incluir en el token.
     * @return El token JWT generado como una cadena de texto.
     */
    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLES_CLAIM, roles);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + jwtConfig.getJwtExpiration());

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(jwtConfig.secretKey())
                .compact();
    }

    /**
     * Valida un token JWT para un usuario específico.
     *
     * @param token    El token JWT a validar.
     * @param username El nombre de usuario esperado en el token.
     * @return {@code true} si el token es válido y corresponde al usuario, {@code false} en caso contrario.
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token El token JWT del cual extraer el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la lista de roles de un token JWT.
     *
     * @param token El token JWT del cual extraer los roles.
     * @return Una lista de cadenas que representan los roles.
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get(ROLES_CLAIM, List.class));
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token El token JWT del cual extraer la fecha de expiración.
     * @return La fecha de expiración.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims de un token JWT.
     *
     * @param token El token JWT del cual extraer todos los claims.
     * @return Un objeto {@link Claims} que contiene todos los claims del token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
