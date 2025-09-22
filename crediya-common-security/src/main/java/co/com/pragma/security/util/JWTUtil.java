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
    public JWTUtil(final JWTProperties jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Genera un token JWT para un usuario con roles específicos.
     *
     * @param username El nombre de usuario (subject) del token.
     * @param roles    La lista de roles a incluir en el token.
     * @return El token JWT generado como una cadena de texto.
     */
    public String generateToken(final String username, final List<String> roles) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(JWTUtil.ROLES_CLAIM, roles);
        return this.createToken(claims, username);
    }

    private String createToken(final Map<String, Object> claims, final String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.jwtConfig.getJwtExpiration());

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(this.jwtConfig.secretKey())
                .compact();
    }

    /**
     * Valida un token JWT para un usuario específico.
     *
     * @param token    El token JWT a validar.
     * @param username El nombre de usuario esperado en el token.
     * @return {@code true} si el token es válido y corresponde al usuario, {@code false} en caso contrario.
     */
    public Boolean validateToken(final String token, final String username) {
        String extractedUsername = this.extractUsername(token);
        return (extractedUsername.equals(username) && !this.isTokenExpired(token));
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token El token JWT del cual extraer el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String extractUsername(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la lista de roles de un token JWT.
     *
     * @param token El token JWT del cual extraer los roles.
     * @return Una lista de cadenas que representan los roles.
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(final String token) {
        return this.extractClaim(token, claims -> claims.get(JWTUtil.ROLES_CLAIM, List.class));
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token El token JWT del cual extraer la fecha de expiración.
     * @return La fecha de expiración.
     */
    public Date extractExpiration(final String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims de un token JWT.
     *
     * @param token El token JWT del cual extraer todos los claims.
     * @return Un objeto {@link Claims} que contiene todos los claims del token.
     */
    public Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .verifyWith(this.jwtConfig.secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }
}
