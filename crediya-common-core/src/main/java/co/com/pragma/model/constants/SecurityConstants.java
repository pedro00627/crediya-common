package co.com.pragma.model.constants;

/**
 * Constantes relacionadas con seguridad, autenticación y autorización.
 * Centraliza las definiciones de rutas excluidas, roles y configuraciones de JWT.
 */
public enum SecurityConstants {
    ;

    /**
     * Algoritmo HMAC SHA-256 para firmar tokens JWT (recomendado para la mayoría de casos).
     */
    public static final String JWT_ALGORITHM_256 = "HmacSHA256";

    /**
     * Algoritmo HMAC SHA-512 para firmar tokens JWT (mayor seguridad, requiere claves más largas).
     */
    public static final String JWT_ALGORITHM_512 = "HmacSHA512";

    /**
     * Longitud mínima requerida para la clave secreta JWT (32 caracteres para SHA-256).
     */
    public static final int JWT_MINIMUM_SECRET_LENGTH = 32;

    /**
     * Umbral para considerar una clave secreta como larga (64 caracteres para SHA-512).
     */
    public static final int JWT_LONG_SECRET_THRESHOLD = 64;

    /**
     * Tiempo de expiración corto para operaciones rápidas (15 minutos).
     */
    public static final long JWT_SHORT_EXPIRATION = 900000L;

    /**
     * Tiempo de expiración estándar para sesiones de usuario regulares (30 minutos).
     */
    public static final long JWT_STANDARD_EXPIRATION = 1800000L;

    /**
     * Tiempo de expiración extendido para sesiones de usuario prolongadas (1 hora).
     */
    public static final long JWT_EXTENDED_EXPIRATION = 3600000L;

    /**
     * Tiempo de expiración para operaciones administrativas (2 horas).
     */
    public static final long JWT_ADMIN_EXPIRATION = 7200000L;

    /**
     * Tiempo de expiración para jornadas laborales completas (8 horas).
     */
    public static final long JWT_WORKDAY_EXPIRATION = 28800000L;

    /**
     * Ruta base para endpoints de autenticación (sin autenticación JWT requerida).
     */
    public static final String AUTH_BASE_PATH = "/api/auth";

    /**
     * Patrón wildcard para todas las rutas de autenticación (excluidas de JWT).
     */
    public static final String AUTH_WILDCARD_PATH = "/api/auth/**";

    /**
     * Endpoint específico para login de usuarios (debe estar excluido de JWT).
     */
    public static final String LOGIN_PATH = "/api/auth/login";

    /**
     * Endpoint específico para registro de nuevos usuarios (acceso público).
     */
    public static final String REGISTER_PATH = "/api/auth/register";

    /**
     * Endpoint para renovación de tokens JWT (requiere token válido).
     */
    public static final String REFRESH_TOKEN_PATH = "/api/auth/refresh";

    /**
     * Patrón para endpoints de Spring Boot Actuator (monitoreo y salud).
     */
    public static final String ACTUATOR_WILDCARD_PATH = "/actuator/**";

    /**
     * Patrón para recursos de la interfaz Swagger UI (documentación).
     */
    public static final String SWAGGER_WILDCARD_PATH = "/swagger-ui/**";

    /**
     * Patrón para documentación OpenAPI v3 (especificaciones de API).
     */
    public static final String API_DOCS_WILDCARD_PATH = "/v3/api-docs/**";

    /**
     * Patrón para recursos estáticos de WebJars (librerías frontend).
     */
    public static final String WEBJARS_WILDCARD_PATH = "/webjars/**";

    /**
     * Patrón para APIs públicas que no requieren autenticación.
     */
    public static final String PUBLIC_API_PATH = "/api/public/**";

    /**
     * Patrón para endpoints administrativos (requieren rol ADMIN y JWT).
     */
    public static final String ADMIN_API_PATH = "/api/admin/**";

    /**
     * Endpoint para perfil de usuario (requiere autenticación JWT).
     */
    public static final String USER_PROFILE_PATH = "/api/users/profile";

    /**
     * Patrón para endpoints de solicitudes de crédito (requieren JWT).
     */
    public static final String APPLICATIONS_PATH = "/api/applications/**";

    /**
     * Patrón para endpoints de reportes (requieren JWT y permisos específicos).
     */
    public static final String REPORTS_PATH = "/api/reports/**";

    /**
     * Patrón para endpoints de asesores (requieren rol ADVISOR y JWT).
     */
    public static final String ADVISOR_PATH = "/api/advisor/**";

    /**
     * Clave secreta de ejemplo para producción (32 caracteres, apropiada para SHA-256).
     */
    public static final String PRODUCTION_SECRET_SAMPLE = "production-hmac-key-32-characters!";

    /**
     * Clave secreta de ejemplo para desarrollo (32 caracteres, apropiada para SHA-256).
     */
    public static final String DEVELOPMENT_SECRET_SAMPLE = "development-testing-key-32-chars!";

    /**
     * Clave secreta de ejemplo para staging (32 caracteres, apropiada para SHA-256).
     */
    public static final String STAGING_SECRET_SAMPLE = "staging-environment-key-32-chars!";

    /**
     * Clave secreta mínima viable (33 caracteres, cumple requisitos básicos).
     */
    public static final String MINIMUM_SECRET_SAMPLE = "minimum-viable-secret-32-chars!!";

