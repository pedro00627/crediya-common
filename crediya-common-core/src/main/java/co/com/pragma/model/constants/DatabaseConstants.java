package co.com.pragma.model.constants;

/**
 * Constantes para esquemas, tablas y campos de la base de datos.
 * Centraliza las definiciones para evitar errores de tipeo y facilitar refactoring.
 */
public final class DatabaseConstants {

    // Database Schemas
    public static final String AUTHENTICATION_SCHEMA = "autenticacion";
    public static final String APPLICATIONS_SCHEMA = "solicitudes";
    public static final String REPORTS_SCHEMA = "reportes";

    // Common Table Names
    public static final String USERS_TABLE = "usuarios";
    public static final String ROLES_TABLE = "roles";
    public static final String APPLICATIONS_TABLE = "solicitudes";
    public static final String APPLICATION_STATUS_TABLE = "estados_solicitud";
    public static final String LOAN_TYPES_TABLE = "tipos_prestamo";

    // User Table Columns
    public static final String USER_ID_COLUMN = "id_usuario";
    public static final String USER_NAME_COLUMN = "nombre";
    public static final String USER_LASTNAME_COLUMN = "apellido";
    public static final String USER_BIRTH_DATE_COLUMN = "fecha_nacimiento";
    public static final String USER_EMAIL_COLUMN = "correo_electronico";
    public static final String USER_IDENTITY_DOCUMENT_COLUMN = "documento_identidad";
    public static final String USER_PHONE_COLUMN = "telefono";
    public static final String USER_ADDRESS_COLUMN = "direccion";
    public static final String USER_BASE_SALARY_COLUMN = "salario_base";
    public static final String USER_PASSWORD_COLUMN = "password";
    public static final String USER_ROLE_ID_COLUMN = "id_rol";

    // Application Table Columns
    public static final String APPLICATION_ID_COLUMN = "id_solicitud";
    public static final String APPLICATION_AMOUNT_COLUMN = "monto";
    public static final String APPLICATION_TERM_COLUMN = "plazo";
    public static final String APPLICATION_STATUS_ID_COLUMN = "id_estado";
    public static final String APPLICATION_LOAN_TYPE_ID_COLUMN = "id_tipo_prestamo";
    public static final String APPLICATION_CREATION_DATE_COLUMN = "fecha_creacion";
    public static final String APPLICATION_UPDATE_DATE_COLUMN = "fecha_actualizacion";

    // Status Table Columns
    public static final String STATUS_ID_COLUMN = "id";
    public static final String STATUS_NAME_COLUMN = "nombre";
    public static final String STATUS_DESCRIPTION_COLUMN = "descripcion";

    // Loan Type Table Columns
    public static final String LOAN_TYPE_ID_COLUMN = "id";
    public static final String LOAN_TYPE_NAME_COLUMN = "nombre";
    public static final String LOAN_TYPE_DESCRIPTION_COLUMN = "descripcion";
    public static final String LOAN_TYPE_INTEREST_RATE_COLUMN = "tasa_interes";
    public static final String LOAN_TYPE_MIN_AMOUNT_COLUMN = "monto_minimo";
    public static final String LOAN_TYPE_MAX_AMOUNT_COLUMN = "monto_maximo";
    public static final String LOAN_TYPE_AUTO_VALIDATION_COLUMN = "validacion_automatica";

    // Role Table Columns
    public static final String ROLE_ID_COLUMN = "id";
    public static final String ROLE_NAME_COLUMN = "nombre";
    public static final String ROLE_DESCRIPTION_COLUMN = "descripcion";

    private DatabaseConstants() {
        // Utility class - prevent instantiation
    }
}