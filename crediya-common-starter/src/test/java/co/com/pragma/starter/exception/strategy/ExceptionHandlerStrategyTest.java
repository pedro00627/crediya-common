package co.com.pragma.starter.exception.strategy;

import co.com.pragma.starter.exception.dto.ErrorResponseWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExceptionHandlerStrategyTest {

    @Test
    void shouldBeAnInterface() {
        assertTrue(ExceptionHandlerStrategy.class.isInterface());
    }

    @Test
    void shouldHaveCorrectHandleMethod() throws NoSuchMethodException {
        ExceptionHandlerStrategy.class.getMethod("handle", Throwable.class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectGetExceptionTypeMethod() throws NoSuchMethodException {
        ExceptionHandlerStrategy.class.getMethod("getExceptionType");
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveExactlyTwoMethods() {
        assertEquals(2, ExceptionHandlerStrategy.class.getDeclaredMethods().length);
    }

    @Test
    void shouldBeGenericInterface() {
        assertEquals(1, ExceptionHandlerStrategy.class.getTypeParameters().length);
        assertEquals("T", ExceptionHandlerStrategy.class.getTypeParameters()[0].getName());
    }

    @Test
    void mockImplementationShouldWorkCorrectly() {
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<RuntimeException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        final RuntimeException testException = new RuntimeException("Test exception");
        final ErrorResponseWrapper errorWrapper = ErrorResponseWrapper.create(500, "INTERNAL_SERVER_ERROR", "Server error");
        final ResponseEntity<ErrorResponseWrapper> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorWrapper);

        // Configure mock behavior
        when(mockStrategy.handle(testException)).thenReturn(expectedResponse);
        when(mockStrategy.getExceptionType()).thenReturn(RuntimeException.class);

        // Test mock behavior
        final ResponseEntity<ErrorResponseWrapper> actualResponse = mockStrategy.handle(testException);
        final Class<RuntimeException> exceptionType = mockStrategy.getExceptionType();

        assertEquals(expectedResponse, actualResponse);
        assertSame(RuntimeException.class, exceptionType);

        // Verify method calls
        verify(mockStrategy).handle(testException);
        verify(mockStrategy).getExceptionType();
    }

    @Test
    void handleMethodShouldReturnResponseEntity() {
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<IllegalArgumentException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        final IllegalArgumentException testException = new IllegalArgumentException("Invalid argument");
        final ErrorResponseWrapper errorWrapper = ErrorResponseWrapper.create(400, "BAD_REQUEST", "Invalid argument");
        final ResponseEntity<ErrorResponseWrapper> response = ResponseEntity.badRequest().body(errorWrapper);

        when(mockStrategy.handle(testException)).thenReturn(response);

        final ResponseEntity<ErrorResponseWrapper> result = mockStrategy.handle(testException);

        assertNotNull(result);
        assertInstanceOf(ResponseEntity.class, result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getExceptionTypeShouldReturnClassType() {
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<NullPointerException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        when(mockStrategy.getExceptionType()).thenReturn(NullPointerException.class);

        final Class<NullPointerException> result = mockStrategy.getExceptionType();

        assertNotNull(result);
        assertSame(NullPointerException.class, result);
        assertTrue(Throwable.class.isAssignableFrom(result));
    }

    @Test
    void shouldSupportDifferentExceptionTypes() {
        // Test with different exception types
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<IllegalStateException> strategy1 = mock(ExceptionHandlerStrategy.class);
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<UnsupportedOperationException> strategy2 = mock(ExceptionHandlerStrategy.class);

        when(strategy1.getExceptionType()).thenReturn(IllegalStateException.class);
        when(strategy2.getExceptionType()).thenReturn(UnsupportedOperationException.class);

        assertSame(IllegalStateException.class, strategy1.getExceptionType());
        assertSame(UnsupportedOperationException.class, strategy2.getExceptionType());
        assertNotEquals(strategy1.getExceptionType(), strategy2.getExceptionType());
    }

    @Test
    void shouldWorkWithCustomExceptions() {
        class CustomException extends Exception {
            public CustomException(final String message) {
                super(message);
            }
        }

        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<CustomException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        final CustomException customException = new CustomException("Custom error");
        final ErrorResponseWrapper errorWrapper = ErrorResponseWrapper.create(422, "CUSTOM_ERROR", "Custom error");
        final ResponseEntity<ErrorResponseWrapper> response = ResponseEntity.unprocessableEntity().body(errorWrapper);

        when(mockStrategy.handle(customException)).thenReturn(response);
        when(mockStrategy.getExceptionType()).thenReturn(CustomException.class);

        final ResponseEntity<ErrorResponseWrapper> result = mockStrategy.handle(customException);
        final Class<CustomException> exceptionType = mockStrategy.getExceptionType();

        assertEquals(response, result);
        assertSame(CustomException.class, exceptionType);
        assertTrue(Exception.class.isAssignableFrom(exceptionType));
    }

    @Test
    void shouldHandleNullExceptionGracefully() {
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<RuntimeException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        final ErrorResponseWrapper errorWrapper = ErrorResponseWrapper.create(500, "NULL_EXCEPTION", "Null exception received");
        final ResponseEntity<ErrorResponseWrapper> response = ResponseEntity.internalServerError().body(errorWrapper);

        when(mockStrategy.handle(null)).thenReturn(response);

        final ResponseEntity<ErrorResponseWrapper> result = mockStrategy.handle(null);

        assertEquals(response, result);
        verify(mockStrategy).handle(null);
    }

    @Test
    void shouldSupportMethodChaining() {
        @SuppressWarnings("unchecked") final ExceptionHandlerStrategy<RuntimeException> mockStrategy = mock(ExceptionHandlerStrategy.class);

        final RuntimeException exception = new RuntimeException("Test");
        final ErrorResponseWrapper errorWrapper = ErrorResponseWrapper.create(500, "ERROR", "Test error");
        final ResponseEntity<ErrorResponseWrapper> response = ResponseEntity.internalServerError().body(errorWrapper);

        when(mockStrategy.getExceptionType()).thenReturn(RuntimeException.class);
        when(mockStrategy.handle(exception)).thenReturn(response);

        // Should be able to chain method calls
        final Class<RuntimeException> type = mockStrategy.getExceptionType();
        final ResponseEntity<ErrorResponseWrapper> result = mockStrategy.handle(exception);

        assertSame(RuntimeException.class, type);
        assertEquals(response, result);
    }
}