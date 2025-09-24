package co.com.pragma.security.api;

import co.com.pragma.security.model.RoleConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserAuthorizationLogicTest {

    private UserAuthorizationLogic userAuthorizationLogic;

    @BeforeEach
    void setUp() {
        this.userAuthorizationLogic = new UserAuthorizationLogic();
    }

    static Stream<Arguments> authorizedRoles() {
        return Stream.of(
                Arguments.of(RoleConstants.ADMIN, "Admin should have access"),
                Arguments.of(RoleConstants.ADVISOR, "Advisor should have access")
        );
    }

    static Stream<Arguments> deniedRoles() {
        return Stream.of(
                Arguments.of(RoleConstants.CLIENT, "Client should be denied"),
                Arguments.of(RoleConstants.ROLE_ANONYMOUS, "Anonymous should be denied"),
                Arguments.of("UNKNOWN_ROLE", "Unknown role should be denied"),
                Arguments.of("admin", "Lowercase admin should be denied (case sensitive)")
        );
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("authorizedRoles")
    void shouldGrantAccessForAuthorizedRoles(String role, String description) {
        final Authentication authentication = createAuthenticatedUser(role);
        final Mono<Authentication> authMono = Mono.just(authentication);

        StepVerifier.create(userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted(), description);
                })
                .verifyComplete();
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("deniedRoles")
    void shouldDenyAccessForUnauthorizedRoles(String role, String description) {
        final Authentication authentication = createAuthenticatedUser(role);
        final Mono<Authentication> authMono = Mono.just(authentication);

        StepVerifier.create(userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted(), description);
                })
                .verifyComplete();
    }

    @Test
    void shouldGrantAccessWhenUserHasMultipleRolesIncludingAuthorized() {
        final List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(RoleConstants.CLIENT),
                new SimpleGrantedAuthority(RoleConstants.ADMIN)
        );
        final Authentication authentication = createAuthenticatedUserWithAuthorities(authorities);

        StepVerifier.create(userAuthorizationLogic.authorize(Mono.just(authentication)))
                .assertNext(decision -> assertTrue(decision.isGranted()))
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessForUnauthenticatedUser() {
        final Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        StepVerifier.create(userAuthorizationLogic.authorize(Mono.just(authentication)))
                .assertNext(decision -> assertFalse(decision.isGranted()))
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessForEmptyAuthentication() {
        StepVerifier.create(userAuthorizationLogic.authorize(Mono.empty()))
                .assertNext(decision -> assertFalse(decision.isGranted()))
                .verifyComplete();
    }

    private Authentication createAuthenticatedUser(String role) {
        return createAuthenticatedUserWithAuthorities(
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    private Authentication createAuthenticatedUserWithAuthorities(List<GrantedAuthority> authorities) {
        final Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);
        return authentication;
    }
}