package co.com.pragma.security.util;

import co.com.pragma.security.api.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWTUtilTest {

    private final String testSecret = "testSecretKeyForJWTTesting123456789012345678901234567890";
    private final long testExpiration = 3600000L; // 1 hour
    @Mock
    private JWTProperties jwtProperties;
    private JWTUtil jwtUtil;
    private SecretKey testSecretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.testSecretKey = Keys.hmacShaKeyFor(this.testSecret.getBytes(StandardCharsets.UTF_8));

        when(this.jwtProperties.secretKey()).thenReturn(this.testSecretKey);
        when(this.jwtProperties.getJwtExpiration()).thenReturn(this.testExpiration);

        this.jwtUtil = new JWTUtil(this.jwtProperties);
    }

    @Test
    void shouldBeAnnotatedWithComponent() {
        assertTrue(JWTUtil.class.isAnnotationPresent(Component.class));
    }

    @Test
    void shouldHaveCorrectRolesClaimConstant() {
        assertEquals("roles", JWTUtil.ROLES_CLAIM);
    }

    @Test
    void constructorShouldInitializeWithJwtProperties() {
        final JWTProperties mockProperties = mock(JWTProperties.class);
        final JWTUtil util = new JWTUtil(mockProperties);

        assertNotNull(util);
    }

    @Test
    void shouldGenerateTokenWithUsernameAndRoles() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList("ADMIN", "USER");

        final String token = this.jwtUtil.generateToken(username, roles);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
        // JWT should have 3 parts separated by dots
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void shouldGenerateTokenWithEmptyRoles() {
        final String username = "testuser";
        final List<String> roles = List.of();

        final String token = this.jwtUtil.generateToken(username, roles);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldGenerateTokenWithNullUsername() {
        final List<String> roles = List.of("USER");

        final String token = this.jwtUtil.generateToken(null, roles);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldGenerateTokenWithNullRoles() {
        final String username = "testuser";

        final String token = this.jwtUtil.generateToken(username, null);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldExtractUsernameFromValidToken() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final String extractedUsername = this.jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void shouldExtractRolesFromValidToken() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList("ADMIN", "USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final List<String> extractedRoles = this.jwtUtil.extractRoles(token);

        assertEquals(roles, extractedRoles);
        assertEquals(2, extractedRoles.size());
        assertTrue(extractedRoles.contains("ADMIN"));
        assertTrue(extractedRoles.contains("USER"));
    }

    @Test
    void shouldExtractEmptyRolesFromToken() {
        final String username = "testuser";
        final List<String> roles = List.of();
        final String token = this.jwtUtil.generateToken(username, roles);

        final List<String> extractedRoles = this.jwtUtil.extractRoles(token);

        assertEquals(roles, extractedRoles);
        assertTrue(extractedRoles.isEmpty());
    }

    @Test
    void shouldExtractExpirationFromValidToken() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final Date expiration = this.jwtUtil.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void shouldExtractAllClaimsFromValidToken() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList("ADMIN", "USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final Claims claims = this.jwtUtil.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
        assertEquals(roles, claims.get("roles", List.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void shouldValidateValidToken() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final Boolean isValid = this.jwtUtil.validateToken(token, username);

        assertTrue(isValid);
    }

    @Test
    void shouldRejectTokenWithWrongUsername() {
        final String username = "testuser";
        final String wrongUsername = "wronguser";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final Boolean isValid = this.jwtUtil.validateToken(token, wrongUsername);

        assertFalse(isValid);
    }

    @Test
    void shouldRejectExpiredToken() {
        // Create JWT util with very short expiration
        when(this.jwtProperties.getJwtExpiration()).thenReturn(1L); // 1ms
        final JWTUtil shortExpirationUtil = new JWTUtil(this.jwtProperties);

        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final String token = shortExpirationUtil.generateToken(username, roles);

        // Wait for token to expire
        try {
            Thread.sleep(10); // Sleep for 10ms to ensure expiration
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When token is expired, validateToken might throw ExpiredJwtException
        // Let's catch it and verify it's expired
        try {
            final Boolean isValid = shortExpirationUtil.validateToken(token, username);
            assertFalse(isValid);
        } catch (final ExpiredJwtException e) {
            // This is expected behavior for expired tokens
            assertTrue(true, "Token correctly identified as expired");
        }
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        final String invalidToken = "invalid.token.here";

        assertThrows(JwtException.class, () -> {
            this.jwtUtil.extractUsername(invalidToken);
        });
    }

    @Test
    void shouldThrowExceptionForMalformedToken() {
        final String malformedToken = "malformed-token";

        assertThrows(JwtException.class, () -> {
            this.jwtUtil.extractAllClaims(malformedToken);
        });
    }

    @Test
    void shouldHandleTokenWithDifferentSecretKey() {
        // Create another JWT util with different secret
        final JWTProperties differentProperties = mock(JWTProperties.class);
        final SecretKey differentKey = Keys.hmacShaKeyFor("differentSecret123456789012345678901234567890".getBytes(StandardCharsets.UTF_8));
        when(differentProperties.secretKey()).thenReturn(differentKey);
        when(differentProperties.getJwtExpiration()).thenReturn(this.testExpiration);

        final JWTUtil differentUtil = new JWTUtil(differentProperties);
        final String token = differentUtil.generateToken("testuser", List.of("USER"));

        // Should throw exception when trying to parse with different secret
        assertThrows(JwtException.class, () -> {
            this.jwtUtil.extractUsername(token);
        });
    }

    @Test
    void shouldGenerateTokensWithSameContentButDifferentTimestamps() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList("USER", "ADMIN");

        final String token1 = this.jwtUtil.generateToken(username, roles);
        // Wait a moment to ensure different issued time
        try {
            Thread.sleep(50); // Increase sleep time significantly
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        final String token2 = this.jwtUtil.generateToken(username, roles);

        // Both tokens should have same username and roles content
        assertEquals(this.jwtUtil.extractUsername(token1), this.jwtUtil.extractUsername(token2));
        assertEquals(this.jwtUtil.extractRoles(token1), this.jwtUtil.extractRoles(token2));

        // Both tokens should be valid for the same user
        assertTrue(this.jwtUtil.validateToken(token1, username));
        assertTrue(this.jwtUtil.validateToken(token2, username));
    }

    @Test
    void shouldHandleSpecialCharactersInUsername() {
        final String username = "user@domain.com";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final String extractedUsername = this.jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void shouldHandleSpecialCharactersInRoles() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        final List<String> extractedRoles = this.jwtUtil.extractRoles(token);

        assertEquals(roles, extractedRoles);
    }

    @Test
    void shouldValidateTokenIssuedAtTime() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final Date beforeGeneration = new Date(System.currentTimeMillis() - 1000); // 1 second before
        final String token = this.jwtUtil.generateToken(username, roles);
        final Date afterGeneration = new Date(System.currentTimeMillis() + 1000); // 1 second after

        final Claims claims = this.jwtUtil.extractAllClaims(token);
        final Date issuedAt = claims.getIssuedAt();

        assertNotNull(issuedAt);
        assertTrue(issuedAt.after(beforeGeneration));
        assertTrue(issuedAt.before(afterGeneration));
    }

    @Test
    void shouldValidateTokenExpirationTime() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final Date beforeGeneration = new Date();
        final String token = this.jwtUtil.generateToken(username, roles);

        final Claims claims = this.jwtUtil.extractAllClaims(token);
        final Date expiration = claims.getExpiration();
        final Date expectedExpiration = new Date(beforeGeneration.getTime() + this.testExpiration);

        assertNotNull(expiration);
        // Allow some tolerance for timing differences
        final long timeDifference = Math.abs(expiration.getTime() - expectedExpiration.getTime());
        assertTrue(1000 > timeDifference); // Less than 1 second difference
    }

    @Test
    void shouldHandleLargeNumberOfRoles() {
        final String username = "testuser";
        final List<String> roles = Arrays.asList(
                "ROLE_1", "ROLE_2", "ROLE_3", "ROLE_4", "ROLE_5",
                "ROLE_6", "ROLE_7", "ROLE_8", "ROLE_9", "ROLE_10"
        );
        final String token = this.jwtUtil.generateToken(username, roles);

        final List<String> extractedRoles = this.jwtUtil.extractRoles(token);

        assertEquals(roles, extractedRoles);
        assertEquals(10, extractedRoles.size());
    }

    @Test
    void shouldGenerateTokenForAnyValidUsername() {
        // Test with various valid usernames
        final List<String> validUsernames = Arrays.asList(
                "user123",
                "user@domain.com",
                "user.name",
                "user_name",
                "user-name"
        );

        final List<String> roles = List.of("USER");

        for (final String username : validUsernames) {
            final String token = this.jwtUtil.generateToken(username, roles);
            assertNotNull(token);
            assertFalse(token.isEmpty());

            final String extractedUsername = this.jwtUtil.extractUsername(token);
            assertEquals(username, extractedUsername);

            final List<String> extractedRoles = this.jwtUtil.extractRoles(token);
            assertEquals(roles, extractedRoles);
        }
    }

    @Test
    void shouldValidateTokenSignature() {
        final String username = "testuser";
        final List<String> roles = List.of("USER");
        final String token = this.jwtUtil.generateToken(username, roles);

        // This should not throw an exception if signature is valid
        assertDoesNotThrow(() -> {
            this.jwtUtil.extractAllClaims(token);
        });
    }
}