package co.com.pragma.security.util;

import co.com.pragma.security.model.gateways.DataMaskerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class DataMaskerAdapterTest {

    private DataMaskerAdapter dataMaskerAdapter;

    @BeforeEach
    void setUp() {
        this.dataMaskerAdapter = new DataMaskerAdapter();
    }

    @Test
    void shouldImplementDataMaskerPort() {
        assertInstanceOf(DataMaskerPort.class, this.dataMaskerAdapter);
    }

    @Test
    void shouldBeAnnotatedWithComponent() {
        assertTrue(DataMaskerAdapter.class.isAnnotationPresent(Component.class));
    }

    @Test
    void shouldHaveDefaultConstructor() throws NoSuchMethodException {
        final Constructor<DataMaskerAdapter> constructor = DataMaskerAdapter.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPublic(constructor.getModifiers()));
        assertNotNull(constructor);
    }

    @Test
    void constructorShouldAllowInstantiation() {
        assertDoesNotThrow(() -> new DataMaskerAdapter());
    }

    @Test
    void maskEmailShouldDelegateToLogHelper() {
        // Test valid emails
        final String result1 = this.dataMaskerAdapter.maskEmail("test.user@pragma.com.co");
        assertNotNull(result1);
        assertTrue(result1.contains("@pragma.com.co"));
        assertTrue(result1.contains("*"));

        final String result2 = this.dataMaskerAdapter.maskEmail("john.doe@example.com");
        assertNotNull(result2);
        assertTrue(result2.contains("@example.com"));
        assertTrue(result2.contains("*"));
    }

    @Test
    void maskEmailShouldHandleShortLocalPart() {
        final String result1 = this.dataMaskerAdapter.maskEmail("a@example.com");
        assertEquals("***@example.com", result1);

        final String result2 = this.dataMaskerAdapter.maskEmail("ab@example.com");
        assertEquals("***@example.com", result2);

        final String result3 = this.dataMaskerAdapter.maskEmail("abc@example.com");
        assertNotNull(result3);
        assertTrue(result3.contains("@example.com"));
        assertTrue(result3.contains("*"));
    }

    @Test
    void maskEmailShouldHandleNullInput() {
        final String result = this.dataMaskerAdapter.maskEmail(null);
        assertEquals("***", result);
    }

    @Test
    void maskEmailShouldHandleInvalidEmailFormat() {
        final String result1 = this.dataMaskerAdapter.maskEmail("invalid-email");
        assertEquals("***", result1);

        final String result2 = this.dataMaskerAdapter.maskEmail("test@.com");
        assertEquals("***", result2);

        final String result3 = this.dataMaskerAdapter.maskEmail("test@com");
        assertEquals("***", result3);

        final String result4 = this.dataMaskerAdapter.maskEmail("test@example");
        assertEquals("***", result4);

        final String result5 = this.dataMaskerAdapter.maskEmail("test@example.c");
        assertEquals("***", result5);
    }

    @Test
    void maskEmailShouldHandleEmptyString() {
        final String result = this.dataMaskerAdapter.maskEmail("");
        assertEquals("***", result);
    }

    @Test
    void maskEmailShouldBeConsistent() {
        final String email = "test.user@example.com";
        final String result1 = this.dataMaskerAdapter.maskEmail(email);
        final String result2 = this.dataMaskerAdapter.maskEmail(email);

        assertEquals(result1, result2);
    }

    @Test
    void maskEmailShouldPreserveDomainPart() {
        final String result1 = this.dataMaskerAdapter.maskEmail("user@example.com");
        assertTrue(result1.endsWith("@example.com"));

        final String result2 = this.dataMaskerAdapter.maskEmail("admin@domain.org");
        assertTrue(result2.endsWith("@domain.org"));

        final String result3 = this.dataMaskerAdapter.maskEmail("test@subdomain.example.co.uk");
        assertTrue(result3.endsWith("@subdomain.example.co.uk"));
    }

    @Test
    void maskEmailShouldMaskLocalPart() {
        final String result = this.dataMaskerAdapter.maskEmail("testuser@example.com");
        assertNotNull(result);
        assertTrue(result.contains("*"));
        assertFalse(result.startsWith("testuser"));
        assertTrue(result.contains("@example.com"));
    }

    @Test
    void shouldSupportMultipleCalls() {
        final String email1 = "first@example.com";
        final String email2 = "second@domain.org";
        final String email3 = "third@test.net";

        final String result1 = this.dataMaskerAdapter.maskEmail(email1);
        final String result2 = this.dataMaskerAdapter.maskEmail(email2);
        final String result3 = this.dataMaskerAdapter.maskEmail(email3);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertTrue(result1.contains("@example.com"));
        assertTrue(result2.contains("@domain.org"));
        assertTrue(result3.contains("@test.net"));
    }

    @Test
    void shouldHandleSpecialCharactersInEmail() {
        final String result1 = this.dataMaskerAdapter.maskEmail("user.name+tag@example.com");
        assertNotNull(result1);
        assertTrue(result1.contains("@example.com"));

        final String result2 = this.dataMaskerAdapter.maskEmail("user_name@example-domain.com");
        assertNotNull(result2);
        assertTrue(result2.contains("@example-domain.com"));
    }

    @Test
    void shouldMaintainInterfaceContract() {
        // Verify that the class properly implements the interface
        final DataMaskerPort port = this.dataMaskerAdapter;

        assertNotNull(port.maskEmail("test@example.com"));
        assertEquals("***", port.maskEmail(null));
        assertEquals("***", port.maskEmail("invalid"));
    }

    @Test
    void shouldBeSpringComponent() {
        final Component annotation = DataMaskerAdapter.class.getAnnotation(Component.class);
        assertNotNull(annotation);

        // Component annotation should have default value (empty string)
        assertEquals("", annotation.value());
    }

    @Test
    void shouldHaveProperClassStructure() {
        assertTrue(java.lang.reflect.Modifier.isPublic(DataMaskerAdapter.class.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isAbstract(DataMaskerAdapter.class.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isFinal(DataMaskerAdapter.class.getModifiers()));
    }
}