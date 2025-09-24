package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationStatusConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // Status IDs
                Arguments.of("PENDING_STATUS_ID", 1),
                Arguments.of("APPROVED_STATUS_ID", 2),
                Arguments.of("REJECTED_STATUS_ID", 3),
                Arguments.of("CANCELLED_STATUS_ID", 4),
                Arguments.of("MANUAL_REVIEW_STATUS_ID", 5),

                // Status Names
                Arguments.of("PENDING_STATUS_NAME", "PENDIENTE"),
                Arguments.of("APPROVED_STATUS_NAME", "APROBADO"),
                Arguments.of("REJECTED_STATUS_NAME", "RECHAZADO"),
                Arguments.of("CANCELLED_STATUS_NAME", "CANCELADO"),
                Arguments.of("MANUAL_REVIEW_STATUS_NAME", "REVISION_MANUAL"),

                // Status Descriptions
                Arguments.of("PENDING_STATUS_DESCRIPTION", "Pendiente de revisión"),
                Arguments.of("APPROVED_STATUS_DESCRIPTION", "Aprobada"),
                Arguments.of("REJECTED_STATUS_DESCRIPTION", "Rechazada"),
                Arguments.of("CANCELLED_STATUS_DESCRIPTION", "Cancelada"),
                Arguments.of("MANUAL_REVIEW_STATUS_DESCRIPTION", "Revisión manual")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, Object expectedValue) throws Exception {
        var field = ApplicationStatusConstants.class.getDeclaredField(fieldName);
        var actualValue = field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}