package co.com.pragma.model.constants;

/**
 * Mensajes de error centralizados para todo el sistema CrediYa.
 * Proporciona consistencia en los mensajes de error entre microservicios.
 */
public enum ErrorMessages {
    ;

    /**
     * Mensaje genérico para errores internos del servidor (HTTP 500).
     */
    public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";

    /**
     * Mensaje para errores inesperados que requieren contactar soporte técnico.
     */
    public static final String UNEXPECTED_ERROR = "Ocurrió un error inesperado. Por favor, contacte al soporte.";

    /**
     * Mensaje genérico para errores de validación de datos de entrada.
     */
    public static final String VALIDATION_ERROR = "Error de validación";

    /**
     * Mensaje para cuando se viola una regla de negocio específica del sistema CrediYa.
     */
    public static final String BUSINESS_RULE_VIOLATION = "Violación de regla de negocio";

    /**
     * Mensaje para acceso no autorizado (HTTP 401).
     */
    public static final String UNAUTHORIZED_ACCESS = "Acceso no autorizado";

    /**
     * Mensaje para acceso denegado por permisos insuficientes (HTTP 403).
     */
    public static final String FORBIDDEN_ACCESS = "Acceso denegado";

    /**
     * Mensaje genérico para recursos no encontrados (HTTP 404).
     */
    public static final String RESOURCE_NOT_FOUND = "Recurso no encontrado";

    /**
     * Mensaje para credenciales de login incorrectas.
     */
    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";

    /**
     * Mensaje cuando el token JWT ha expirado y se requiere renovación.
     */
    public static final String TOKEN_EXPIRED = "El token ha expirado";

    /**
     * Mensaje para tokens JWT malformados o inválidos.
     */
    public static final String TOKEN_INVALID = "Token inválido";

    /**
     * Mensaje cuando se requiere autenticación para acceder a un recurso.
     */
    public static final String AUTHENTICATION_REQUIRED = "Autenticación requerida";

    /**
     * Mensaje cuando no se encuentra un usuario en el sistema.
     */
    public static final String USER_NOT_FOUND = "El usuario no existe";

    /**
     * Mensaje específico cuando la búsqueda de usuario por email no arroja resultados.
     */
    public static final String USER_NOT_FOUND_BY_EMAIL = "El usuario con el email especificado no existe";

    /**
     * Mensaje cuando se intenta registrar un email que ya existe en el sistema.
     */
    public static final String EMAIL_ALREADY_EXISTS = "El correo electrónico ya se encuentra registrado";

    /**
     * Mensaje cuando el parámetro email es requerido en la petición.
     */
    public static final String EMAIL_REQUIRED = "El parámetro 'email' es requerido";

    /**
     * Mensaje cuando faltan tanto email como documento para identificar al usuario.
     */
    public static final String EMAIL_OR_DOCUMENT_REQUIRED = "Falta email o documento";

    /**
     * Mensaje cuando no se encuentra una solicitud de crédito específica.
     */
    public static final String APPLICATION_NOT_FOUND = "La solicitud no existe";

    /**
     * Mensaje cuando un usuario intenta crear una nueva solicitud teniendo una activa.
     */
    public static final String USER_HAS_ACTIVE_APPLICATION = "El usuario ya tiene una solicitud de préstamo activa";

    /**
     * Mensaje para estados de solicitud no válidos o inexistentes.
     */
    public static final String INVALID_APPLICATION_STATUS = "Estado de solicitud inválido";

    /**
     * Mensaje cuando se intenta modificar una solicitud en estado terminal.
     */
    public static final String APPLICATION_CANNOT_BE_MODIFIED = "La solicitud no puede ser modificada en su estado actual";

    /**
     * Mensaje cuando el tipo de préstamo especificado no existe en el catálogo.
     */
    public static final String LOAN_TYPE_NOT_FOUND = "El tipo de préstamo especificado no existe";

    /**
     * Mensaje para montos de préstamo inválidos o fuera de rangos permitidos.
     */
    public static final String INVALID_LOAN_AMOUNT = "El monto del préstamo no es válido";

    /**
     * Mensaje para plazos de préstamo inválidos.
     */
    public static final String INVALID_LOAN_TERM = "El plazo del préstamo no es válido";

    /**
     * Mensaje específico cuando el monto está fuera del rango del tipo de préstamo.
     */
    public static final String LOAN_AMOUNT_OUT_OF_RANGE = "El monto solicitado está fuera de los límites para el tipo de préstamo seleccionado.";

    /**
     * Mensaje cuando no se encuentra un estado específico en el catálogo.
     */
    public static final String STATUS_NOT_FOUND = "El estado especificado no existe";

    /**
     * Mensaje cuando el estado inicial 'Pendiente' no está configurado correctamente.
     */
    public static final String INITIAL_STATUS_NOT_FOUND = "El estado inicial 'Pendiente' no está configurado en el sistema";

    /**
     * Mensaje cuando no se encuentra un rol específico en el sistema.
     */
    public static final String ROLE_NOT_FOUND = "El rol especificado no existe";

    /**
     * Mensaje para operaciones que requieren permisos que el usuario no posee.
     */
    public static final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes para realizar esta operación";

