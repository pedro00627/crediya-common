package co.com.pragma.security.config;

import co.com.pragma.model.security.PasswordEncryptor;
import co.com.pragma.security.impl.BCryptPasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests críticos de auto-configuración de seguridad.
 * Verifica que los componentes de seguridad se configuran correctamente en todos los microservicios.
 */
class SecurityAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class));

    static Stream<Arguments> securityBeanValidationScenarios() {
        return Stream.of(
                Arguments.of("PasswordEncoder should be BCryptPasswordEncoder",
                        PasswordEncoder.class, BCryptPasswordEncoder.class),
                Arguments.of("PasswordEncryptor should be BCryptPasswordEncryptor",
                        PasswordEncryptor.class, BCryptPasswordEncryptor.class),
                Arguments.of("SecurityProperties should be available",
                        SecurityProperties.class, SecurityProperties.class)
        );
    }

    @Test
    void shouldAutoConfigureSecurityComponents() {
        contextRunner.run(context -> {
            // Assert - Verify all critical security components are available
            assertThat(context).hasSingleBean(PasswordEncoder.class);
            assertThat(context).hasSingleBean(PasswordEncryptor.class);
            assertThat(context).hasSingleBean(SecurityProperties.class);

            // Verify correct implementations
            assertThat(context.getBean(PasswordEncoder.class))
                    .isInstanceOf(BCryptPasswordEncoder.class);
            assertThat(context.getBean(PasswordEncryptor.class))
                    .isInstanceOf(BCryptPasswordEncryptor.class);
        });
    }

    @ParameterizedTest
    @MethodSource("securityBeanValidationScenarios")
    void shouldConfigureCorrectSecurityBeans(String scenario, Class<?> beanType, Class<?> expectedImplementation) {
        contextRunner.run(context -> {
            // Assert
            assertThat(context).hasSingleBean(beanType);
            assertThat(context.getBean(beanType)).isInstanceOf(expectedImplementation);
        });
    }

    @Test
    void shouldNotOverrideExistingPasswordEncoder() {
        contextRunner
                .withBean("customPasswordEncoder", PasswordEncoder.class, () -> new CustomPasswordEncoder())
                .run(context -> {
                    // Assert - Should not override existing bean
                    assertThat(context).hasSingleBean(PasswordEncoder.class);
                    assertThat(context.getBean(PasswordEncoder.class))
                            .isInstanceOf(CustomPasswordEncoder.class);
                });
    }

    @Test
    void shouldNotOverrideExistingPasswordEncryptor() {
        contextRunner
                .withBean("customPasswordEncryptor", PasswordEncryptor.class, () -> new CustomPasswordEncryptor())
                .run(context -> {
                    // Assert - Should not override existing bean
                    assertThat(context).hasSingleBean(PasswordEncryptor.class);
                    assertThat(context.getBean(PasswordEncryptor.class))
                            .isInstanceOf(CustomPasswordEncryptor.class);
                });
    }

    @Test
    void shouldConfigureSecurityPropertiesWithDefaults() {
        contextRunner
                .withPropertyValues(
                        "jwt.secret=testSecret",
                        "jwt.expiration=3600000",
                        "jwt.excludedPaths[0]=/auth/login"
                )
                .run(context -> {
                    // Assert - Verify properties are bound correctly
                    assertThat(context).hasSingleBean(SecurityProperties.class);

                    SecurityProperties properties = context.getBean(SecurityProperties.class);
                    assertThat(properties.secret()).isEqualTo("testSecret");
                    assertThat(properties.expiration()).isEqualTo(3600000L);
                    assertThat(properties.excludedPaths()).containsExactly("/auth/login");
                });
    }

    @Test
    void shouldIntegratePasswordEncoderWithPasswordEncryptor() {
        contextRunner.run(context -> {
            // Arrange
            PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
            PasswordEncryptor encryptor = context.getBean(PasswordEncryptor.class);

            // Act - Test integration
            String rawPassword = "businessTestPassword123";
            String encoded = encryptor.encode(rawPassword);

            // Assert - Verify they work together
            assertThat(encoded).isNotNull();
            assertThat(encryptor.matches(rawPassword, encoded)).isTrue();

            // Verify it's using the same encoder internally
            assertThat(encoder.matches(rawPassword, encoded)).isTrue();
        });
    }

    // Test helper classes
    private static class CustomPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return "custom_" + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encodedPassword.equals("custom_" + rawPassword);
        }
    }

    private static class CustomPasswordEncryptor implements PasswordEncryptor {
        @Override
        public String encode(String rawPassword) {
            return "custom_encrypted_" + rawPassword;
        }

        @Override
        public boolean matches(String rawPassword, String encodedPassword) {
            return encodedPassword.equals("custom_encrypted_" + rawPassword);
        }
    }
}