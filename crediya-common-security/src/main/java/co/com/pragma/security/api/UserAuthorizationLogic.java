package co.com.pragma.security.api;

import co.com.pragma.security.model.RoleConstants;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Contiene la lógica de negocio pura para la autorización de usuarios,
 * desacoplada de las interfaces de Spring Security.
 */
public class UserAuthorizationLogic {

    private static final Set<String> REQUIRED_ROLES = Set.of(RoleConstants.ADMIN, RoleConstants.ADVISOR);

    /**
     * Constructor por defecto para UserAuthorizationLogic.
     */
    public UserAuthorizationLogic() {
        // Constructor por defecto
    }

    /**
     * Realiza la verificación de autorización basada en la autenticación del usuario y los roles requeridos.
     *
     * @param authentication Un {@link Mono} que emite la información de autenticación del usuario.
     * @return Un {@link Mono} que emite un {@link AuthorizationDecision} indicando si la autorización es concedida o denegada.
     */
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(REQUIRED_ROLES::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
