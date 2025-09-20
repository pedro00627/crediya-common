package co.com.pragma.starter.exception.strategy;

import co.com.pragma.starter.exception.dto.ErrorResponseWrapper;
import org.springframework.http.ResponseEntity;

/**
 * Strategy interface for handling different types of exceptions.
 * Implementations should define how specific exceptions are converted to HTTP responses.
 */
public interface ExceptionHandlerStrategy<T extends Throwable> {

    /**
     * Handles the given exception and returns an appropriate HTTP response.
     *
     * @param exception The exception to handle
     * @return ResponseEntity with error information
     */
    ResponseEntity<ErrorResponseWrapper> handle(T exception);

    /**
     * Returns the type of exception this strategy can handle.
     *
     * @return Class of the exception type
     */
    Class<T> getExceptionType();
}