package co.com.pragma.model.constants;

import java.math.BigDecimal;

/**
 * Constantes de reglas de negocio para el sistema CrediYa.
 * Define límites, porcentajes y otros valores críticos para la lógica de negocio.
 */
public final class BusinessConstants {

    // Salary Validation
    public static final BigDecimal MIN_BASE_SALARY = BigDecimal.ZERO;
    public static final BigDecimal MAX_BASE_SALARY = new BigDecimal("15000000");

    // Credit Analysis - Debt Capacity
    public static final BigDecimal MAX_DEBT_TO_INCOME_RATIO = new BigDecimal("0.35"); // 35%
    public static final int MANUAL_REVIEW_SALARY_MULTIPLIER = 5; // 5 salarios mínimos

    // Default Loan Types
    public static final String LIBRE_INVERSION_LOAN_TYPE = "LIBRE INVERSION";
    public static final String EDUCATIVO_LOAN_TYPE = "Educativo";

    // Role IDs (corresponding to database values)
    public static final Integer ADMIN_ROLE_ID = 1;
    public static final Integer ADVISOR_ROLE_ID = 2;
    public static final Integer CLIENT_ROLE_ID = 3;

    // JWT Configuration
    public static final long DEFAULT_JWT_EXPIRATION_MS = 3600000L; // 1 hour
    public static final String JWT_ROLES_CLAIM = "roles";

    // Pagination Defaults
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    // Cache Configuration
    public static final String CACHE_STATUSES = "statuses";
    public static final String CACHE_LOAN_TYPES = "loan_types";
    public static final String CACHE_SPEC = "maximumSize=500,expireAfterAccess=500s";

    // Circuit Breaker Configuration
    public static final int DEFAULT_FAILURE_RATE_THRESHOLD = 50;
    public static final int DEFAULT_SLOW_CALL_RATE_THRESHOLD = 50;
    public static final String DEFAULT_SLOW_CALL_DURATION = "2s";
    public static final int DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN = 3;
    public static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;
    public static final int DEFAULT_MINIMUM_NUMBER_OF_CALLS = 10;
    public static final String DEFAULT_WAIT_DURATION_IN_OPEN_STATE = "10s";

    // Service Communication Timeouts
    public static final int DEFAULT_USER_API_TIMEOUT_MS = 5000;
    public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 5000;

    // Database Pool Configuration
    public static final int DEFAULT_DB_POOL_INITIAL_SIZE = 5;
    public static final int DEFAULT_DB_POOL_MAX_SIZE = 10;
    public static final String DEFAULT_DB_POOL_MAX_IDLE_TIME = "30m";
    public static final String DEFAULT_DB_POOL_CONNECT_TIMEOUT = "5s";

    // Monitoring and Health
    public static final String HEALTH_ENDPOINT_PATH = "health";
    public static final String PROMETHEUS_ENDPOINT_PATH = "prometheus";
    public static final double DEFAULT_TRACING_SAMPLING_PROBABILITY = 1.0; // 100% in development

    // CORS Configuration
    public static final String DEFAULT_CORS_ORIGINS = "http://localhost:4200,http://localhost:8080";

    // Regular Expressions for Validation
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_REGEX = "^[+]?[0-9]{10,15}$";
    public static final String ROLE_FORMAT_REGEX = "^[A-Z_]+$";

    private BusinessConstants() {
        // Utility class - prevent instantiation
    }
}