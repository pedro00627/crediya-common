package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryParameterConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // User-related parameters
                Arguments.of("EMAIL", "email"),
                Arguments.of("IDENTITY_DOCUMENT", "identityDocument"),
                Arguments.of("USER_ID", "userId"),

                // Application-related parameters
                Arguments.of("APPLICATION_ID", "applicationId"),
                Arguments.of("STATUS", "status"),
                Arguments.of("LOAN_TYPE", "loanType"),

                // Pagination parameters
                Arguments.of("PAGE", "page"),
                Arguments.of("SIZE", "size"),
                Arguments.of("SORT", "sort"),
                Arguments.of("DIRECTION", "direction"),

                // Date range parameters
                Arguments.of("START_DATE", "startDate"),
                Arguments.of("END_DATE", "endDate"),
                Arguments.of("DATE_FROM", "dateFrom"),
                Arguments.of("DATE_TO", "dateTo"),

                // Filtering parameters
                Arguments.of("SEARCH", "search"),
                Arguments.of("FILTER", "filter")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = QueryParameterConstants.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}