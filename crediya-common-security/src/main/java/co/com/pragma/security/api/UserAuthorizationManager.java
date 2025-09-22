package co.com.pragma.security.api;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * Adaptador que integra la lógica de autorización de negocio con las interfaces de Spring Security.
 * Esta clase es el único punto que depende directamente de ReactiveAuthorizationManager,
 * aislando la lógica de negocio de las particularidades del framework.
 */

public class UserAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final UserAuthorizationLogic authorizationLogic;

    /**
     * Constructor por defecto para UserAuthorizationManager.
     * Inicializa la lógica de autorización de usuario.
     */
    public UserAuthorizationManager() {
        authorizationLogic = new UserAuthorizationLogic();
    }

    /**
     * Realiza la verificación de autorización para un contexto dado.
     * Delega la lógica de negocio a {@link UserAuthorizationLogic}.
     *
     * @param authentication Un {@link Mono} que emite la información de autenticación del usuario.
     * @param object         El contexto de autorización.
     * @return Un {@link Mono} que emite un {@link AuthorizationDecision} indicando si la autorización es concedida o denegada.
     */
    @Override
    public Mono<AuthorizationDecision> check(final Mono<Authentication> authentication, final AuthorizationContext object) {
        return this.authorize(authentication, object).cast(AuthorizationDecision.class);
    }

    @Override
    public Mono<AuthorizationResult> authorize(final Mono<Authentication> authentication, final AuthorizationContext object) {
        // Delega la lógica de negocio a la clase pura.
        return authorizationLogic.authorize(authentication).cast(AuthorizationResult.class);
    }
}
