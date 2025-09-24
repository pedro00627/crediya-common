package co.com.pragma.model.constants;

/**
 * Constantes para esquemas, tablas y campos de la base de datos.
 * Centraliza las definiciones para evitar errores de tipeo y facilitar refactoring.
 */
public enum DatabaseConstants {
    ;

    /**
     * Esquema de base de datos para funcionalidades de autenticación y usuarios.
     */
    public static final String AUTHENTICATION_SCHEMA = "autenticacion";

    /**
     * Esquema de base de datos para gestión de solicitudes de crédito.
     */
    public static final String APPLICATIONS_SCHEMA = "solicitudes";

    /**
     * Esquema de base de datos para generación y almacenamiento de reportes.
     */
    public static final String REPORTS_SCHEMA = "reportes";

    /**
     * Nombre de la tabla de usuarios del sistema.
     */
    public static final String USERS_TABLE = "usuarios";

    /**
     * Nombre de la tabla de roles de usuario.
     */
    public static final String ROLES_TABLE = "roles";

    /**
     * Nombre de la tabla de solicitudes de crédito.
     */
    public static final String APPLICATIONS_TABLE = "solicitudes";

    /**
     * Nombre de la tabla de estados de solicitudes.
     */
    public static final String APPLICATION_STATUS_TABLE = "estados_solicitud";

    /**
     * Nombre de la tabla de tipos de préstamos disponibles.
     */
    public static final String LOAN_TYPES_TABLE = "tipos_prestamo";

    /**
     * Columna del identificador único del usuario.
     */
    public static final String USER_ID_COLUMN = "id_usuario";

    /**
     * Columna del nombre del usuario.
     */
    public static final String USER_NAME_COLUMN = "nombre";

    /**
     * Columna del apellido del usuario.
     */
    public static final String USER_LASTNAME_COLUMN = "apellido";

    /**
     * Columna de la fecha de nacimiento del usuario.
     */
    public static final String USER_BIRTH_DATE_COLUMN = "fecha_nacimiento";

    /**
     * Columna del correo electrónico del usuario.
     */
    public static final String USER_EMAIL_COLUMN = "correo_electronico";

    /**
     * Columna del documento de identidad del usuario (cédula, pasaporte, etc.).
     */
    public static final String USER_IDENTITY_DOCUMENT_COLUMN = "documento_identidad";

    /**
     * Columna del número de teléfono del usuario.
     */
    public static final String USER_PHONE_COLUMN = "telefono";

    /**
     * Columna de la dirección de residencia del usuario.
     */
    public static final String USER_ADDRESS_COLUMN = "direccion";

    /**
     * Columna del salario base del usuario (utilizado para análisis crediticio).
     */
    public static final String USER_BASE_SALARY_COLUMN = "salario_base";

    /**
     * Columna de la contraseña encriptada del usuario.
     */
    public static final String USER_PASSWORD_COLUMN = "password";

    /**
     * Columna del identificador del rol asignado al usuario.
     */
    public static final String USER_ROLE_ID_COLUMN = "id_rol";

    /**
     * Columna del identificador único de la solicitud de crédito.
     */
    public static final String APPLICATION_ID_COLUMN = "id_solicitud";

    /**
     * Columna del monto solicitado en la aplicación de crédito.
     */
    public static final String APPLICATION_AMOUNT_COLUMN = "monto";

    /**
     * Columna del plazo en meses para el pago del crédito.
     */
    public static final String APPLICATION_TERM_COLUMN = "plazo";

    /**
     * Columna del identificador del estado actual de la solicitud.
     */
    public static final String APPLICATION_STATUS_ID_COLUMN = "id_estado";

    /**
     * Columna del identificador del tipo de préstamo solicitado.
     */
    public static final String APPLICATION_LOAN_TYPE_ID_COLUMN = "id_tipo_prestamo";

    /**
     * Columna de la fecha de creación de la solicitud.
     */
    public static final String APPLICATION_CREATION_DATE_COLUMN = "fecha_creacion";

    /**
     * Columna de la fecha de última actualización de la solicitud.
     */
    public static final String APPLICATION_UPDATE_DATE_COLUMN = "fecha_actualizacion";

    /**
     * Columna del identificador único del estado.
     */
    public static final String STATUS_ID_COLUMN = "id";

    /**
     * Columna del nombre técnico del estado.
     */
    public static final String STATUS_NAME_COLUMN = "nombre";

    /**
     * Columna de la descripción amigable del estado.
     */
    public static final String STATUS_DESCRIPTION_COLUMN = "descripcion";

    /**
     * Columna del identificador único del tipo de préstamo.
     */
    public static final String LOAN_TYPE_ID_COLUMN = "id";

    /**
     * Columna del nombre del tipo de préstamo.
     */
    public static final String LOAN_TYPE_NAME_COLUMN = "nombre";

    /**
     * Columna de la descripción del tipo de préstamo.
     */
    public static final String LOAN_TYPE_DESCRIPTION_COLUMN = "descripcion";

    /**
     * Columna de la tasa de interés aplicable al tipo de préstamo.
     */
    public static final String LOAN_TYPE_INTEREST_RATE_COLUMN = "tasa_interes";

    /**
     * Columna del monto mínimo permitido para este tipo de préstamo.
     */
    public static final String LOAN_TYPE_MIN_AMOUNT_COLUMN = "monto_minimo";

    /**
     * Columna del monto máximo permitido para este tipo de préstamo.
     */
    public static final String LOAN_TYPE_MAX_AMOUNT_COLUMN = "monto_maximo";

    /**
     * Columna que indica si el tipo de préstamo permite validación automática.
     */
    public static final String LOAN_TYPE_AUTO_VALIDATION_COLUMN = "validacion_automatica";

    /**
     * Columna del identificador único del rol.
     */
    public static final String ROLE_ID_COLUMN = "id";

    /**
     * Columna del nombre del rol.
     */
    public static final String ROLE_NAME_COLUMN = "nombre";

    /**
     * Columna de la descripción del rol y sus permisos.
     */
    public static final String ROLE_DESCRIPTION_COLUMN = "descripcion";

}