package co.com.pragma.model.constants;

/**
 * Constantes relacionadas con HTTP: headers, content types, y otros valores HTTP.
 * Centraliza las definiciones para mantener consistencia en las comunicaciones HTTP.
 */
public enum HttpConstants {
    ;

    /**
     * Header HTTP para autenticación (contiene tokens JWT o credenciales básicas).
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Header HTTP que especifica el tipo de contenido de la petición.
     */
    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    /**
     * Header HTTP que especifica los tipos de contenido aceptados en la respuesta.
     */
    public static final String ACCEPT_HEADER = "Accept";

    /**
     * Header HTTP que identifica la aplicación cliente que hace la petición.
     */
    public static final String USER_AGENT_HEADER = "User-Agent";

    /**
     * Header HTTP para identificar la IP original del cliente cuando pasa por proxies.
     */
    public static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";

    /**
     * Header HTTP personalizado para rastrear peticiones específicas.
     */
    public static final String X_REQUEST_ID_HEADER = "X-Request-ID";

    /**
     * Header de seguridad que define la política de seguridad de contenido.
     */
    public static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";

    /**
     * Header de seguridad que fuerza el uso de HTTPS (HTTP Strict Transport Security).
     */
    public static final String STRICT_TRANSPORT_SECURITY_HEADER = "Strict-Transport-Security";

    /**
     * Header de seguridad que previene ataques de tipo MIME sniffing.
     */
    public static final String X_CONTENT_TYPE_OPTIONS_HEADER = "X-Content-Type-Options";

    /**
     * Header de seguridad que controla si la página puede ser mostrada en un frame.
     */
    public static final String X_FRAME_OPTIONS_HEADER = "X-Frame-Options";

    /**
     * Header de seguridad que habilita la protección contra ataques XSS.
     */
    public static final String X_XSS_PROTECTION_HEADER = "X-XSS-Protection";

    /**
     * Header de seguridad que controla cuánta información de referencia se envía.
     */
    public static final String REFERRER_POLICY_HEADER = "Referrer-Policy";

    /**
     * Header HTTP que controla el almacenamiento en caché.
     */
    public static final String CACHE_CONTROL_HEADER = "Cache-Control";

    /**
     * Header HTTP heredado para control de caché (compatibilidad con HTTP/1.0).
     */
    public static final String PRAGMA_HEADER = "Pragma";

    /**
     * Valor de Content Security Policy que permite recursos solo del mismo origen.
     */
    public static final String CSP_DEFAULT_SRC_SELF = "default-src 'self'; frame-ancestors 'self'; form-action 'self'";

    /**
     * Valor HSTS que fuerza HTTPS por un año (31,536,000 segundos).
     */
    public static final String HSTS_MAX_AGE = "max-age=31536000;";

    /**
     * Valor que previene el MIME type sniffing en navegadores.
     */
    public static final String X_CONTENT_TYPE_NOSNIFF = "nosniff";

    /**
     * Valor que niega completamente mostrar la página en frames.
     */
    public static final String X_FRAME_OPTIONS_DENY = "DENY";

    /**
     * Valor que habilita la protección XSS con modo de bloqueo.
     */
    public static final String X_XSS_PROTECTION_ENABLED = "1; mode=block";

    /**
     * Valor de política de referencia que envía origen solo en navegación cross-origin.
     */
    public static final String REFERRER_POLICY_STRICT = "strict-origin-when-cross-origin";

    /**
     * Valor de cache control que prohíbe el almacenamiento en caché.
     */
    public static final String CACHE_CONTROL_NO_STORE = "no-store";

    /**
     * Valor pragma que prohíbe el almacenamiento en caché (HTTP/1.0).
     */
    public static final String PRAGMA_NO_CACHE = "no-cache";

    /**
     * Tipo de contenido para datos JSON.
     */
    public static final String APPLICATION_JSON = "application/json";

    /**
     * Tipo de contenido para datos XML.
     */
    public static final String APPLICATION_XML = "application/xml";

    /**
     * Tipo de contenido para texto plano.
     */
    public static final String TEXT_PLAIN = "text/plain";

    /**
     * Tipo de contenido para HTML.
     */
    public static final String TEXT_HTML = "text/html";

    /**
     * Tipo de contenido para formularios URL encoded.
     */
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * Tipo de contenido para formularios con archivos adjuntos.
     */
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * Prefijo para tokens Bearer en el header Authorization.
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Prefijo para autenticación básica en el header Authorization.
     */
    public static final String BASIC_PREFIX = "Basic ";

    /**
     * Header CORS que especifica los orígenes permitidos.
     */
    public static final String CORS_ORIGIN_HEADER = "Access-Control-Allow-Origin";

    /**
     * Header CORS que especifica los métodos HTTP permitidos.
     */
    public static final String CORS_METHODS_HEADER = "Access-Control-Allow-Methods";

    /**
     * Header CORS que especifica los headers permitidos en peticiones.
     */
    public static final String CORS_HEADERS_HEADER = "Access-Control-Allow-Headers";

    /**
     * Header CORS que indica si se permiten credenciales en peticiones cross-origin.
     */
    public static final String CORS_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";

    /**
     * Método HTTP GET para recuperar datos.
     */
    public static final String GET_METHOD = "GET";

    /**
     * Método HTTP POST para crear nuevos recursos.
     */
    public static final String POST_METHOD = "POST";

    /**
     * Método HTTP PUT para actualizar recursos completos.
     */
    public static final String PUT_METHOD = "PUT";

    /**
     * Método HTTP DELETE para eliminar recursos.
     */
    public static final String DELETE_METHOD = "DELETE";

    /**
     * Método HTTP PATCH para actualizaciones parciales de recursos.
     */
    public static final String PATCH_METHOD = "PATCH";

    /**
     * Método HTTP OPTIONS para consultar opciones disponibles del servidor.
     */
    public static final String OPTIONS_METHOD = "OPTIONS";

    /**
     * Método HTTP HEAD para obtener solo los headers de una respuesta.
     */
    public static final String HEAD_METHOD = "HEAD";

    /**
     * Parámetro de consulta para especificar el número de página en paginación.
     */
    public static final String PAGE_PARAM = "page";

    /**
     * Parámetro de consulta para especificar el tamaño de página en paginación.
     */
    public static final String SIZE_PARAM = "size";

    /**
     * Parámetro de consulta para especificar criterios de ordenamiento.
     */
    public static final String SORT_PARAM = "sort";

    /**
     * Parámetro de consulta para filtrar por dirección de correo electrónico.
     */
    public static final String EMAIL_PARAM = "email";

    /**
     * Parámetro de consulta para filtrar por documento de identidad.
     */
    public static final String IDENTITY_DOCUMENT_PARAM = "identityDocument";

}