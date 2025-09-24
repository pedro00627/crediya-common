package co.com.pragma.model.constants;

/**
 * Constantes para endpoints y rutas de la API.
 * Centraliza las definiciones de URLs para mantener consistencia entre microservicios.
 */
public enum ApiConstants {
    ;

    /**
     * Ruta base para todos los endpoints de la API.
     */
    public static final String API_BASE_PATH = "/api";

    /**
     * Ruta base para la versión 1 de la API.
     */
    public static final String API_V1_BASE_PATH = "/api/v1";

    /**
     * Endpoint completo para el login de usuarios.
     */
    public static final String LOGIN_ENDPOINT = "/api/v1/login";

    /**
     * Ruta relativa para el login de usuarios.
     */
    public static final String LOGIN_PATH = "/login";

    /**
     * Endpoint para la gestión de usuarios.
     */
    public static final String USERS_ENDPOINT = "/api/v1/usuarios";

    /**
     * Endpoint para la búsqueda de usuarios.
     */
    public static final String USERS_SEARCH_ENDPOINT = "/api/v1/usuarios/search";

    /**
     * Endpoint para la gestión de solicitudes de crédito.
     */
    public static final String APPLICATIONS_ENDPOINT = "/api/v1/solicitud";

    /**
     * Endpoint para la generación y consulta de reportes.
     */
    public static final String REPORTS_ENDPOINT = "/api/v1/reportes";

    /**
     * Endpoint para verificar el estado de salud de la aplicación.
     */
    public static final String HEALTH_ENDPOINT = "/actuator/health";

    /**
     * Endpoint para obtener métricas de Prometheus.
     */
    public static final String METRICS_ENDPOINT = "/actuator/prometheus";

    /**
     * Ruta para acceder a la interfaz de Swagger UI.
     */
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";

    /**
     * Patrón para todas las rutas de Swagger UI.
     */
    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";

    /**
     * Patrón para recursos estáticos de WebJars.
     */
    public static final String WEBJARS_PATH = "/webjars/**";

    /**
     * Patrón para documentación de API OpenAPI v3.
     */
    public static final String API_DOCS_PATH = "/v3/api-docs/**";

}