package co.com.pragma.model.constants;

/**
 * Constantes para parámetros de consulta utilizados en las APIs.
 * Centraliza los nombres de parámetros para mantener consistencia.
 */
public enum QueryParameterConstants {
    ;

    /**
     * Parámetro de consulta para filtrar usuarios por dirección de correo electrónico.
     * Ejemplo: /api/v1/usuarios?email=juan@ejemplo.com
     */
    public static final String EMAIL = "email";

    /**
     * Parámetro de consulta para filtrar usuarios por documento de identidad (cédula, pasaporte).
     * Ejemplo: /api/v1/usuarios?identityDocument=12345678
     */
    public static final String IDENTITY_DOCUMENT = "identityDocument";

    /**
     * Parámetro de consulta para especificar un ID de usuario específico.
     * Ejemplo: /api/v1/usuarios?userId=123
     */
    public static final String USER_ID = "userId";

    /**
     * Parámetro de consulta para especificar un ID de solicitud de crédito específica.
     * Ejemplo: /api/v1/solicitudes?applicationId=456
     */
    public static final String APPLICATION_ID = "applicationId";

    /**
     * Parámetro de consulta para filtrar solicitudes por estado (pendiente, aprobado, rechazado).
     * Ejemplo: /api/v1/solicitudes?status=PENDIENTE
     */
    public static final String STATUS = "status";

    /**
     * Parámetro de consulta para filtrar solicitudes por tipo de préstamo.
     * Ejemplo: /api/v1/solicitudes?loanType=LIBRE_INVERSION
     */
    public static final String LOAN_TYPE = "loanType";

    /**
     * Parámetro de consulta para especificar el número de página en consultas paginadas (base cero).
     * Ejemplo: /api/v1/usuarios?page=0
     */
    public static final String PAGE = "page";

    /**
     * Parámetro de consulta para especificar el tamaño de página en consultas paginadas.
     * Ejemplo: /api/v1/usuarios?size=10
     */
    public static final String SIZE = "size";

    /**
     * Parámetro de consulta para especificar el campo por el cual ordenar los resultados.
     * Ejemplo: /api/v1/usuarios?sort=fechaCreacion
     */
    public static final String SORT = "sort";

    /**
     * Parámetro de consulta para especificar la dirección del ordenamiento (ASC o DESC).
     * Ejemplo: /api/v1/usuarios?direction=DESC
     */
    public static final String DIRECTION = "direction";

    /**
     * Parámetro de consulta para especificar la fecha de inicio en filtros de rango de fechas.
     * Ejemplo: /api/v1/solicitudes?startDate=2024-01-01
     */
    public static final String START_DATE = "startDate";

    /**
     * Parámetro de consulta para especificar la fecha de fin en filtros de rango de fechas.
     * Ejemplo: /api/v1/solicitudes?endDate=2024-12-31
     */
    public static final String END_DATE = "endDate";

    /**
     * Parámetro de consulta alternativo para especificar la fecha desde en filtros de rango.
     * Ejemplo: /api/v1/reportes?dateFrom=2024-01-01
     */
    public static final String DATE_FROM = "dateFrom";

    /**
     * Parámetro de consulta alternativo para especificar la fecha hasta en filtros de rango.
     * Ejemplo: /api/v1/reportes?dateTo=2024-12-31
     */
    public static final String DATE_TO = "dateTo";

    /**
     * Parámetro de consulta para búsqueda de texto libre en múltiples campos.
     * Ejemplo: /api/v1/usuarios?search=Juan Pérez
     */
    public static final String SEARCH = "search";

    /**
     * Parámetro de consulta genérico para aplicar filtros personalizados.
     * Ejemplo: /api/v1/solicitudes?filter=montoMayor1000000
     */
    public static final String FILTER = "filter";

}