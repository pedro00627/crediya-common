package co.com.pragma.commons.config;

import co.com.pragma.commons.logging.LogHelperAdapter;
import co.com.pragma.model.log.gateways.LoggerPort;
import org.junit.jupiter.api.Test;
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
}