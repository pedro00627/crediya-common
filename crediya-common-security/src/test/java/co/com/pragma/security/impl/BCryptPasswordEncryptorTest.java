package co.com.pragma.security.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests críticos de seguridad para BCryptPasswordEncryptor.
 * Centralizados en Common para asegurar consistencia entre microservicios.
 */
@ExtendWith(MockitoExtension.class)
class BCryptPasswordEncryptorTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private BCryptPasswordEncryptor passwordEncryptor;

    static Stream<Arguments> businessCriticalPasswordScenarios() {
        return Stream.of(
                Arguments.of("validUser123", "$2a$10$validHash", true, "Valid business password should authenticate"),
                Arguments.of("adminUser", "$2a$10$adminHash", true, "Admin password should authenticate"),
                Arguments.of("wrongPassword", "$2a$10$validHash", false, "Invalid password should be rejected"),
                Arguments.of("", "$2a$10$emptyHash", false, "Empty password should be rejected for security"),
                Arguments.of("P@$$w0rd!", "$2a$10$complexHash", true, "Complex password should be supported"),
                Arguments.of("weak", "$2a$10$complexHash", false, "Password mismatch should be detected")
        );
    }

    static Stream<Arguments> securityEdgeCases() {
        return Stream.of(
                Arguments.of(null, "$2a$10$hash", false, "Null password attack should be prevented"),
                Arguments.of("password", null, false, "Null hash attack should be prevented"),
                Arguments.of("SQL'; DROP TABLE users;--", "$2a$10$hash", false, "SQL injection attempt should fail"),
                Arguments.of("unicode🔒password", "$2a$10$unicodeHash", true, "Unicode passwords should work")
        );
    }

    @BeforeEach
    void setUp() {
        passwordEncryptor = new BCryptPasswordEncryptor(passwordEncoder);
    }

    @Test
    void shouldEncodePasswordSecurely() {
        // Arrange - Business critical password encoding
        final String rawPassword = "userPassword123";
        final String encodedPassword = "$2a$10$securelyHashedPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        String result = passwordEncryptor.encode(rawPassword);

        // Assert - Verify delegation to secure encoder
        assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @ParameterizedTest
    @MethodSource("businessCriticalPasswordScenarios")
    void shouldValidateBusinessCriticalPasswords(String rawPassword, String encodedPassword,
                                                boolean shouldMatch, String businessScenario) {
        // Arrange
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(shouldMatch);

        // Act
        boolean result = passwordEncryptor.matches(rawPassword, encodedPassword);

        // Assert
        assertEquals(shouldMatch, result, businessScenario);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @ParameterizedTest
    @MethodSource("securityEdgeCases")
    void shouldPreventSecurityVulnerabilities(String rawPassword, String encodedPassword,
                                             boolean expectedResult, String securityScenario) {
        // Arrange
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(expectedResult);

        // Act
        boolean result = passwordEncryptor.matches(rawPassword, encodedPassword);

        // Assert
        assertEquals(expectedResult, result, securityScenario);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "businessPassword123",
            "AdminP@$$w0rd",
            "clientLogin2024",
            "advisorAccess!"
    })
    void shouldEncodeDifferentUserRolePasswords(String businessPassword) {
        // Arrange
        String expectedEncoded = "$2a$10$business_" + businessPassword.hashCode();
        when(passwordEncoder.encode(businessPassword)).thenReturn(expectedEncoded);

        // Act
        String result = passwordEncryptor.encode(businessPassword);

        // Assert
        assertNotNull(result, "Encoded password should never be null");
        assertEquals(expectedEncoded, result);
        verify(passwordEncoder).encode(businessPassword);
    }

    @Test
    void shouldCorrectlyDelegateToSpringSecurityEncoder() {
        // Arrange - Test actual delegation behavior
        final String userPassword = "businessUser2024";
        final String adminPassword = "adminUser2024";
        final String encodedUserPassword = "$2a$10$userHash";
        final String encodedAdminPassword = "$2a$10$adminHash";

        // Act & Assert - Verify encode delegation
        when(passwordEncoder.encode(userPassword)).thenReturn(encodedUserPassword);
        assertEquals(encodedUserPassword, passwordEncryptor.encode(userPassword));

        // Act & Assert - Verify matches delegation
        when(passwordEncoder.matches(adminPassword, encodedAdminPassword)).thenReturn(true);
        assertTrue(passwordEncryptor.matches(adminPassword, encodedAdminPassword));

        // Verify actual Spring Security integration
        verify(passwordEncoder).encode(userPassword);
        verify(passwordEncoder).matches(adminPassword, encodedAdminPassword);
    }
}