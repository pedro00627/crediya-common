package co.com.pragma.starter.exception.dto;

/**
 * Wrapper for error responses to provide consistent structure across microservices.
 */
public record ErrorResponseWrapper(
        ErrorBody error
) {
    /**
     * Creates an ErrorResponseWrapper from an ErrorBody.
     *
     * @param errorBody The error body to wrap
     * @return ErrorResponseWrapper instance
     */
    public static ErrorResponseWrapper wrap(ErrorBody errorBody) {
        return new ErrorResponseWrapper(errorBody);
    }

    /**
     * Creates an ErrorResponseWrapper with minimal error information.
     *
     * @param statusCode HTTP status code
     * @param error      Error category
     * @param message    Error message
     * @return ErrorResponseWrapper instance
     */
    public static ErrorResponseWrapper create(int statusCode, String error, String message) {
        return new ErrorResponseWrapper(ErrorBody.create(statusCode, error, message));
    }

    /**
     * Creates an ErrorResponseWrapper with complete error information.
     *
     * @param statusCode HTTP status code
     * @param error      Error category
     * @param message    Error message
     * @param details    Additional error details
     * @return ErrorResponseWrapper instance
     */
    public static ErrorResponseWrapper create(int statusCode, String error, String message, String details) {
        return new ErrorResponseWrapper(ErrorBody.create(statusCode, error, message, details));
    }
}