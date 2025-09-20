package co.com.pragma.starter.exception;

/**
 * Exception class for business rule violations.
 * Used when business logic constraints are not met.
 */
public class BusinessException extends RuntimeException {

    private final String errorCode;

    /**
     * Creates a BusinessException with a message.
     *
     * @param message The error message
     */
    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }

    /**
     * Creates a BusinessException with a message and error code.
     *
     * @param message   The error message
     * @param errorCode The specific error code
     */
    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Creates a BusinessException with a message and cause.
     *
     * @param message The error message
     * @param cause   The underlying cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BUSINESS_ERROR";
    }

    /**
     * Creates a BusinessException with message, cause, and error code.
     *
     * @param message   The error message
     * @param cause     The underlying cause
     * @param errorCode The specific error code
     */
    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code associated with this exception.
     *
     * @return The error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}