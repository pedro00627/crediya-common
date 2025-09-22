package co.com.pragma.security.model.gateways;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataMaskerPortTest {

    @Test
    void shouldBeAnInterface() {
        assertTrue(DataMaskerPort.class.isInterface());
    }

    @Test
    void shouldHaveCorrectMaskEmailMethod() throws NoSuchMethodException {
        DataMaskerPort.class.getMethod("maskEmail", String.class);
        // If no exception is thrown, the method exists with correct signature
    }

    @Test
    void shouldHaveExactlyOneMethod() {
        assertEquals(1, DataMaskerPort.class.getDeclaredMethods().length);
    }

    @Test
    void mockImplementationShouldWorkCorrectly() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        // Configure mock behavior
        when(mockMasker.maskEmail("test@example.com")).thenReturn("t***@example.com");
        when(mockMasker.maskEmail("user@domain.org")).thenReturn("u***@domain.org");

        // Test mock behavior
        assertEquals("t***@example.com", mockMasker.maskEmail("test@example.com"));
        assertEquals("u***@domain.org", mockMasker.maskEmail("user@domain.org"));

        // Verify method calls
        verify(mockMasker).maskEmail("test@example.com");
        verify(mockMasker).maskEmail("user@domain.org");
    }

    @Test
    void shouldAcceptNullInputInMockImplementation() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        when(mockMasker.maskEmail(null)).thenReturn("***");

        assertEquals("***", mockMasker.maskEmail(null));
        verify(mockMasker).maskEmail(null);
    }

    @Test
    void shouldAcceptEmptyStringInMockImplementation() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        when(mockMasker.maskEmail("")).thenReturn("***");

        assertEquals("***", mockMasker.maskEmail(""));
        verify(mockMasker).maskEmail("");
    }

    @Test
    void shouldAcceptInvalidEmailInMockImplementation() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        when(mockMasker.maskEmail("invalid-email")).thenReturn("***");

        assertEquals("***", mockMasker.maskEmail("invalid-email"));
        verify(mockMasker).maskEmail("invalid-email");
    }

    @Test
    void maskEmailMethodShouldReturnString() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        when(mockMasker.maskEmail(anyString())).thenReturn("masked@email.com");

        final String result = mockMasker.maskEmail("any@email.com");

        assertNotNull(result);
        assertInstanceOf(String.class, result);
    }

    @Test
    void shouldSupportMethodChaining() {
        final DataMaskerPort mockMasker = mock(DataMaskerPort.class);

        when(mockMasker.maskEmail("first@example.com")).thenReturn("f***@example.com");
        when(mockMasker.maskEmail("second@example.com")).thenReturn("s***@example.com");

        // Should be able to call method multiple times
        final String result1 = mockMasker.maskEmail("first@example.com");
        final String result2 = mockMasker.maskEmail("second@example.com");

        assertEquals("f***@example.com", result1);
        assertEquals("s***@example.com", result2);

        verify(mockMasker).maskEmail("first@example.com");
        verify(mockMasker).maskEmail("second@example.com");
    }

    @Test
    void shouldAllowMultipleImplementations() {
        final DataMaskerPort masker1 = mock(DataMaskerPort.class);
        final DataMaskerPort masker2 = mock(DataMaskerPort.class);

        when(masker1.maskEmail("test@example.com")).thenReturn("t***@example.com");
        when(masker2.maskEmail("test@example.com")).thenReturn("***@example.com");

        final String result1 = masker1.maskEmail("test@example.com");
        final String result2 = masker2.maskEmail("test@example.com");

        assertEquals("t***@example.com", result1);
        assertEquals("***@example.com", result2);
        assertNotEquals(result1, result2);
    }

    @Test
    void shouldBeImplementableByConcreteClasses() {
        // Anonymous implementation
        final DataMaskerPort anonymousMasker = new DataMaskerPort() {
            @Override
            public String maskEmail(final String email) {
                if (null == email || email.isEmpty()) {
                    return "***";
                }
                if (email.contains("@")) {
                    return "***@" + email.substring(email.indexOf('@') + 1);
                }
                return "***";
            }
        };

        assertEquals("***@example.com", anonymousMasker.maskEmail("test@example.com"));
        assertEquals("***", anonymousMasker.maskEmail(null));
        assertEquals("***", anonymousMasker.maskEmail(""));
        assertEquals("***", anonymousMasker.maskEmail("invalid"));
    }
}