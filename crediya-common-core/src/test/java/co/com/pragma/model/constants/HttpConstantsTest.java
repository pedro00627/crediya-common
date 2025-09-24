package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // HTTP Headers
                Arguments.of("AUTHORIZATION_HEADER", "Authorization"),
                Arguments.of("CONTENT_TYPE_HEADER", "Content-Type"),
                Arguments.of("ACCEPT_HEADER", "Accept"),
                Arguments.of("USER_AGENT_HEADER", "User-Agent"),

                // Security Headers
                Arguments.of("CONTENT_SECURITY_POLICY_HEADER", "Content-Security-Policy"),
                Arguments.of("X_CONTENT_TYPE_OPTIONS_HEADER", "X-Content-Type-Options"),
                Arguments.of("X_FRAME_OPTIONS_HEADER", "X-Frame-Options"),

                // Security Header Values
                Arguments.of("X_CONTENT_TYPE_NOSNIFF", "nosniff"),
                Arguments.of("X_FRAME_OPTIONS_DENY", "DENY"),
                Arguments.of("CACHE_CONTROL_NO_STORE", "no-store"),

                // Content Types
                Arguments.of("APPLICATION_JSON", "application/json"),
                Arguments.of("APPLICATION_XML", "application/xml"),
                Arguments.of("TEXT_PLAIN", "text/plain"),
                Arguments.of("TEXT_HTML", "text/html"),

                // Authentication
                Arguments.of("BEARER_PREFIX", "Bearer "),
                Arguments.of("BASIC_PREFIX", "Basic "),

                // HTTP Methods
                Arguments.of("GET_METHOD", "GET"),
                Arguments.of("POST_METHOD", "POST"),
                Arguments.of("PUT_METHOD", "PUT"),
                Arguments.of("DELETE_METHOD", "DELETE"),

                // Query Parameters
                Arguments.of("PAGE_PARAM", "page"),
                Arguments.of("SIZE_PARAM", "size"),
                Arguments.of("EMAIL_PARAM", "email")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = HttpConstants.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}