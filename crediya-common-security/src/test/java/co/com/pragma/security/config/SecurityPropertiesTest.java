package co.com.pragma.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests críticos de configuración JWT para todos los microservicios.
 * Verifica reglas de negocio de seguridad y configuraciones válidas.
 */
class SecurityPropertiesTest {

    private SecurityProperties securityProperties;

    static Stream<Arguments> businessCriticalJwtScenarios() {
        return Stream.of(
                Arguments.of("Business JWT scenarios",
                        "mySecureJWTKeyForProduction2024", 3600000L,
                        List.of("/auth/login", "/auth/register"),
                        "Standard business configuration"),
                Arguments.of("Admin configuration",
                        "adminSecretKey2024", 7200000L,
                        List.of("/admin/**", "/management/**"),
                        "Extended admin session"),
                Arguments.of("Client configuration",
                        "clientAccessKey2024", 1800000L,
                        List.of("/public/**", "/health"),
                        "Client restricted access"),
                Arguments.of("Development environment",
                        "devSecretKey", 86400000L,
                        List.of("/swagger-ui/**", "/v3/api-docs/**"),
                        "Development with docs access")
        );
    }

    static Stream<Arguments> securityValidationScenarios() {
        return Stream.of(
                Arguments.of("Minimum security requirements",
                        "minimumSecureKey123456", 300000L, true,
                        "Should meet minimum security standards"),
                Arguments.of("Production security requirements",
                        "productionSecureKeyWithComplexity2024!", 3600000L, true,
                        "Production should have strong security"),
                Arguments.of("Short-lived tokens for sensitive operations",
                        "sensitiveOperationKey", 60000L, true,
                        "Sensitive operations need short expiration"),
                Arguments.of("Long-lived tokens for batch operations",
                        "batchProcessingKey2024", 28800000L, true,
                        "Batch operations may need extended time")
        );
    }

    @BeforeEach
    void setUp() {
        securityProperties = new SecurityProperties();
    }

    @Test
    void shouldInitializeWithSecureDefaults() {
        // Assert - Verify secure initialization
        assertNotNull(securityProperties.getExcludedPaths(),
                "Excluded paths should never be null for security");
        assertTrue(securityProperties.getExcludedPaths().isEmpty(),
                "Should start with empty excluded paths for maximum security");
        assertEquals(0L, securityProperties.getExpiration(),
                "Should start with zero expiration requiring explicit configuration");
    }

    @ParameterizedTest
    @MethodSource("businessCriticalJwtScenarios")
    void shouldHandleBusinessCriticalJwtConfigurations(String scenario, String secret,
                                                      long expiration, List<String> excludedPaths,
                                                      String businessContext) {
        // Arrange & Act - Configure for business scenario
        securityProperties.setSecret(secret);
        securityProperties.setExpiration(expiration);
        securityProperties.setExcludedPaths(excludedPaths);

        // Assert - Verify business configuration is preserved
        assertEquals(secret, securityProperties.secret(),
                businessContext + " - Secret should be preserved");
        assertEquals(expiration, securityProperties.expiration(),
                businessContext + " - Expiration should be preserved");
        assertEquals(excludedPaths, securityProperties.excludedPaths(),
                businessContext + " - Excluded paths should be preserved");

        // Verify interface consistency
        assertEquals(securityProperties.getSecret(), securityProperties.secret(),
                businessContext + " - Interface should match getter");
    }

    @ParameterizedTest
    @MethodSource("securityValidationScenarios")
    void shouldValidateSecurityConfiguration(String scenario, String secret, long expiration,
                                           boolean isValid, String securityRequirement) {
        // Act
        securityProperties.setSecret(secret);
        securityProperties.setExpiration(expiration);

        // Assert - Verify security properties are set correctly
        if (isValid) {
            assertNotNull(securityProperties.secret(), securityRequirement);
            assertTrue(securityProperties.expiration() > 0 ||
                      securityProperties.expiration() == expiration, securityRequirement);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/actuator/health",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    })
    void shouldSupportCommonBusinessEndpoints(String businessEndpoint) {
        // Arrange - Set up common business excluded paths
        List<String> businessPaths = List.of(businessEndpoint);

        // Act
        securityProperties.setExcludedPaths(businessPaths);

        // Assert
        assertTrue(securityProperties.excludedPaths().contains(businessEndpoint),
                "Should support common business endpoint: " + businessEndpoint);
    }

    @Test
    void shouldHandleNullExcludedPathsSecurely() {
        // Act - Test null handling for security
        securityProperties.setExcludedPaths(null);

        // Assert - Should create secure empty list, never null
        assertNotNull(securityProperties.getExcludedPaths(),
                "Excluded paths should never be null for security scanning");
        assertTrue(securityProperties.getExcludedPaths().isEmpty(),
                "Should default to empty (secure) when null provided");
    }

    @Test
    void shouldMaintainSecurityInterfaceContract() {
        // Arrange - Business configuration
        final String businessSecret = "businessSecretKey2024";
        final long businessExpiration = 3600000L;
        final List<String> businessPaths = List.of("/auth/**", "/public/**");

        // Act
        securityProperties.setSecret(businessSecret);
        securityProperties.setExpiration(businessExpiration);
        securityProperties.setExcludedPaths(businessPaths);

        // Assert - Verify JWTProperties interface contract
        assertEquals(businessSecret, securityProperties.secret(),
                "Interface secret() should match business configuration");
        assertEquals(businessExpiration, securityProperties.expiration(),
                "Interface expiration() should match business configuration");
        assertEquals(businessPaths, securityProperties.excludedPaths(),
                "Interface excludedPaths() should match business configuration");
    }
}