package co.com.pragma.security.api;

import co.com.pragma.security.model.RoleConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAuthorizationLogicTest {

    private UserAuthorizationLogic userAuthorizationLogic;

    @BeforeEach
    void setUp() {
        this.userAuthorizationLogic = new UserAuthorizationLogic();
    }

    @Test
    void shouldHaveDefaultConstructor() {
        assertDoesNotThrow(() -> new UserAuthorizationLogic());
    }

    @Test
    void shouldGrantAccessForAdminRole() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser(RoleConstants.ADMIN);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldGrantAccessForAdvisorRole() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser(RoleConstants.ADVISOR);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessForClientRole() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser(RoleConstants.CLIENT);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldGrantAccessWhenUserHasMultipleRolesIncludingAdmin() {
        // Arrange
        final List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(RoleConstants.CLIENT),
                new SimpleGrantedAuthority(RoleConstants.ADMIN)
        );
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(authorities);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldGrantAccessWhenUserHasMultipleRolesIncludingAdvisor() {
        // Arrange
        final List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(RoleConstants.CLIENT),
                new SimpleGrantedAuthority(RoleConstants.ADVISOR)
        );
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(authorities);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessWhenUserHasOnlyClientRole() {
        // Arrange
        final List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(RoleConstants.CLIENT)
        );
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(authorities);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessForUnauthenticatedUser() {
        // Arrange
        final Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessWhenUserHasNoAuthorities() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(Collections.emptyList());
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessWhenUserHasNullAuthorities() {
        // Arrange
        final Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(null);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .expectError(NullPointerException.class)
                .verify();
    }

    @Test
    void shouldDenyAccessForUnknownRole() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser("UNKNOWN_ROLE");
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldHandleEmptyAuthenticationMono() {
        // Arrange
        final Mono<Authentication> authMono = Mono.empty();

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldGrantAccessWhenUserHasBothAdminAndAdvisorRoles() {
        // Arrange
        final List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(RoleConstants.ADMIN),
                new SimpleGrantedAuthority(RoleConstants.ADVISOR)
        );
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(authorities);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertTrue(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldDenyAccessForAnonymousRole() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser(RoleConstants.ROLE_ANONYMOUS);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnAuthorizationDecisionInstance() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser(RoleConstants.ADMIN);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertInstanceOf(AuthorizationDecision.class, decision);
                })
                .verifyComplete();
    }

    @Test
    void shouldHandleRoleWithPrefixes() {
        // Arrange
        final List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_" + RoleConstants.ADMIN),
                new SimpleGrantedAuthority("PREFIX_" + RoleConstants.ADVISOR)
        );
        final Authentication authentication = this.createAuthenticatedUserWithAuthorities(authorities);
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert - Should deny access because roles don't match exactly
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldBeCaseSeensitive() {
        // Arrange
        final Authentication authentication = this.createAuthenticatedUser("admin"); // lowercase
        final Mono<Authentication> authMono = Mono.just(authentication);

        // Act & Assert - Should deny access because it's case sensitive
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .assertNext(decision -> {
                    assertNotNull(decision);
                    assertFalse(decision.isGranted());
                })
                .verifyComplete();
    }

    @Test
    void shouldHandleMonoError() {
        // Arrange
        final Mono<Authentication> authMono = Mono.error(new RuntimeException("Authentication error"));

        // Act & Assert
        StepVerifier.create(this.userAuthorizationLogic.authorize(authMono))
                .expectError(RuntimeException.class)
                .verify();
    }

    // Helper methods

    private Authentication createAuthenticatedUser(final String role) {
        return this.createAuthenticatedUserWithAuthorities(
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    private Authentication createAuthenticatedUserWithAuthorities(final List<GrantedAuthority> authorities) {
        final Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);
        return authentication;
    }
}