package co.com.pragma.model.constants;

/**
 * Mensajes de error centralizados para todo el sistema CrediYa.
 * Proporciona consistencia en los mensajes de error entre microservicios.
 */
public final class ErrorMessages {

    // Generic Error Messages
    public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";
    public static final String UNEXPECTED_ERROR = "Ocurrió un error inesperado. Por favor, contacte al soporte.";
    public static final String VALIDATION_ERROR = "Error de validación";
    public static final String BUSINESS_RULE_VIOLATION = "Violación de regla de negocio";
    public static final String UNAUTHORIZED_ACCESS = "Acceso no autorizado";
    public static final String FORBIDDEN_ACCESS = "Acceso denegado";
    public static final String RESOURCE_NOT_FOUND = "Recurso no encontrado";

    // Authentication Error Messages
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";
    public static final String TOKEN_EXPIRED = "El token ha expirado";
    public static final String TOKEN_INVALID = "Token inválido";
    public static final String AUTHENTICATION_REQUIRED = "Autenticación requerida";

    // User-related Error Messages
    public static final String USER_NOT_FOUND = "El usuario no existe";
    public static final String USER_NOT_FOUND_BY_EMAIL = "El usuario con el email especificado no existe";
    public static final String EMAIL_ALREADY_EXISTS = "El correo electrónico ya se encuentra registrado";
    public static final String EMAIL_REQUIRED = "El parámetro 'email' es requerido";
    public static final String EMAIL_OR_DOCUMENT_REQUIRED = "Falta email o documento";

    // Application-related Error Messages
    public static final String APPLICATION_NOT_FOUND = "La solicitud no existe";
    public static final String USER_HAS_ACTIVE_APPLICATION = "El usuario ya tiene una solicitud de préstamo activa";
    public static final String INVALID_APPLICATION_STATUS = "Estado de solicitud inválido";
    public static final String APPLICATION_CANNOT_BE_MODIFIED = "La solicitud no puede ser modificada en su estado actual";

    // Loan Type Error Messages
    public static final String LOAN_TYPE_NOT_FOUND = "El tipo de préstamo especificado no existe";
    public static final String INVALID_LOAN_AMOUNT = "El monto del préstamo no es válido";
    public static final String INVALID_LOAN_TERM = "El plazo del préstamo no es válido";

    // Status Error Messages
    public static final String STATUS_NOT_FOUND = "El estado especificado no existe";
    public static final String INITIAL_STATUS_NOT_FOUND = "El estado inicial 'Pendiente' no está configurado en el sistema";

    // Role Error Messages
    public static final String ROLE_NOT_FOUND = "El rol especificado no existe";
    public static final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes para realizar esta operación";

    // Validation Error Messages
    public static final String INVALID_EMAIL_FORMAT = "El formato del correo electrónico no es válido";
    public static final String INVALID_SALARY_RANGE = "El salario base debe estar entre 0 y 15,000,000";
    public static final String REQUIRED_FIELD_MISSING = "Campo requerido faltante";
    public static final String INVALID_DATE_FORMAT = "Formato de fecha inválido";
    public static final String INVALID_PHONE_FORMAT = "Formato de teléfono inválido";

    // User Request Validation Messages
    public static final String FIRST_NAME_REQUIRED = "El primer nombre no puede estar vacío";
    public static final String LAST_NAME_REQUIRED = "El apellido no puede estar vacío";
    public static final String BIRTH_DATE_REQUIRED = "La fecha de nacimiento no puede ser nula";
    public static final String EMAIL_FIELD_REQUIRED = "El correo electrónico no puede estar vacío";
    public static final String IDENTITY_DOCUMENT_REQUIRED = "El documento de identidad no puede estar vacío";
    public static final String PHONE_REQUIRED = "El teléfono no puede estar vacío";
    public static final String ROLE_ID_REQUIRED = "El ID del rol no puede estar vacío";
    public static final String BASE_SALARY_REQUIRED = "El salario base no puede ser nulo";
    public static final String PASSWORD_REQUIRED = "La contraseña no puede estar vacía";

    // HTTP-related Error Messages
    public static final String INVALID_REQUEST_BODY = "El cuerpo de la petición no puede estar vacío";
    public static final String MALFORMED_REQUEST = "Solicitud mal formada";
    public static final String UNSUPPORTED_MEDIA_TYPE = "Tipo de contenido no soportado";
    public static final String INVALID_REQUEST_FORMAT = "El cuerpo de la petición tiene un formato inválido.";
    public static final String INVALID_DATE_FORMAT_MESSAGE = "El formato de fecha es inválido. Por favor, use el formato 'YYYY-MM-DD'.";
    public static final String JWT_TOKEN_EXPIRED = "El token de autenticación ha expirado. Por favor, inicie sesión nuevamente.";

    // Error Categories
    public static final String INVALID_INPUT_CATEGORY = "Invalid Input";
    public static final String INTERNAL_SERVER_ERROR_CATEGORY = "Internal Server Error";
    public static final String BUSINESS_RULE_VIOLATION_CATEGORY = "Business Rule Violation";

    // Template Error Messages (for parameterized messages)
    public static final String EMAIL_ALREADY_EXISTS_TEMPLATE = "El correo electrónico '%s' ya se encuentra registrado";
    public static final String ROLE_NOT_FOUND_TEMPLATE = "El rol con ID '%d' no existe";
    public static final String USER_NOT_FOUND_BY_ID_TEMPLATE = "El usuario con ID '%d' no existe";
    public static final String APPLICATION_NOT_FOUND_BY_ID_TEMPLATE = "La solicitud con ID '%s' no existe";

    private ErrorMessages() {
        // Utility class - prevent instantiation
    }
}