package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConstantsTest {

    @ParameterizedTest
    @MethodSource("jwtConfigurationConstants")
    void shouldHaveCorrectJWTConfiguration(final String fieldName, final Object expectedValue, final String description) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final Object actualValue = field.get(null);
        assertEquals(expectedValue, actualValue, description);
    }

    @ParameterizedTest
    @MethodSource("jwtExpirationConstants")
    void shouldHaveValidJWTExpirationTimes(final String fieldName, final Long expectedValue, final String businessScenario) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final Long actualValue = (Long) field.get(null);
        assertEquals(expectedValue, actualValue, businessScenario);
        assertTrue(0 < actualValue, "JWT expiration must be positive for business operations: " + businessScenario);
    }

    @ParameterizedTest
    @MethodSource("authenticationPaths")
    void shouldHaveCorrectAuthenticationPaths(final String fieldName, final String expectedValue, final String pathPurpose) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue, pathPurpose);
        assertFalse(actualValue.isEmpty(), "Authentication path should not be empty: " + pathPurpose);
    }

    @ParameterizedTest
    @MethodSource("publicAccessPaths")
    void shouldHaveCorrectPublicAccessPaths(final String fieldName, final String expectedValue, final String accessReason) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue, accessReason);
        assertFalse(actualValue.isEmpty(), "Public access path should not be empty: " + accessReason);
    }

    @ParameterizedTest
    @MethodSource("protectedPaths")
    void shouldHaveCorrectProtectedPaths(final String fieldName, final String expectedValue, final String securityLevel) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue, securityLevel);
        assertFalse(actualValue.isEmpty(), "Protected path should not be empty: " + securityLevel);
    }

    @ParameterizedTest
    @MethodSource("securityTestSamples")
    void shouldHaveValidSecurityTestSamples(final String fieldName, final String businessContext) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String actualValue = (String) field.get(null);
        assertNotNull(actualValue, "Security test sample should not be null: " + businessContext);
        assertFalse(actualValue.isEmpty(), "Security test sample should not be empty: " + businessContext);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ROLE_ADMIN", "ROLE_ADVISOR", "ROLE_CLIENT", "ROLE_ANONYMOUS"
    })
    void shouldHaveValidRoleConstants(final String fieldName) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String roleValue = (String) field.get(null);
        assertFalse(roleValue.isEmpty(), "Role constant should not be empty");
        assertTrue(roleValue.matches("^[A-Z_]+$"), "Role should be uppercase: " + roleValue);
    }

    @ParameterizedTest
    @MethodSource("businessDescriptions")
    void shouldHaveCorrectBusinessDescriptions(final String fieldName, final String businessContext) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String description = (String) field.get(null);
        assertNotNull(description, "Business description should not be null: " + businessContext);
        assertFalse(description.isEmpty(), "Business description should not be empty: " + businessContext);
        assertTrue(10 < description.length(), "Business description should be meaningful: " + businessContext);
    }

    @ParameterizedTest
    @MethodSource("microserviceIdentifiers")
    void shouldHaveCorrectMicroserviceIdentifiers(final String fieldName, final String expectedValue, final String serviceContext) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue, serviceContext);
        assertFalse(actualValue.isEmpty(), "Microservice identifier should not be empty: " + serviceContext);
    }

    @ParameterizedTest
    @MethodSource("securityReasons")
    void shouldHaveCorrectSecurityReasons(final String fieldName, final String securityContext) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final String reason = (String) field.get(null);
        assertNotNull(reason, "Security reason should not be null: " + securityContext);
        assertFalse(reason.isEmpty(), "Security reason should not be empty: " + securityContext);
        assertTrue(15 < reason.length(), "Security reason should be detailed: " + securityContext);
    }

    @ParameterizedTest
    @CsvSource({
        "JWT_MINIMUM_SECRET_LENGTH, 32, Minimum JWT secret length for HMAC-SHA256",
        "JWT_SHORT_EXPIRATION, 900000, Quick operations expiration",
        "JWT_STANDARD_EXPIRATION, 1800000, Standard user sessions expiration",
        "JWT_EXTENDED_EXPIRATION, 3600000, Extended user sessions expiration",
        "JWT_ADMIN_EXPIRATION, 7200000, Administrative operations expiration",
        "JWT_WORKDAY_EXPIRATION, 28800000, Full workday sessions expiration"
    })
    void shouldHaveCorrectBusinessValues(final String fieldName, final long expectedValue, final String businessRule) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final Object actualValue = field.get(null);

        if (actualValue instanceof Integer) {
            assertEquals((int) expectedValue, (Integer) actualValue, businessRule);
        } else if (actualValue instanceof Long) {
            assertEquals(expectedValue, (Long) actualValue, businessRule);
        }

        if (fieldName.contains("EXPIRATION")) {
            assertTrue(0 < ((Number) actualValue).longValue(), "Expiration should be positive: " + businessRule);
        }
    }

    @ParameterizedTest
    @MethodSource("allSecurityConstants")
    void shouldHavePublicStaticFinalFields(final String fieldName) throws Exception {
        final Field field = SecurityConstants.class.getDeclaredField(fieldName);
        final int modifiers = field.getModifiers();

        assertTrue(Modifier.isPublic(modifiers), "Field " + fieldName + " should be public");
        assertTrue(Modifier.isStatic(modifiers), "Field " + fieldName + " should be static");
        assertTrue(Modifier.isFinal(modifiers), "Field " + fieldName + " should be final");
    }

    // Data providers for parameterized tests

    static Stream<Arguments> jwtConfigurationConstants() {
        return Stream.of(
            Arguments.of("JWT_ALGORITHM_256", "HmacSHA256", "JWT SHA256 algorithm for secure token signing"),
            Arguments.of("JWT_ALGORITHM_512", "HmacSHA512", "JWT SHA512 algorithm for secure token signing"),
            Arguments.of("JWT_MINIMUM_SECRET_LENGTH", 32, "Minimum secret length for JWT security"),
            Arguments.of("JWT_LONG_SECRET_THRESHOLD", 64, "Threshold for long secrets triggering SHA512")
        );
    }

    static Stream<Arguments> jwtExpirationConstants() {
        return Stream.of(
            Arguments.of("JWT_SHORT_EXPIRATION", 900000L, "15 minutes for quick operations"),
            Arguments.of("JWT_STANDARD_EXPIRATION", 1800000L, "30 minutes for standard user sessions"),
            Arguments.of("JWT_EXTENDED_EXPIRATION", 3600000L, "1 hour for extended user sessions"),
            Arguments.of("JWT_ADMIN_EXPIRATION", 7200000L, "2 hours for administrative operations"),
            Arguments.of("JWT_WORKDAY_EXPIRATION", 28800000L, "8 hours for full workday sessions")
        );
    }

    static Stream<Arguments> authenticationPaths() {
        return Stream.of(
            Arguments.of("AUTH_BASE_PATH", "/api/auth", "Base authentication path"),
            Arguments.of("AUTH_WILDCARD_PATH", "/api/auth/**", "Authentication wildcard path"),
            Arguments.of("LOGIN_PATH", "/api/auth/login", "User login endpoint"),
            Arguments.of("REGISTER_PATH", "/api/auth/register", "User registration endpoint"),
            Arguments.of("REFRESH_TOKEN_PATH", "/api/auth/refresh", "Token refresh endpoint")
        );
    }

    static Stream<Arguments> publicAccessPaths() {
        return Stream.of(
            Arguments.of("ACTUATOR_WILDCARD_PATH", "/actuator/**", "Monitoring and health check endpoints"),
            Arguments.of("SWAGGER_WILDCARD_PATH", "/swagger-ui/**", "API documentation endpoints"),
            Arguments.of("API_DOCS_WILDCARD_PATH", "/v3/api-docs/**", "OpenAPI specification endpoints"),
            Arguments.of("WEBJARS_WILDCARD_PATH", "/webjars/**", "Web JAR resources"),
            Arguments.of("PUBLIC_API_PATH", "/api/public/**", "Public API endpoints")
        );
    }

    static Stream<Arguments> protectedPaths() {
        return Stream.of(
            Arguments.of("ADMIN_API_PATH", "/api/admin/**", "Administrative endpoints requiring admin role"),
            Arguments.of("USER_PROFILE_PATH", "/api/users/profile", "User profile management"),
            Arguments.of("APPLICATIONS_PATH", "/api/applications/**", "Loan application endpoints"),
            Arguments.of("REPORTS_PATH", "/api/reports/**", "Business report endpoints"),
            Arguments.of("ADVISOR_PATH", "/api/advisor/**", "Advisor dashboard endpoints")
        );
    }

    static Stream<Arguments> securityTestSamples() {
        return Stream.of(
            Arguments.of("PRODUCTION_SECRET_SAMPLE", "Production environment secret"),
            Arguments.of("DEVELOPMENT_SECRET_SAMPLE", "Development environment secret"),
            Arguments.of("STAGING_SECRET_SAMPLE", "Staging environment secret"),
            Arguments.of("MINIMUM_SECRET_SAMPLE", "Minimum viable secret"),
            Arguments.of("SHORT_SECRET_SAMPLE", "Insufficient length secret sample"),
            Arguments.of("LONG_SECRET_SAMPLE", "Long secret for SHA512 algorithm")
        );
    }

    static Stream<Arguments> businessDescriptions() {
        return Stream.of(
            Arguments.of("PRODUCTION_SECURITY_DESC", "Production security description"),
            Arguments.of("DEVELOPMENT_SECURITY_DESC", "Development security description"),
            Arguments.of("STAGING_SECURITY_DESC", "Staging security description"),
            Arguments.of("INSUFFICIENT_SECURITY_DESC", "Insufficient security description"),
            Arguments.of("MINIMUM_SECURITY_DESC", "Minimum security description"),
            Arguments.of("QUICK_OPERATIONS_DESC", "Quick operations description"),
            Arguments.of("STANDARD_SESSIONS_DESC", "Standard sessions description"),
            Arguments.of("EXTENDED_SESSIONS_DESC", "Extended sessions description"),
            Arguments.of("ADMIN_OPERATIONS_DESC", "Admin operations description"),
            Arguments.of("WORKDAY_SESSIONS_DESC", "Workday sessions description"),
            Arguments.of("ZERO_EXPIRATION_DESC", "Zero expiration description"),
            Arguments.of("NEGATIVE_EXPIRATION_DESC", "Negative expiration description")
        );
    }

    static Stream<Arguments> microserviceIdentifiers() {
        return Stream.of(
            Arguments.of("AUTENTICACION_SERVICE", "Autenticacion", "Authentication microservice"),
            Arguments.of("SOLICITUDES_SERVICE", "Solicitudes", "Loan applications microservice"),
            Arguments.of("REPORTES_SERVICE", "Reportes", "Reports microservice"),
            Arguments.of("ALL_SERVICES", "All services", "All microservices identifier")
        );
    }

    static Stream<Arguments> securityReasons() {
        return Stream.of(
            Arguments.of("LOGIN_EXCLUSION_REASON", "Login endpoint exclusion"),
            Arguments.of("REGISTER_EXCLUSION_REASON", "Registration endpoint exclusion"),
            Arguments.of("HEALTH_EXCLUSION_REASON", "Health check exclusion"),
            Arguments.of("DOCS_EXCLUSION_REASON", "Documentation access"),
            Arguments.of("AUTH_REQUIRED_REASON", "Authentication required"),
            Arguments.of("ADMIN_AUTH_REASON", "Admin authentication required"),
            Arguments.of("PREVENT_AUTH_LOOPS", "Authentication loop prevention"),
            Arguments.of("PUBLIC_ACCESS_REASON", "Public access requirement"),
            Arguments.of("MONITORING_ACCESS_REASON", "Monitoring access requirement"),
            Arguments.of("PUBLIC_INFO_REASON", "Public information access"),
            Arguments.of("SENSITIVE_DATA_REASON", "Sensitive data protection")
        );
    }

    static Stream<String> allSecurityConstants() {
        return Stream.of(
            // JWT Configuration
            "JWT_ALGORITHM_256", "JWT_ALGORITHM_512", "JWT_MINIMUM_SECRET_LENGTH", "JWT_LONG_SECRET_THRESHOLD",
            // JWT Expiration Times
            "JWT_SHORT_EXPIRATION", "JWT_STANDARD_EXPIRATION", "JWT_EXTENDED_EXPIRATION",
            "JWT_ADMIN_EXPIRATION", "JWT_WORKDAY_EXPIRATION",
            // Authentication paths
            "AUTH_BASE_PATH", "AUTH_WILDCARD_PATH", "LOGIN_PATH", "REGISTER_PATH", "REFRESH_TOKEN_PATH",
            // Public access paths
            "ACTUATOR_WILDCARD_PATH", "SWAGGER_WILDCARD_PATH", "API_DOCS_WILDCARD_PATH",
            "WEBJARS_WILDCARD_PATH", "PUBLIC_API_PATH",
            // Protected paths
            "ADMIN_API_PATH", "USER_PROFILE_PATH", "APPLICATIONS_PATH", "REPORTS_PATH", "ADVISOR_PATH",
            // Security test scenarios
            "PRODUCTION_SECRET_SAMPLE", "DEVELOPMENT_SECRET_SAMPLE", "STAGING_SECRET_SAMPLE",
            "MINIMUM_SECRET_SAMPLE", "SHORT_SECRET_SAMPLE", "LONG_SECRET_SAMPLE",
            // Role constants
            "ROLE_ADMIN", "ROLE_ADVISOR", "ROLE_CLIENT", "ROLE_ANONYMOUS",
            // Security test descriptions
            "PRODUCTION_SECURITY_DESC", "DEVELOPMENT_SECURITY_DESC", "STAGING_SECURITY_DESC",
            "INSUFFICIENT_SECURITY_DESC", "MINIMUM_SECURITY_DESC",
            // Business operation descriptions
            "QUICK_OPERATIONS_DESC", "STANDARD_SESSIONS_DESC", "EXTENDED_SESSIONS_DESC",
            "ADMIN_OPERATIONS_DESC", "WORKDAY_SESSIONS_DESC", "ZERO_EXPIRATION_DESC", "NEGATIVE_EXPIRATION_DESC",
            // Microservice identifiers
            "AUTENTICACION_SERVICE", "SOLICITUDES_SERVICE", "REPORTES_SERVICE", "ALL_SERVICES",
            // Security reasons
            "LOGIN_EXCLUSION_REASON", "REGISTER_EXCLUSION_REASON", "HEALTH_EXCLUSION_REASON",
            "DOCS_EXCLUSION_REASON", "AUTH_REQUIRED_REASON", "ADMIN_AUTH_REASON",
            "PREVENT_AUTH_LOOPS", "PUBLIC_ACCESS_REASON", "MONITORING_ACCESS_REASON",
            "PUBLIC_INFO_REASON", "SENSITIVE_DATA_REASON"
        );
    }
}