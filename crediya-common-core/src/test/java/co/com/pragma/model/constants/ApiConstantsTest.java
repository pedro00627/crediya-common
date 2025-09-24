package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                Arguments.of("API_BASE_PATH", "/api"),
                Arguments.of("API_V1_BASE_PATH", "/api/v1"),
                Arguments.of("LOGIN_ENDPOINT", "/api/v1/login"),
                Arguments.of("LOGIN_PATH", "/login"),
                Arguments.of("USERS_ENDPOINT", "/api/v1/usuarios"),
                Arguments.of("USERS_SEARCH_ENDPOINT", "/api/v1/usuarios/search"),
                Arguments.of("APPLICATIONS_ENDPOINT", "/api/v1/solicitud"),
                Arguments.of("REPORTS_ENDPOINT", "/api/v1/reportes"),
                Arguments.of("HEALTH_ENDPOINT", "/actuator/health"),
                Arguments.of("METRICS_ENDPOINT", "/actuator/prometheus"),
                Arguments.of("SWAGGER_UI_HTML", "/swagger-ui.html"),
                Arguments.of("SWAGGER_UI_PATH", "/swagger-ui/**"),
                Arguments.of("WEBJARS_PATH", "/webjars/**"),
                Arguments.of("API_DOCS_PATH", "/v3/api-docs/**")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = ApiConstants.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}