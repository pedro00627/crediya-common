package co.com.pragma.model.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessagesTest {

    static Stream<Arguments> expectedValues() {
        return Stream.of(
                // Generic Error Messages
                Arguments.of("INTERNAL_SERVER_ERROR", "Error interno del servidor"),
                Arguments.of("UNEXPECTED_ERROR", "Ocurrió un error inesperado. Por favor, contacte al soporte."),
                Arguments.of("VALIDATION_ERROR", "Error de validación"),
                Arguments.of("UNAUTHORIZED_ACCESS", "Acceso no autorizado"),
                Arguments.of("FORBIDDEN_ACCESS", "Acceso denegado"),
                Arguments.of("RESOURCE_NOT_FOUND", "Recurso no encontrado"),

                // Authentication Errors
                Arguments.of("INVALID_CREDENTIALS", "Credenciales inválidas"),
                Arguments.of("TOKEN_EXPIRED", "El token ha expirado"),
                Arguments.of("TOKEN_INVALID", "Token inválido"),
                Arguments.of("AUTHENTICATION_REQUIRED", "Autenticación requerida"),

                // User-related Errors
                Arguments.of("USER_NOT_FOUND", "El usuario no existe"),
                Arguments.of("EMAIL_ALREADY_EXISTS", "El correo electrónico ya se encuentra registrado"),
                Arguments.of("EMAIL_REQUIRED", "El parámetro 'email' es requerido"),

                // Application-related Errors
                Arguments.of("APPLICATION_NOT_FOUND", "La solicitud no existe"),
                Arguments.of("INVALID_APPLICATION_STATUS", "Estado de solicitud inválido"),

                // Validation Errors
                Arguments.of("INVALID_EMAIL_FORMAT", "El formato del correo electrónico no es válido"),
                Arguments.of("INVALID_SALARY_RANGE", "El salario base debe estar entre 0 y 15,000,000"),
                Arguments.of("REQUIRED_FIELD_MISSING", "Campo requerido faltante")
        );
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("expectedValues")
    void shouldHaveExpectedValue(String fieldName, String expectedValue) throws Exception {
        var field = ErrorMessages.class.getDeclaredField(fieldName);
        var actualValue = (String) field.get(null);
        assertEquals(expectedValue, actualValue);
    }
}