package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // JWT Configuration
                Arguments.of("JWT_ALGORITHM_256", "HmacSHA256"),
                Arguments.of("JWT_ALGORITHM_512", "HmacSHA512"),
                Arguments.of("JWT_MINIMUM_SECRET_LENGTH", 32),
                Arguments.of("JWT_LONG_SECRET_THRESHOLD", 64),

                // JWT Expiration Times
                Arguments.of("JWT_SHORT_EXPIRATION", 900000L),
                Arguments.of("JWT_STANDARD_EXPIRATION", 1800000L),
                Arguments.of("JWT_EXTENDED_EXPIRATION", 3600000L),
                Arguments.of("JWT_ADMIN_EXPIRATION", 7200000L),
                Arguments.of("JWT_WORKDAY_EXPIRATION", 28800000L),

                // Authentication Paths
                Arguments.of("AUTH_BASE_PATH", "/api/auth"),
                Arguments.of("AUTH_WILDCARD_PATH", "/api/auth/**"),
                Arguments.of("LOGIN_PATH", "/api/auth/login"),
                Arguments.of("REGISTER_PATH", "/api/auth/register"),
                Arguments.of("REFRESH_TOKEN_PATH", "/api/auth/refresh"),

                // Public Access Paths
                Arguments.of("ACTUATOR_WILDCARD_PATH", "/actuator/**"),
                Arguments.of("SWAGGER_WILDCARD_PATH", "/swagger-ui/**"),
                Arguments.of("API_DOCS_WILDCARD_PATH", "/v3/api-docs/**"),
                Arguments.of("WEBJARS_WILDCARD_PATH", "/webjars/**"),
                Arguments.of("PUBLIC_API_PATH", "/api/public/**"),

                // Protected Paths
                Arguments.of("ADMIN_API_PATH", "/api/admin/**"),
                Arguments.of("USER_PROFILE_PATH", "/api/users/profile"),
                Arguments.of("APPLICATIONS_PATH", "/api/applications/**"),
                Arguments.of("REPORTS_PATH", "/api/reports/**"),
                Arguments.of("ADVISOR_PATH", "/api/advisor/**"),

                // Role Constants
                Arguments.of("ROLE_ADMIN", "ADMIN"),
                Arguments.of("ROLE_ADVISOR", "ADVISOR"),
                Arguments.of("ROLE_CLIENT", "CLIENT"),
                Arguments.of("ROLE_ANONYMOUS", "ANONYMOUS"),

                // Microservice Identifiers
                Arguments.of("AUTENTICACION_SERVICE", "Autenticacion"),
                Arguments.of("SOLICITUDES_SERVICE", "Solicitudes"),
                Arguments.of("REPORTES_SERVICE", "Reportes"),
                Arguments.of("ALL_SERVICES", "All services")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, Object expectedValue) throws Exception {
        var field = SecurityConstants.class.getDeclaredField(fieldName);
        var actualValue = field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}