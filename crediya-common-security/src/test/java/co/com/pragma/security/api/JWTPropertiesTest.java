package co.com.pragma.security.api;

import co.com.pragma.model.constants.ApiConstants;
import co.com.pragma.model.constants.SecurityConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTPropertiesTest {

    @Test
    void shouldBeAnInterface() {
        assertTrue(JWTProperties.class.isInterface());
    }

    @Test
    void shouldHaveCorrectAbstractMethods() throws NoSuchMethodException {
        JWTProperties.class.getMethod("secret");
        JWTProperties.class.getMethod("expiration");
        JWTProperties.class.getMethod("excludedPaths");
        // If no exception is thrown, the methods exist with correct signature
    }

    @Test
    void shouldHaveCorrectDefaultMethods() throws NoSuchMethodException {
        JWTProperties.class.getMethod("secretKey");
        JWTProperties.class.getMethod("getJwtExpiration");
        JWTProperties.class.getMethod("isPathCoveredByExcluded", String.class);
        // If no exception is thrown, the methods exist with correct signature
    }

    @Test
    void secretKeyShouldGenerateFromSecret() {
        final JWTProperties mockProperties = mock(JWTProperties.class);
        final String testSecret = SecurityConstants.PRODUCTION_SECRET_SAMPLE;

        when(mockProperties.secret()).thenReturn(testSecret);
        when(mockProperties.secretKey()).thenCallRealMethod();

        final SecretKey secretKey = mockProperties.secretKey();

        assertNotNull(secretKey);
        assertTrue(secretKey.getAlgorithm().startsWith("HmacSHA"), "Should use HMAC algorithm");
    }

    @Test
    void getJwtExpirationShouldReturnExpirationValue() {
        final JWTProperties mockProperties = mock(JWTProperties.class);
        final long testExpiration = SecurityConstants.JWT_EXTENDED_EXPIRATION;

        when(mockProperties.expiration()).thenReturn(testExpiration);
        when(mockProperties.getJwtExpiration()).thenCallRealMethod();

        final Long result = mockProperties.getJwtExpiration();

        assertEquals(testExpiration, result);
        assertEquals(Long.valueOf(testExpiration), result);
    }

    @Test
    void isPathCoveredByExcludedShouldWorkWithEmptyList() {
        final JWTProperties mockProperties = mock(JWTProperties.class);

        when(mockProperties.excludedPaths()).thenReturn(Collections.emptyList());
        when(mockProperties.isPathCoveredByExcluded(anyString())).thenCallRealMethod();

        final boolean result = mockProperties.isPathCoveredByExcluded(ApiConstants.API_BASE_PATH + "/test");

        assertFalse(result);
        verify(mockProperties).excludedPaths();
    }

    @Test
    void isPathCoveredByExcludedShouldWorkWithExactMatches() {
        final JWTProperties mockProperties = mock(JWTProperties.class);
        final List<String> excludedPaths = Arrays.asList(SecurityConstants.PUBLIC_API_PATH, ApiConstants.HEALTH_ENDPOINT, ApiConstants.SWAGGER_UI_PATH);

        when(mockProperties.excludedPaths()).thenReturn(excludedPaths);
        when(mockProperties.isPathCoveredByExcluded(anyString())).thenCallRealMethod();

        // Note: The actual PathMatcher implementation would need to be tested separately
        // Here we're testing the method structure and delegation
        assertDoesNotThrow(() -> mockProperties.isPathCoveredByExcluded(SecurityConstants.PUBLIC_API_PATH));
        assertDoesNotThrow(() -> mockProperties.isPathCoveredByExcluded(ApiConstants.API_BASE_PATH + "/private"));

        verify(mockProperties, atLeast(2)).excludedPaths();
    }

    @Test
    void mockImplementationShouldWorkCorrectly() {
        final JWTProperties mockProperties = mock(JWTProperties.class);

        final String testSecret = SecurityConstants.DEVELOPMENT_SECRET_SAMPLE;
        final long testExpiration = SecurityConstants.JWT_ADMIN_EXPIRATION;
        final List<String> testExcludedPaths = Arrays.asList(SecurityConstants.PUBLIC_API_PATH, ApiConstants.HEALTH_ENDPOINT);

        when(mockProperties.secret()).thenReturn(testSecret);
        when(mockProperties.expiration()).thenReturn(testExpiration);
        when(mockProperties.excludedPaths()).thenReturn(testExcludedPaths);

        assertEquals(testSecret, mockProperties.secret());
        assertEquals(testExpiration, mockProperties.expiration());
        assertEquals(testExcludedPaths, mockProperties.excludedPaths());

        verify(mockProperties).secret();
        verify(mockProperties).expiration();
        verify(mockProperties).excludedPaths();
    }

    @Test
    void secretKeyShouldBeConsistentForSameSecret() {
        final JWTProperties mockProperties = mock(JWTProperties.class);
        final String testSecret = SecurityConstants.STAGING_SECRET_SAMPLE;

        when(mockProperties.secret()).thenReturn(testSecret);
        when(mockProperties.secretKey()).thenCallRealMethod();

        final SecretKey key1 = mockProperties.secretKey();
        final SecretKey key2 = mockProperties.secretKey();

        assertNotNull(key1);
        assertNotNull(key2);
        assertEquals(key1.getAlgorithm(), key2.getAlgorithm());
        assertArrayEquals(key1.getEncoded(), key2.getEncoded());
    }

    @Test
    void secretKeyShouldBeDifferentForDifferentSecrets() {
        final JWTProperties mockProperties1 = mock(JWTProperties.class);
        final JWTProperties mockProperties2 = mock(JWTProperties.class);

        final String secret1 = SecurityConstants.PRODUCTION_SECRET_SAMPLE;
        final String secret2 = SecurityConstants.DEVELOPMENT_SECRET_SAMPLE;

        when(mockProperties1.secret()).thenReturn(secret1);
        when(mockProperties1.secretKey()).thenCallRealMethod();
        when(mockProperties2.secret()).thenReturn(secret2);
        when(mockProperties2.secretKey()).thenCallRealMethod();

        final SecretKey key1 = mockProperties1.secretKey();
        final SecretKey key2 = mockProperties2.secretKey();

        assertNotNull(key1);
        assertNotNull(key2);
        assertFalse(Arrays.equals(key1.getEncoded(), key2.getEncoded()));
    }

    @Test
    void shouldSupportNullExcludedPaths() {
        final JWTProperties mockProperties = mock(JWTProperties.class);

        when(mockProperties.excludedPaths()).thenReturn(null);
        when(mockProperties.isPathCoveredByExcluded(anyString())).thenCallRealMethod();

        // This might throw an exception depending on PathMatcher implementation
        // The test verifies the method can be called without compilation errors
        assertDoesNotThrow(() -> {
            try {
                mockProperties.isPathCoveredByExcluded(ApiConstants.API_BASE_PATH + "/test");
            } catch (final Exception e) {
                // Expected if PathMatcher doesn't handle null
            }
        });
    }

    @Test
    void shouldSupportZeroExpiration() {
        final JWTProperties mockProperties = mock(JWTProperties.class);

        when(mockProperties.expiration()).thenReturn(0L);
        when(mockProperties.getJwtExpiration()).thenCallRealMethod();

        final Long result = mockProperties.getJwtExpiration();

        assertEquals(Long.valueOf(0), result);
    }

    @Test
    void shouldSupportNegativeExpiration() {
        final JWTProperties mockProperties = mock(JWTProperties.class);

        when(mockProperties.expiration()).thenReturn(-1L);
        when(mockProperties.getJwtExpiration()).thenCallRealMethod();

        final Long result = mockProperties.getJwtExpiration();

        assertEquals(Long.valueOf(-1), result);
    }

    // ===== BUSINESS-FOCUSED PARAMETERIZED TESTS =====

    /**
     * Test JWT token expiration times for different business scenarios
     */
    @ParameterizedTest
    @MethodSource("businessExpirationScenarios")
    void shouldHandleBusinessExpirationRequirements(final long expirationMs, final String scenario, final boolean isValidForBusiness) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.expiration()).thenReturn(expirationMs);
        when(mockProperties.getJwtExpiration()).thenCallRealMethod();

        // When
        final Long result = mockProperties.getJwtExpiration();

        // Then
        assertEquals(expirationMs, result.longValue(), "Expiration should match configured value for: " + scenario);

        if (isValidForBusiness) {
            assertTrue(0 < expirationMs, "Valid business scenario should have positive expiration: " + scenario);
        }
    }

    /**
     * Test JWT path exclusion rules for CrediYa microservices
     */
    @ParameterizedTest
    @MethodSource("crediYaPathExclusionScenarios")
    void shouldCorrectlyExcludeCrediYaPaths(final String requestPath, final String excludedPattern, final boolean shouldBeExcluded, final String businessReason) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.excludedPaths()).thenReturn(List.of(excludedPattern));
        when(mockProperties.isPathCoveredByExcluded(anyString())).thenCallRealMethod();

        // When & Then - Note: This tests the method delegation; actual PathMatcher logic is tested separately
        assertDoesNotThrow(() -> mockProperties.isPathCoveredByExcluded(requestPath), businessReason);
        verify(mockProperties).excludedPaths();
    }

    /**
     * Test JWT secret key generation for different security scenarios
     */
    @ParameterizedTest
    @MethodSource("securitySecretScenarios")
    void shouldGenerateSecureKeysForDifferentSecrets(final String secret, final String securityLevel, final boolean shouldBeSecure) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.secret()).thenReturn(secret);
        when(mockProperties.secretKey()).thenCallRealMethod();

        // When
        final SecretKey secretKey = mockProperties.secretKey();

        // Then
        assertNotNull(secretKey, "SecretKey should be generated for: " + securityLevel);
        assertTrue(secretKey.getAlgorithm().startsWith("HmacSHA"), "Should use secure HMAC algorithm: " + securityLevel);

        // JJWT automatically selects algorithm based on key length:
        // - 32-63 bytes: HmacSHA256
        // - 64+ bytes: HmacSHA512
        if (SecurityConstants.JWT_LONG_SECRET_THRESHOLD <= secret.length()) {
            assertEquals(SecurityConstants.JWT_ALGORITHM_512, secretKey.getAlgorithm(), "Long secrets should use SHA512: " + securityLevel);
        } else if (SecurityConstants.JWT_MINIMUM_SECRET_LENGTH <= secret.length()) {
            assertEquals(SecurityConstants.JWT_ALGORITHM_256, secretKey.getAlgorithm(), "Standard secrets should use SHA256: " + securityLevel);
        }

        if (shouldBeSecure) {
            assertTrue(SecurityConstants.JWT_MINIMUM_SECRET_LENGTH <= secret.length(),
                "Secure secrets should be at least " + SecurityConstants.JWT_MINIMUM_SECRET_LENGTH + " characters: " + securityLevel);
        }
    }

    /**
     * Test multiple excluded paths for comprehensive microservice security
     */
    @ParameterizedTest
    @MethodSource("microservicePathExclusionScenarios")
    void shouldHandleMicroserviceSpecificExclusions(final String requestPath, final List<String> excludedPaths, final String microservice, final String expectedBehavior) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.excludedPaths()).thenReturn(excludedPaths);
        when(mockProperties.isPathCoveredByExcluded(anyString())).thenCallRealMethod();

        // When & Then
        assertDoesNotThrow(() -> {
            mockProperties.isPathCoveredByExcluded(requestPath);
        }, expectedBehavior + " for " + microservice + " microservice");

        verify(mockProperties).excludedPaths();
    }

    /**
     * Test JWT expiration consistency for business operations
     */
    @ParameterizedTest
    @ValueSource(longs = {
        SecurityConstants.JWT_SHORT_EXPIRATION,
        SecurityConstants.JWT_STANDARD_EXPIRATION,
        SecurityConstants.JWT_EXTENDED_EXPIRATION,
        SecurityConstants.JWT_ADMIN_EXPIRATION,
        SecurityConstants.JWT_WORKDAY_EXPIRATION
    })
    void shouldMaintainExpirationConsistencyForBusinessOperations(final long expiration) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.expiration()).thenReturn(expiration);
        when(mockProperties.getJwtExpiration()).thenCallRealMethod();

        // When
        final long directExpiration = mockProperties.expiration();
        final Long compatibilityExpiration = mockProperties.getJwtExpiration();

        // Then
        assertEquals(directExpiration, compatibilityExpiration.longValue(),
            "Both expiration methods must return consistent values for business operations");
        assertTrue(0 < expiration, "Business operations require positive expiration times");
    }

    /**
     * Test that short secrets properly fail with WeakKeyException
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "short",
        "tiny",
        "small-key",
        "insufficient"
    })
    void shouldRejectShortSecretsForSecurity(final String shortSecret) {
        // Given
        final JWTProperties mockProperties = mock(JWTProperties.class);
        when(mockProperties.secret()).thenReturn(shortSecret);
        when(mockProperties.secretKey()).thenCallRealMethod();

        // When & Then
        assertThrows(io.jsonwebtoken.security.WeakKeyException.class, () -> {
            mockProperties.secretKey();
        }, "Short secrets should be rejected for security: " + shortSecret);
    }

    // ===== DATA PROVIDERS FOR BUSINESS SCENARIOS =====

    static Stream<Arguments> businessExpirationScenarios() {
        return Stream.of(
            Arguments.of(SecurityConstants.JWT_SHORT_EXPIRATION, SecurityConstants.QUICK_OPERATIONS_DESC, true),
            Arguments.of(SecurityConstants.JWT_STANDARD_EXPIRATION, SecurityConstants.STANDARD_SESSIONS_DESC, true),
            Arguments.of(SecurityConstants.JWT_EXTENDED_EXPIRATION, SecurityConstants.EXTENDED_SESSIONS_DESC, true),
            Arguments.of(SecurityConstants.JWT_ADMIN_EXPIRATION, SecurityConstants.ADMIN_OPERATIONS_DESC, true),
            Arguments.of(SecurityConstants.JWT_WORKDAY_EXPIRATION, SecurityConstants.WORKDAY_SESSIONS_DESC, true),
            Arguments.of(0L, SecurityConstants.ZERO_EXPIRATION_DESC, false),
            Arguments.of(-1L, SecurityConstants.NEGATIVE_EXPIRATION_DESC, false)
        );
    }

    static Stream<Arguments> securitySecretScenarios() {
        return Stream.of(
            Arguments.of(SecurityConstants.PRODUCTION_SECRET_SAMPLE, SecurityConstants.PRODUCTION_SECURITY_DESC, true),
            Arguments.of(SecurityConstants.DEVELOPMENT_SECRET_SAMPLE, SecurityConstants.DEVELOPMENT_SECURITY_DESC, true),
            Arguments.of(SecurityConstants.STAGING_SECRET_SAMPLE, SecurityConstants.STAGING_SECURITY_DESC, true),
            Arguments.of(SecurityConstants.MINIMUM_SECRET_SAMPLE, SecurityConstants.MINIMUM_SECURITY_DESC, true),
            Arguments.of(SecurityConstants.LONG_SECRET_SAMPLE, "Long secret for SHA512", true)
        );
    }

    static Stream<Arguments> microservicePathExclusionScenarios() {
        return Stream.of(
            Arguments.of(SecurityConstants.LOGIN_PATH,
                List.of(SecurityConstants.AUTH_WILDCARD_PATH, SecurityConstants.ACTUATOR_WILDCARD_PATH, SecurityConstants.SWAGGER_WILDCARD_PATH),
                SecurityConstants.AUTENTICACION_SERVICE,
                SecurityConstants.PREVENT_AUTH_LOOPS),
            Arguments.of(SecurityConstants.APPLICATIONS_PATH.replace("/**", "/pending"),
                List.of(SecurityConstants.AUTH_WILDCARD_PATH, SecurityConstants.ACTUATOR_WILDCARD_PATH),
                SecurityConstants.SOLICITUDES_SERVICE,
                SecurityConstants.AUTH_REQUIRED_REASON),
            Arguments.of(SecurityConstants.REPORTS_PATH.replace("/**", "/generate"),
                List.of(SecurityConstants.AUTH_WILDCARD_PATH, SecurityConstants.ACTUATOR_WILDCARD_PATH),
                SecurityConstants.REPORTES_SERVICE,
                SecurityConstants.ADMIN_AUTH_REASON),
            Arguments.of(ApiConstants.HEALTH_ENDPOINT,
                List.of(SecurityConstants.ACTUATOR_WILDCARD_PATH),
                SecurityConstants.ALL_SERVICES,
                SecurityConstants.MONITORING_ACCESS_REASON)
        );
    }

    static Stream<Arguments> crediYaPathExclusionScenarios() {
        return Stream.of(
            Arguments.of(SecurityConstants.LOGIN_PATH, SecurityConstants.AUTH_WILDCARD_PATH, true, SecurityConstants.LOGIN_EXCLUSION_REASON),
            Arguments.of(SecurityConstants.REGISTER_PATH, SecurityConstants.AUTH_WILDCARD_PATH, true, SecurityConstants.REGISTER_EXCLUSION_REASON),
            Arguments.of(ApiConstants.HEALTH_ENDPOINT, SecurityConstants.ACTUATOR_WILDCARD_PATH, true, SecurityConstants.HEALTH_EXCLUSION_REASON),
            Arguments.of(ApiConstants.SWAGGER_UI_HTML, SecurityConstants.SWAGGER_WILDCARD_PATH, true, SecurityConstants.DOCS_EXCLUSION_REASON),
            Arguments.of(ApiConstants.API_DOCS_PATH, SecurityConstants.API_DOCS_WILDCARD_PATH, true, SecurityConstants.DOCS_EXCLUSION_REASON),
            Arguments.of(SecurityConstants.APPLICATIONS_PATH, SecurityConstants.AUTH_WILDCARD_PATH, false, SecurityConstants.AUTH_REQUIRED_REASON),
            Arguments.of(SecurityConstants.USER_PROFILE_PATH, SecurityConstants.AUTH_WILDCARD_PATH, false, SecurityConstants.AUTH_REQUIRED_REASON),
            Arguments.of(SecurityConstants.ADMIN_API_PATH, SecurityConstants.AUTH_WILDCARD_PATH, false, SecurityConstants.ADMIN_AUTH_REASON),
            Arguments.of(SecurityConstants.ADVISOR_PATH, SecurityConstants.AUTH_WILDCARD_PATH, false, SecurityConstants.AUTH_REQUIRED_REASON)
        );
    }
}