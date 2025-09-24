package co.com.pragma.security.api;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class JWTPropertiesTest {

    @Test
    void shouldBeInstantiableAsMock() {
        // This is the only meaningful test for an interface:
        // verifying it can be mocked (meaning it's properly defined)
        mock(JWTProperties.class);
    }
}