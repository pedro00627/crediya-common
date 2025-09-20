package co.com.pragma.model.constants;

/**
 * Constantes para parámetros de consulta utilizados en las APIs.
 * Centraliza los nombres de parámetros para mantener consistencia.
 */
public final class QueryParameterConstants {

    // User-related query parameters
    public static final String EMAIL = "email";
    public static final String IDENTITY_DOCUMENT = "identityDocument";
    public static final String USER_ID = "userId";

    // Application-related query parameters
    public static final String APPLICATION_ID = "applicationId";
    public static final String STATUS = "status";
    public static final String LOAN_TYPE = "loanType";

    // Pagination query parameters
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT = "sort";
    public static final String DIRECTION = "direction";

    // Date range query parameters
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";

    // Filtering query parameters
    public static final String SEARCH = "search";
    public static final String FILTER = "filter";

    private QueryParameterConstants() {
        // Utility class - prevent instantiation
    }
}