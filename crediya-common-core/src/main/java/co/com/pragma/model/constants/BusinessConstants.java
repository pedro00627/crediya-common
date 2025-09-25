package co.com.pragma.model.constants;

import java.math.BigDecimal;

/**
 * Constantes de reglas de negocio para el sistema CrediYa.
 * Define límites, porcentajes y otros valores críticos para la lógica de negocio.
 */
public enum BusinessConstants {
    ;

    /**
     * Salario base mínimo aceptado en el sistema (pesos colombianos).
     */
    public static final BigDecimal MIN_BASE_SALARY = BigDecimal.ZERO;

    /**
     * Salario base máximo aceptado en el sistema (15 millones de pesos colombianos).
     */
    public static final BigDecimal MAX_BASE_SALARY = new BigDecimal("15000000");

    /**
     * Ratio máximo de deuda sobre ingresos permitido (35%).
     * Utilizado para determinar la capacidad de endeudamiento del cliente.
     */
    public static final BigDecimal MAX_DEBT_TO_INCOME_RATIO = new BigDecimal("0.35");

    /**
     * Multiplicador de salarios mínimos que determina cuándo una solicitud requiere revisión manual.
     * Si el monto solicitado supera 5 veces el salario, pasa a revisión manual.
     */
    public static final int MANUAL_REVIEW_SALARY_MULTIPLIER = 5;

    /**
     * Tipo de préstamo de libre inversión por defecto.
     */
    public static final String LIBRE_INVERSION_LOAN_TYPE = "LIBRE INVERSION";

    /**
     * Tipo de préstamo educativo por defecto.
     */
    public static final String EDUCATIVO_LOAN_TYPE = "Educativo";

    /**
     * ID del rol de administrador (corresponde al valor en base de datos).
     */
    public static final Integer ADMIN_ROLE_ID = 3;

    /**
     * ID del rol de asesor (corresponde al valor en base de datos).
     */
    public static final Integer ADVISOR_ROLE_ID = 2;

    /**
     * ID del rol de cliente (corresponde al valor en base de datos).
     */
    public static final Integer CLIENT_ROLE_ID = 1;

    /**
     * Tiempo de expiración por defecto para tokens JWT (1 hora en milisegundos).
     */
    public static final long DEFAULT_JWT_EXPIRATION_MS = 3600000L;

    /**
     * Nombre del claim que contiene los roles en el token JWT.
     */
    public static final String JWT_ROLES_CLAIM = "roles";

    /**
     * Tamaño de página por defecto para consultas paginadas.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Tamaño máximo de página permitido para consultas paginadas.
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * Número de página por defecto (base cero).
     */
    public static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Nombre del cache para estados de solicitudes.
     */
    public static final String CACHE_STATUSES = "statuses";

    /**
     * Nombre del cache para tipos de préstamos.
     */
    public static final String CACHE_LOAN_TYPES = "loan_types";

    /**
     * Especificación de configuración para caches (máximo 500 elementos, expiración después de 500s de inactividad).
     */
    public static final String CACHE_SPEC = "maximumSize=500,expireAfterAccess=500s";

    /**
     * Porcentaje de fallas que activa el circuit breaker (50%).
     */
    public static final int DEFAULT_FAILURE_RATE_THRESHOLD = 50;

    /**
     * Porcentaje de llamadas lentas que activa el circuit breaker (50%).
     */
    public static final int DEFAULT_SLOW_CALL_RATE_THRESHOLD = 50;

    /**
     * Duración que define una llamada como lenta (2 segundos).
     */
    public static final String DEFAULT_SLOW_CALL_DURATION = "2s";

    /**
     * Número de llamadas permitidas cuando el circuit breaker está semi-abierto.
     */
    public static final int DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN = 3;

    /**
     * Tamaño de la ventana deslizante para el circuit breaker.
     */
    public static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;

    /**
     * Número mínimo de llamadas requeridas antes de calcular la tasa de fallas.
     */
    public static final int DEFAULT_MINIMUM_NUMBER_OF_CALLS = 10;

    /**
     * Tiempo de espera antes de pasar de abierto a semi-abierto (10 segundos).
     */
    public static final String DEFAULT_WAIT_DURATION_IN_OPEN_STATE = "10s";

    /**
     * Timeout por defecto para llamadas a la API de usuarios (5 segundos).
     */
    public static final int DEFAULT_USER_API_TIMEOUT_MS = 5000;

    /**
     * Timeout por defecto para establecer conexiones (5 segundos).
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 5000;

    /**
     * Tamaño inicial del pool de conexiones a base de datos.
     */
    public static final int DEFAULT_DB_POOL_INITIAL_SIZE = 5;

    /**
     * Tamaño máximo del pool de conexiones a base de datos.
     */
    public static final int DEFAULT_DB_POOL_MAX_SIZE = 10;

    /**
     * Tiempo máximo que una conexión puede estar inactiva antes de ser cerrada (30 minutos).
     */
    public static final String DEFAULT_DB_POOL_MAX_IDLE_TIME = "30m";

    /**
     * Timeout para establecer conexión con la base de datos (5 segundos).
     */
    public static final String DEFAULT_DB_POOL_CONNECT_TIMEOUT = "5s";

    /**
     * Ruta del endpoint de health check.
     */
    public static final String HEALTH_ENDPOINT_PATH = "health";

    /**
     * Ruta del endpoint de métricas de Prometheus.
     */
    public static final String PROMETHEUS_ENDPOINT_PATH = "prometheus";

    /**
     * Probabilidad de muestreo para trazas distribuidas (100% en desarrollo).
     */
    public static final double DEFAULT_TRACING_SAMPLING_PROBABILITY = 1.0;

    /**
     * Orígenes permitidos por defecto para CORS.
     */
    public static final String DEFAULT_CORS_ORIGINS = "http://localhost:4200,http://localhost:8080";

    /**
     * Expresión regular para validación de direcciones de correo electrónico.
     */
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /**
     * Expresión regular para validación de números de teléfono (10-15 dígitos, con signo + opcional).
     */
    public static final String PHONE_REGEX = "^[+]?[0-9]{10,15}$";

    /**
     * Expresión regular para validar formato de roles (solo letras mayúsculas y guiones bajos).
     */
    public static final String ROLE_FORMAT_REGEX = "^[A-Z_]+$";

}