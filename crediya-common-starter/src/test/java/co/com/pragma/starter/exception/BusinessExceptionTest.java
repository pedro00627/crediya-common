package co.com.pragma.starter.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void shouldExtendRuntimeException() {
        assertTrue(RuntimeException.class.isAssignableFrom(BusinessException.class));
    }

    @Test
    void shouldCreateExceptionWithMessage() {
        final String message = "Test business error";
        final BusinessException exception = new BusinessException(message);

        assertEquals(message, exception.getMessage());
        assertEquals("BUSINESS_ERROR", exception.getErrorCode());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithMessageAndErrorCode() {
        final String message = "Test business error";
        final String errorCode = "CUSTOM_ERROR_CODE";
        final BusinessException exception = new BusinessException(message, errorCode);

        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        final String message = "Test business error";
        final RuntimeException cause = new RuntimeException("Underlying cause");
        final BusinessException exception = new BusinessException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals("BUSINESS_ERROR", exception.getErrorCode());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithMessageCauseAndErrorCode() {
        final String message = "Test business error";
        final RuntimeException cause = new RuntimeException("Underlying cause");
        final String errorCode = "CUSTOM_ERROR_CODE";
        final BusinessException exception = new BusinessException(message, cause, errorCode);

        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldHaveDefaultErrorCodeWhenNotProvided() {
        final BusinessException exception1 = new BusinessException("Test message");
        final BusinessException exception2 = new BusinessException("Test message", new RuntimeException());

        assertEquals("BUSINESS_ERROR", exception1.getErrorCode());
        assertEquals("BUSINESS_ERROR", exception2.getErrorCode());
    }

    @Test
    void shouldAllowCustomErrorCode() {
        final String customCode1 = "VALIDATION_ERROR";
        final String customCode2 = "AUTHORIZATION_ERROR";

        final BusinessException exception1 = new BusinessException("Message", customCode1);
        final BusinessException exception2 = new BusinessException("Message", new RuntimeException(), customCode2);

        assertEquals(customCode1, exception1.getErrorCode());
        assertEquals(customCode2, exception2.getErrorCode());
    }

    @Test
    void shouldAllowNullErrorCode() {
        final BusinessException exception = new BusinessException("Test message", (String) null);
        assertNull(exception.getErrorCode());
    }

    @Test
    void shouldAllowEmptyErrorCode() {
        final BusinessException exception = new BusinessException("Test message", "");
        assertEquals("", exception.getErrorCode());
    }

    @Test
    void shouldPreserveCauseInformation() {
        final String originalMessage = "Original exception message";
        final RuntimeException originalException = new RuntimeException(originalMessage);

        final BusinessException businessException = new BusinessException("Business error", originalException);

        assertEquals(originalException, businessException.getCause());
        assertEquals(originalMessage, businessException.getCause().getMessage());
    }

    @Test
    void shouldHandleNullMessage() {
        final BusinessException exception = new BusinessException(null);
        assertNull(exception.getMessage());
        assertEquals("BUSINESS_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldHandleNullCause() {
        final BusinessException exception = new BusinessException("Test message", (Throwable) null);
        assertEquals("Test message", exception.getMessage());
        assertNull(exception.getCause());
        assertEquals("BUSINESS_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldBeCatchableAsRuntimeException() {
        final BusinessException businessException = new BusinessException("Test");

        assertDoesNotThrow(() -> {
            try {
                throw businessException;
            } catch (final RuntimeException e) {
                // Should catch as RuntimeException
                assertEquals(businessException, e);
            }
        });
    }

    @Test
    void shouldBeCatchableAsBusinessException() {
        final BusinessException businessException = new BusinessException("Test", "CUSTOM_CODE");

        assertDoesNotThrow(() -> {
            try {
                throw businessException;
            } catch (final BusinessException e) {
                // Should catch as BusinessException
                assertEquals(businessException, e);
                assertEquals("CUSTOM_CODE", e.getErrorCode());
            }
        });
    }

    @Test
    void shouldSupportMethodChaining() {
        final String message = "Test message";
        final String errorCode = "TEST_CODE";
        final RuntimeException cause = new RuntimeException("Test cause");

        final BusinessException exception = new BusinessException(message, cause, errorCode);

        // Verify all properties are correctly set
        assertEquals(message, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(cause, exception.getCause());
    }
}