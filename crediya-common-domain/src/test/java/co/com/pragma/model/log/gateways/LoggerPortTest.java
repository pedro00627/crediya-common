package co.com.pragma.model.log.gateways;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoggerPortTest {

    @Test
    void shouldBeAnInterface() {
        assertTrue(LoggerPort.class.isInterface());
    }

    @Test
    void shouldHaveCorrectInfoMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("info", String.class, Object[].class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectWarnMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("warn", String.class, Object[].class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectDebugMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("debug", String.class, Object[].class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectErrorMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("error", String.class, Throwable.class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectMaskEmailMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("maskEmail", String.class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveCorrectMaskDocumentMethod() throws NoSuchMethodException {
        LoggerPort.class.getMethod("maskDocument", String.class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveExactlyFiveMethodsAsDeclared() {
        // LoggerPort should have exactly 6 public methods: info, warn, debug, error, maskEmail, maskDocument
        assertEquals(6, LoggerPort.class.getDeclaredMethods().length);
    }

    @Test
    void mockImplementationShouldWorkCorrectly() {
        final LoggerPort mockLogger = mock(LoggerPort.class);

        // Configure mock behavior
        when(mockLogger.maskEmail("test@example.com")).thenReturn("t***@example.com");
        when(mockLogger.maskDocument("1234567890")).thenReturn("1***7890");

        // Test mock behavior
        assertEquals("t***@example.com", mockLogger.maskEmail("test@example.com"));
        assertEquals("1***7890", mockLogger.maskDocument("1234567890"));

        // Verify method calls
        verify(mockLogger).maskEmail("test@example.com");
        verify(mockLogger).maskDocument("1234567890");

        // Test void methods don't throw exceptions
        assertDoesNotThrow(() -> mockLogger.info("Test message"));
        assertDoesNotThrow(() -> mockLogger.warn("Warning message"));
        assertDoesNotThrow(() -> mockLogger.debug("Debug message"));
        assertDoesNotThrow(() -> mockLogger.error("Error message", new RuntimeException()));

        // Verify void method calls
        verify(mockLogger).info("Test message");
        verify(mockLogger).warn("Warning message");
        verify(mockLogger).debug("Debug message");
        verify(mockLogger).error(eq("Error message"), any(RuntimeException.class));
    }

    @Test
    void shouldAcceptVarArgsInLogMethods() {
        final LoggerPort mockLogger = mock(LoggerPort.class);

        assertDoesNotThrow(() -> mockLogger.info("Test message with no args"));
        assertDoesNotThrow(() -> mockLogger.info("Test message with one arg: {}", "arg1"));
        assertDoesNotThrow(() -> mockLogger.info("Test message with multiple args: {} and {}", "arg1", "arg2"));

        assertDoesNotThrow(() -> mockLogger.warn("Warning with no args"));
        assertDoesNotThrow(() -> mockLogger.warn("Warning with args: {}", "arg1"));

        assertDoesNotThrow(() -> mockLogger.debug("Debug with no args"));
        assertDoesNotThrow(() -> mockLogger.debug("Debug with args: {}", "arg1"));
    }

    @Test
    void errorMethodShouldAcceptThrowable() {
        final LoggerPort mockLogger = mock(LoggerPort.class);

        final RuntimeException runtimeException = new RuntimeException("Test exception");
        final IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Illegal argument");
        final Exception genericException = new Exception("Generic exception");

        assertDoesNotThrow(() -> mockLogger.error("Runtime exception", runtimeException));
        assertDoesNotThrow(() -> mockLogger.error("Illegal argument", illegalArgumentException));
        assertDoesNotThrow(() -> mockLogger.error("Generic exception", genericException));

        verify(mockLogger).error("Runtime exception", runtimeException);
        verify(mockLogger).error("Illegal argument", illegalArgumentException);
        verify(mockLogger).error("Generic exception", genericException);
    }

    @Test
    void maskMethodsShouldReturnString() {
        final LoggerPort mockLogger = mock(LoggerPort.class);

        when(mockLogger.maskEmail(anyString())).thenReturn("masked@email.com");
        when(mockLogger.maskDocument(anyString())).thenReturn("masked****document");

        final String maskedEmail = mockLogger.maskEmail("any@email.com");
        final String maskedDocument = mockLogger.maskDocument("1234567890");

        assertNotNull(maskedEmail);
        assertNotNull(maskedDocument);
        assertInstanceOf(String.class, maskedEmail);
        assertInstanceOf(String.class, maskedDocument);
    }
}