    /**
     * Clave secreta demasiado corta (ejemplo de configuración incorrecta).
     */
    public static final String SHORT_SECRET_SAMPLE = "short-key";

    /**
     * Clave secreta larga para SHA-512 (80+ caracteres, máxima seguridad).
     */
    public static final String LONG_SECRET_SAMPLE = "super-long-production-secret-key-with-sufficient-length-for-hmac-sha512-algorithm";

    /**
     * Rol de administrador del sistema (máximos permisos).
     */
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * Rol de asesor financiero (permisos para gestionar solicitudes).
     */
    public static final String ROLE_ADVISOR = "ADVISOR";

    /**
     * Rol de cliente (permisos básicos para sus propias solicitudes).
     */
    public static final String ROLE_CLIENT = "CLIENT";

    /**
     * Rol anónimo para usuarios no autenticados.
     */
    public static final String ROLE_ANONYMOUS = "ANONYMOUS";

    /**
     * Descripción para secretos de calidad de producción.
     */
    public static final String PRODUCTION_SECURITY_DESC = "Production-grade secret";

    /**
     * Descripción para secretos de desarrollo.
     */
    public static final String DEVELOPMENT_SECURITY_DESC = "Development secret";

    /**
     * Descripción para secretos de staging.
     */
    public static final String STAGING_SECURITY_DESC = "Staging secret";

    /**
     * Descripción para secretos con longitud insuficiente.
     */
    public static final String INSUFFICIENT_SECURITY_DESC = "Insufficient length secret";

    /**
     * Descripción para secretos con longitud mínima viable.
     */
    public static final String MINIMUM_SECURITY_DESC = "Minimum viable secret";

    /**
     * Descripción para operaciones rápidas de 15 minutos.
     */
    public static final String QUICK_OPERATIONS_DESC = "15 minutes - Quick operations";

    /**
     * Descripción para sesiones estándar de usuario de 30 minutos.
     */
    public static final String STANDARD_SESSIONS_DESC = "30 minutes - Standard user sessions";

    /**
     * Descripción para sesiones extendidas de usuario de 1 hora.
     */
    public static final String EXTENDED_SESSIONS_DESC = "1 hour - Extended user sessions";

    /**
     * Descripción para operaciones administrativas de 2 horas.
     */
    public static final String ADMIN_OPERATIONS_DESC = "2 hours - Administrative operations";

    /**
     * Descripción para sesiones de jornada laboral completa de 8 horas.
     */
    public static final String WORKDAY_SESSIONS_DESC = "8 hours - Full workday sessions";

    /**
     * Descripción para expiración cero (configuración inválida).
     */
    public static final String ZERO_EXPIRATION_DESC = "Zero expiration - Invalid for production";

    /**
     * Descripción para expiración negativa (configuración inválida).
     */
    public static final String NEGATIVE_EXPIRATION_DESC = "Negative expiration - Invalid configuration";

    /**
     * Identificador del microservicio de autenticación.
     */
    public static final String AUTENTICACION_SERVICE = "Autenticacion";

    /**
     * Identificador del microservicio de solicitudes.
     */
    public static final String SOLICITUDES_SERVICE = "Solicitudes";

    /**
     * Identificador del microservicio de reportes.
     */
    public static final String REPORTES_SERVICE = "Reportes";

    /**
     * Identificador para todos los microservicios.
     */
    public static final String ALL_SERVICES = "All services";

    /**
     * Razón por la cual el login debe estar excluido de autenticación JWT.
     */
    public static final String LOGIN_EXCLUSION_REASON = "Authentication login must be excluded";

    /**
     * Razón por la cual el registro debe estar excluido de autenticación JWT.
     */
    public static final String REGISTER_EXCLUSION_REASON = "User registration must be excluded";

    /**
     * Razón por la cual los health checks deben estar excluidos para monitoreo.
     */
    public static final String HEALTH_EXCLUSION_REASON = "Health checks must be excluded for monitoring";

    /**
     * Razón por la cual la documentación API debe ser accesible públicamente.
     */
    public static final String DOCS_EXCLUSION_REASON = "API documentation must be accessible";

    /**
     * Razón para endpoints que requieren autenticación JWT.
     */
    public static final String AUTH_REQUIRED_REASON = "Requires JWT authentication";

    /**
     * Razón específica para endpoints administrativos que requieren JWT.
     */
    public static final String ADMIN_AUTH_REASON = "Admin endpoints must require JWT authentication";

    /**
     * Razón para prevenir bucles de autenticación en endpoints de login.
     */
    public static final String PREVENT_AUTH_LOOPS = "Login endpoints must be excluded to prevent authentication loops";

    /**
     * Razón para el acceso público a endpoints de registro.
     */
    public static final String PUBLIC_ACCESS_REASON = "Registration endpoints must be excluded for public access";

    /**
     * Razón para el acceso de monitoreo a endpoints de salud.
     */
    public static final String MONITORING_ACCESS_REASON = "Health check endpoints must be accessible for monitoring";

    /**
     * Razón para endpoints de información pública sin JWT.
     */
    public static final String PUBLIC_INFO_REASON = "Public information endpoints should be accessible without JWT";

    /**
     * Razón para proteger endpoints con datos sensibles de usuario.
     */
    public static final String SENSITIVE_DATA_REASON = "User data endpoints must require JWT authentication";

}