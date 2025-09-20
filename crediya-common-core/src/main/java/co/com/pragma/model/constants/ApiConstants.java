package co.com.pragma.model.constants;

/**
 * Constantes para endpoints y rutas de la API.
 * Centraliza las definiciones de URLs para mantener consistencia entre microservicios.
 */
public final class ApiConstants {

    // Base paths
    public static final String API_BASE_PATH = "/api";
    public static final String API_V1_BASE_PATH = "/api/v1";

    // Authentication endpoints
    public static final String LOGIN_ENDPOINT = "/api/v1/login";
    public static final String LOGIN_PATH = "/login";
    public static final String USERS_ENDPOINT = "/api/v1/usuarios";
    public static final String USERS_SEARCH_ENDPOINT = "/api/v1/usuarios/search";

    // Application endpoints
    public static final String APPLICATIONS_ENDPOINT = "/api/v1/solicitud";

    // Reports endpoints
    public static final String REPORTS_ENDPOINT = "/api/v1/reportes";

    // Health and monitoring
    public static final String HEALTH_ENDPOINT = "/actuator/health";
    public static final String METRICS_ENDPOINT = "/actuator/prometheus";

    // Swagger/OpenAPI endpoints
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    public static final String WEBJARS_PATH = "/webjars/**";
    public static final String API_DOCS_PATH = "/v3/api-docs/**";

    private ApiConstants() {
        // Utility class - prevent instantiation
    }
}