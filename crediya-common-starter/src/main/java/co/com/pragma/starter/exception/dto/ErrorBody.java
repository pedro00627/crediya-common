package co.com.pragma.starter.exception.dto;

/**
 * Represents the structure of an error response body.
 * This class standardizes error information across all microservices.
 */
public record ErrorBody(
        int statusCode,
        String error,
        String message,
        String details
) {
    /**
     * Creates an ErrorBody with minimal information.
     *
     * @param statusCode HTTP status code
     * @param error      Error category
     * @param message    Error message
     * @return ErrorBody instance
     */
    public static ErrorBody create(int statusCode, String error, String message) {
        return new ErrorBody(statusCode, error, message, null);
    }

    /**
     * Creates an ErrorBody with complete information.
     *
     * @param statusCode HTTP status code
     * @param error      Error category
     * @param message    Error message
     * @param details    Additional error details
     * @return ErrorBody instance
     */
    public static ErrorBody create(int statusCode, String error, String message, String details) {
        return new ErrorBody(statusCode, error, message, details);
    }
}