    /**
     * Mensaje cuando el rol del usuario no es apropiado para la operación solicitada.
     */
    public static final String INVALID_USER_ROLE = "El usuario no tiene el rol requerido para esta operación.";

    /**
     * Mensaje para formatos de email que no cumplen con la expresión regular válida.
     */
    public static final String INVALID_EMAIL_FORMAT = "El formato del correo electrónico no es válido";

    /**
     * Mensaje para salarios fuera del rango permitido (0 a 15 millones).
     */
    public static final String INVALID_SALARY_RANGE = "El salario base debe estar entre 0 y 15,000,000";

    /**
     * Mensaje genérico para campos obligatorios faltantes.
     */
    public static final String REQUIRED_FIELD_MISSING = "Campo requerido faltante";

    /**
     * Mensaje para fechas con formato incorrecto.
     */
    public static final String INVALID_DATE_FORMAT = "Formato de fecha inválido";

    /**
     * Mensaje para números de teléfono con formato incorrecto.
     */
    public static final String INVALID_PHONE_FORMAT = "Formato de teléfono inválido";

    /**
     * Mensaje de validación específico para el campo nombre requerido.
     */
    public static final String FIRST_NAME_REQUIRED = "El primer nombre no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo apellido requerido.
     */
    public static final String LAST_NAME_REQUIRED = "El apellido no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo fecha de nacimiento requerido.
     */
    public static final String BIRTH_DATE_REQUIRED = "La fecha de nacimiento no puede ser nula";

    /**
     * Mensaje de validación específico para el campo email requerido.
     */
    public static final String EMAIL_FIELD_REQUIRED = "El correo electrónico no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo documento de identidad requerido.
     */
    public static final String IDENTITY_DOCUMENT_REQUIRED = "El documento de identidad no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo teléfono requerido.
     */
    public static final String PHONE_REQUIRED = "El teléfono no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo ID de rol requerido.
     */
    public static final String ROLE_ID_REQUIRED = "El ID del rol no puede estar vacío";

    /**
     * Mensaje de validación específico para el campo salario base requerido.
     */
    public static final String BASE_SALARY_REQUIRED = "El salario base no puede ser nulo";

    /**
     * Mensaje de validación específico para el campo contraseña requerido.
     */
    public static final String PASSWORD_REQUIRED = "La contraseña no puede estar vacía";

    /**
     * Mensaje para peticiones HTTP sin cuerpo cuando es requerido.
     */
    public static final String INVALID_REQUEST_BODY = "El cuerpo de la petición no puede estar vacío";

    /**
     * Mensaje para peticiones HTTP mal formadas.
     */
    public static final String MALFORMED_REQUEST = "Solicitud mal formada";

    /**
     * Mensaje para tipos de contenido HTTP no soportados.
     */
    public static final String UNSUPPORTED_MEDIA_TYPE = "Tipo de contenido no soportado";

    /**
     * Mensaje para formatos de petición JSON inválidos.
     */
    public static final String INVALID_REQUEST_FORMAT = "El cuerpo de la petición tiene un formato inválido.";

    /**
     * Mensaje específico para fechas con formato incorrecto, con instrucciones claras.
     */
    public static final String INVALID_DATE_FORMAT_MESSAGE = "El formato de fecha es inválido. Por favor, use el formato 'YYYY-MM-DD'.";

    /**
     * Mensaje específico para tokens JWT expirados con instrucciones de renovación.
     */
    public static final String JWT_TOKEN_EXPIRED = "El token de autenticación ha expirado. Por favor, inicie sesión nuevamente.";

    /**
     * Categoría de error para entradas de datos inválidas.
     */
    public static final String INVALID_INPUT_CATEGORY = "Invalid Input";

    /**
     * Categoría de error para errores internos del servidor.
     */
    public static final String INTERNAL_SERVER_ERROR_CATEGORY = "Internal Server Error";

    /**
     * Categoría de error para violaciones de reglas de negocio.
     */
    public static final String BUSINESS_RULE_VIOLATION_CATEGORY = "Business Rule Violation";

    /**
     * Plantilla de mensaje parametrizable para emails duplicados.
     * Uso: String.format(EMAIL_ALREADY_EXISTS_TEMPLATE, email)
     */
    public static final String EMAIL_ALREADY_EXISTS_TEMPLATE = "El correo electrónico '%s' ya se encuentra registrado";

    /**
     * Plantilla de mensaje parametrizable para roles no encontrados por ID.
     * Uso: String.format(ROLE_NOT_FOUND_TEMPLATE, roleId)
     */
    public static final String ROLE_NOT_FOUND_TEMPLATE = "El rol con ID '%d' no existe";

    /**
     * Plantilla de mensaje parametrizable para usuarios no encontrados por ID.
     * Uso: String.format(USER_NOT_FOUND_BY_ID_TEMPLATE, userId)
     */
    public static final String USER_NOT_FOUND_BY_ID_TEMPLATE = "El usuario con ID '%d' no existe";

    /**
     * Plantilla de mensaje parametrizable para solicitudes no encontradas por ID.
     * Uso: String.format(APPLICATION_NOT_FOUND_BY_ID_TEMPLATE, applicationId)
     */
    public static final String APPLICATION_NOT_FOUND_BY_ID_TEMPLATE = "La solicitud con ID '%s' no existe";

}