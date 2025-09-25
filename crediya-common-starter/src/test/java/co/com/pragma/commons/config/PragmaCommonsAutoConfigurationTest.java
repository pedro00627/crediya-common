package co.com.pragma.commons.config;

import co.com.pragma.commons.logging.LogHelperAdapter;
import co.com.pragma.model.log.gateways.LoggerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PragmaCommonsAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(PragmaCommonsAutoConfiguration.class));

    @Test
    void shouldCreateLoggerPortBeanWhenNotPresent() {
        this.contextRunner.run(context -> {
            assertThat(context).hasSingleBean(LoggerPort.class);
            assertThat(context).getBean(LoggerPort.class).isInstanceOf(LogHelperAdapter.class);
        });
    }

    @Test
    void shouldNotCreateLoggerPortBeanWhenAlreadyPresent() {
        LoggerPort mockLoggerPort = mock(LoggerPort.class);

        this.contextRunner
                .withBean(LoggerPort.class, () -> mockLoggerPort)
                .run(context -> {
                    assertThat(context).hasSingleBean(LoggerPort.class);
                    assertThat(context.getBean(LoggerPort.class)).isSameAs(mockLoggerPort);
                });
    }

    @Test
    void shouldHaveConfigurationAnnotation() {
        assertThat(PragmaCommonsAutoConfiguration.class).hasAnnotation(Configuration.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@business.com",
            "advisor@crediya.com",
            "admin@system.com"
    })
    void shouldProvideBusinessLoggerWithMaskingForSensitiveData(String sensitiveEmail) {
        this.contextRunner.run(context -> {
            // Arrange
            LoggerPort logger = context.getBean(LoggerPort.class);

            // Act - Test business data masking for different user types
            String maskedEmail = logger.maskEmail(sensitiveEmail);
            String maskedDocument = logger.maskDocument("123456789");

            // Assert - Should protect sensitive business data
            assertThat(maskedEmail).isNotNull();
            assertThat(maskedDocument).isNotNull();
            assertThat(maskedEmail).isNotEqualTo(sensitiveEmail);
            assertThat(maskedDocument).isNotEqualTo("123456789");
        });
    }

    @Test
    void shouldSupportBusinessLoggingScenarios() {
        this.contextRunner.run(context -> {
            // Arrange
            LoggerPort logger = context.getBean(LoggerPort.class);

            // Act & Assert - Should not throw exceptions for business logging
            logger.info("User {} logged in successfully", "business-user");
            logger.debug("Processing loan application for amount: {}", 50000);
            logger.warn("Business rule validation warning for user: {}", "test-user");
            logger.error("Business error processing application", new RuntimeException("Test error"));

            // If we reach here, all business logging scenarios work
            assertThat(logger).isNotNull();
        });
    }
}