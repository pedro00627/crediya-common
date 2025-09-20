package co.com.pragma.model.constants;

/**
 * Constantes relacionadas con HTTP: headers, content types, y otros valores HTTP.
 * Centraliza las definiciones para mantener consistencia en las comunicaciones HTTP.
 */
public final class HttpConstants {

    // HTTP Headers
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String USER_AGENT_HEADER = "User-Agent";
    public static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
    public static final String X_REQUEST_ID_HEADER = "X-Request-ID";

    // Security Headers
    public static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";
    public static final String STRICT_TRANSPORT_SECURITY_HEADER = "Strict-Transport-Security";
    public static final String X_CONTENT_TYPE_OPTIONS_HEADER = "X-Content-Type-Options";
    public static final String X_FRAME_OPTIONS_HEADER = "X-Frame-Options";
    public static final String X_XSS_PROTECTION_HEADER = "X-XSS-Protection";
    public static final String REFERRER_POLICY_HEADER = "Referrer-Policy";
    public static final String CACHE_CONTROL_HEADER = "Cache-Control";
    public static final String PRAGMA_HEADER = "Pragma";

    // Security Header Values
    public static final String CSP_DEFAULT_SRC_SELF = "default-src 'self'; frame-ancestors 'self'; form-action 'self'";
    public static final String HSTS_MAX_AGE = "max-age=31536000;";
    public static final String X_CONTENT_TYPE_NOSNIFF = "nosniff";
    public static final String X_FRAME_OPTIONS_DENY = "DENY";
    public static final String X_XSS_PROTECTION_ENABLED = "1; mode=block";
    public static final String REFERRER_POLICY_STRICT = "strict-origin-when-cross-origin";
    public static final String CACHE_CONTROL_NO_STORE = "no-store";
    public static final String PRAGMA_NO_CACHE = "no-cache";

    // Content Types
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    // Authentication
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String BASIC_PREFIX = "Basic ";

    // CORS
    public static final String CORS_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    public static final String CORS_METHODS_HEADER = "Access-Control-Allow-Methods";
    public static final String CORS_HEADERS_HEADER = "Access-Control-Allow-Headers";
    public static final String CORS_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";

    // HTTP Methods
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final String PATCH_METHOD = "PATCH";
    public static final String OPTIONS_METHOD = "OPTIONS";
    public static final String HEAD_METHOD = "HEAD";

    // Query Parameters
    public static final String PAGE_PARAM = "page";
    public static final String SIZE_PARAM = "size";
    public static final String SORT_PARAM = "sort";
    public static final String EMAIL_PARAM = "email";
    public static final String IDENTITY_DOCUMENT_PARAM = "identityDocument";

    private HttpConstants() {
        // Utility class - prevent instantiation
    }
}