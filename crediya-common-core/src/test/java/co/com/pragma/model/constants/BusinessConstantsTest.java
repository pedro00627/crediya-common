package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // Salary Validation
                Arguments.of("MIN_BASE_SALARY", BigDecimal.ZERO),
                Arguments.of("MAX_BASE_SALARY", new BigDecimal("15000000")),

                // Credit Analysis
                Arguments.of("MAX_DEBT_TO_INCOME_RATIO", new BigDecimal("0.35")),
                Arguments.of("MANUAL_REVIEW_SALARY_MULTIPLIER", 5),

                // Loan Types
                Arguments.of("LIBRE_INVERSION_LOAN_TYPE", "LIBRE INVERSION"),
                Arguments.of("EDUCATIVO_LOAN_TYPE", "Educativo"),

                // Role IDs
                Arguments.of("ADMIN_ROLE_ID", 1),
                Arguments.of("ADVISOR_ROLE_ID", 2),
                Arguments.of("CLIENT_ROLE_ID", 3),

                // JWT Configuration
                Arguments.of("DEFAULT_JWT_EXPIRATION_MS", 3600000L),
                Arguments.of("JWT_ROLES_CLAIM", "roles"),

                // Pagination
                Arguments.of("DEFAULT_PAGE_SIZE", 10),
                Arguments.of("MAX_PAGE_SIZE", 100),
                Arguments.of("DEFAULT_PAGE_NUMBER", 0),

                // Cache
                Arguments.of("CACHE_STATUSES", "statuses"),
                Arguments.of("CACHE_LOAN_TYPES", "loan_types"),
                Arguments.of("CACHE_SPEC", "maximumSize=500,expireAfterAccess=500s"),

                // Monitoring
                Arguments.of("HEALTH_ENDPOINT_PATH", "health"),
                Arguments.of("PROMETHEUS_ENDPOINT_PATH", "prometheus"),
                Arguments.of("DEFAULT_TRACING_SAMPLING_PROBABILITY", 1.0),

                // CORS
                Arguments.of("DEFAULT_CORS_ORIGINS", "http://localhost:4200,http://localhost:8080"),

                // Regex
                Arguments.of("EMAIL_REGEX", "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
                Arguments.of("PHONE_REGEX", "^[+]?[0-9]{10,15}$"),
                Arguments.of("ROLE_FORMAT_REGEX", "^[A-Z_]+$")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, Object expectedValue) throws Exception {
        var field = BusinessConstants.class.getDeclaredField(fieldName);
        var actualValue = field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}