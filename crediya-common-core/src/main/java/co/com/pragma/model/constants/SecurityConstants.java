package co.com.pragma.model.constants;

/**
 * Constantes relacionadas con seguridad, autenticación y autorización.
 * Centraliza las definiciones de rutas excluidas, roles y configuraciones de JWT.
 */
public enum SecurityConstants {
    ;

    // JWT Configuration
    public static final String JWT_ALGORITHM_256 = "HmacSHA256";
    public static final String JWT_ALGORITHM_512 = "HmacSHA512";
    public static final int JWT_MINIMUM_SECRET_LENGTH = 32;
    public static final int JWT_LONG_SECRET_THRESHOLD = 64;

    // JWT Expiration Times (in milliseconds)
    public static final long JWT_SHORT_EXPIRATION = 900000L;      // 15 minutes
    public static final long JWT_STANDARD_EXPIRATION = 1800000L;  // 30 minutes
    public static final long JWT_EXTENDED_EXPIRATION = 3600000L;  // 1 hour
    public static final long JWT_ADMIN_EXPIRATION = 7200000L;     // 2 hours
    public static final long JWT_WORKDAY_EXPIRATION = 28800000L;  // 8 hours

    // Authentication paths (excluded from JWT)
    public static final String AUTH_BASE_PATH = "/api/auth";
    public static final String AUTH_WILDCARD_PATH = "/api/auth/**";
    public static final String LOGIN_PATH = "/api/auth/login";
    public static final String REGISTER_PATH = "/api/auth/register";
    public static final String REFRESH_TOKEN_PATH = "/api/auth/refresh";

    // Public access paths (excluded from JWT)
    public static final String ACTUATOR_WILDCARD_PATH = "/actuator/**";
    public static final String SWAGGER_WILDCARD_PATH = "/swagger-ui/**";
    public static final String API_DOCS_WILDCARD_PATH = "/v3/api-docs/**";
    public static final String WEBJARS_WILDCARD_PATH = "/webjars/**";
    public static final String PUBLIC_API_PATH = "/api/public/**";

    // Protected paths (require JWT)
    public static final String ADMIN_API_PATH = "/api/admin/**";
    public static final String USER_PROFILE_PATH = "/api/users/profile";
    public static final String APPLICATIONS_PATH = "/api/applications/**";
    public static final String REPORTS_PATH = "/api/reports/**";
    public static final String ADVISOR_PATH = "/api/advisor/**";

    // Security test scenarios
    public static final String PRODUCTION_SECRET_SAMPLE = "production-hmac-key-32-characters!"; // 32 chars for SHA256
    public static final String DEVELOPMENT_SECRET_SAMPLE = "development-testing-key-32-chars!"; // 32 chars for SHA256
    public static final String STAGING_SECRET_SAMPLE = "staging-environment-key-32-chars!"; // 32 chars for SHA256
    public static final String MINIMUM_SECRET_SAMPLE = "minimum-viable-secret-32-chars!!"; // 33 chars for proper JWT
    public static final String SHORT_SECRET_SAMPLE = "short-key"; // Too short for JWT
    public static final String LONG_SECRET_SAMPLE = "super-long-production-secret-key-with-sufficient-length-for-hmac-sha512-algorithm"; // 80+ chars for SHA512

    // Role constants
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_ADVISOR = "ADVISOR";
    public static final String ROLE_CLIENT = "CLIENT";
    public static final String ROLE_ANONYMOUS = "ANONYMOUS";

    // Security test descriptions
    public static final String PRODUCTION_SECURITY_DESC = "Production-grade secret";
    public static final String DEVELOPMENT_SECURITY_DESC = "Development secret";
    public static final String STAGING_SECURITY_DESC = "Staging secret";
    public static final String INSUFFICIENT_SECURITY_DESC = "Insufficient length secret";
    public static final String MINIMUM_SECURITY_DESC = "Minimum viable secret";

    // Business operation descriptions
    public static final String QUICK_OPERATIONS_DESC = "15 minutes - Quick operations";
    public static final String STANDARD_SESSIONS_DESC = "30 minutes - Standard user sessions";
    public static final String EXTENDED_SESSIONS_DESC = "1 hour - Extended user sessions";
    public static final String ADMIN_OPERATIONS_DESC = "2 hours - Administrative operations";
    public static final String WORKDAY_SESSIONS_DESC = "8 hours - Full workday sessions";
    public static final String ZERO_EXPIRATION_DESC = "Zero expiration - Invalid for production";
    public static final String NEGATIVE_EXPIRATION_DESC = "Negative expiration - Invalid configuration";

    // Microservice identifiers
    public static final String AUTENTICACION_SERVICE = "Autenticacion";
    public static final String SOLICITUDES_SERVICE = "Solicitudes";
    public static final String REPORTES_SERVICE = "Reportes";
    public static final String ALL_SERVICES = "All services";

    // Security reasons for path exclusion
    public static final String LOGIN_EXCLUSION_REASON = "Authentication login must be excluded";
    public static final String REGISTER_EXCLUSION_REASON = "User registration must be excluded";
    public static final String HEALTH_EXCLUSION_REASON = "Health checks must be excluded for monitoring";
    public static final String DOCS_EXCLUSION_REASON = "API documentation must be accessible";
    public static final String AUTH_REQUIRED_REASON = "Requires JWT authentication";
    public static final String ADMIN_AUTH_REASON = "Admin endpoints must require JWT authentication";
    public static final String PREVENT_AUTH_LOOPS = "Login endpoints must be excluded to prevent authentication loops";
    public static final String PUBLIC_ACCESS_REASON = "Registration endpoints must be excluded for public access";
    public static final String MONITORING_ACCESS_REASON = "Health check endpoints must be accessible for monitoring";
    public static final String PUBLIC_INFO_REASON = "Public information endpoints should be accessible without JWT";
    public static final String SENSITIVE_DATA_REASON = "User data endpoints must require JWT authentication";

}