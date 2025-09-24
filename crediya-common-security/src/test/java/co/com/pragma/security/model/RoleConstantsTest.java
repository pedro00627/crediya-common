package co.com.pragma.security.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                Arguments.of("ADMIN", "ADMIN"),
                Arguments.of("ADVISOR", "ADVISOR"),
                Arguments.of("CLIENT", "CLIENT"),
                Arguments.of("ROLE_ANONYMOUS", "ROLE_ANONYMOUS")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = RoleConstants.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}