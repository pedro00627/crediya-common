package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseConstantsTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // Database Schemas
                Arguments.of("AUTHENTICATION_SCHEMA", "autenticacion"),
                Arguments.of("APPLICATIONS_SCHEMA", "solicitudes"),
                Arguments.of("REPORTS_SCHEMA", "reportes"),

                // Table Names
                Arguments.of("USERS_TABLE", "usuarios"),
                Arguments.of("ROLES_TABLE", "roles"),
                Arguments.of("APPLICATIONS_TABLE", "solicitudes"),
                Arguments.of("APPLICATION_STATUS_TABLE", "estados_solicitud"),
                Arguments.of("LOAN_TYPES_TABLE", "tipos_prestamo"),

                // User Columns
                Arguments.of("USER_ID_COLUMN", "id_usuario"),
                Arguments.of("USER_NAME_COLUMN", "nombre"),
                Arguments.of("USER_EMAIL_COLUMN", "correo_electronico"),
                Arguments.of("USER_IDENTITY_DOCUMENT_COLUMN", "documento_identidad"),
                Arguments.of("USER_PHONE_COLUMN", "telefono"),
                Arguments.of("USER_BASE_SALARY_COLUMN", "salario_base"),

                // Application Columns
                Arguments.of("APPLICATION_ID_COLUMN", "id_solicitud"),
                Arguments.of("APPLICATION_AMOUNT_COLUMN", "monto"),
                Arguments.of("APPLICATION_TERM_COLUMN", "plazo"),
                Arguments.of("APPLICATION_STATUS_ID_COLUMN", "id_estado"),

                // Status Columns
                Arguments.of("STATUS_ID_COLUMN", "id"),
                Arguments.of("STATUS_NAME_COLUMN", "nombre"),
                Arguments.of("STATUS_DESCRIPTION_COLUMN", "descripcion"),

                // Role Columns
                Arguments.of("ROLE_ID_COLUMN", "id"),
                Arguments.of("ROLE_NAME_COLUMN", "nombre"),
                Arguments.of("ROLE_DESCRIPTION_COLUMN", "descripcion")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = DatabaseConstants.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}