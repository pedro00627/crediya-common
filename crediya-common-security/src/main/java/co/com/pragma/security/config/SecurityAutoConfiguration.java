package co.com.pragma.security.config;

import co.com.pragma.model.security.PasswordEncryptor;
import co.com.pragma.security.impl.BCryptPasswordEncryptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Auto-configuración para componentes de seguridad comunes.
 * Proporciona beans por defecto que pueden ser sobrescritos por cada microservicio.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@Import({OpenApiConfig.class, SecurityHeadersFilter.class})
public class SecurityAutoConfiguration {

    /**
     * Proporciona un PasswordEncoder por defecto si no existe uno en el contexto.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Proporciona un PasswordEncryptor por defecto si no existe uno en el contexto.
     *
     * @param passwordEncoder el PasswordEncoder a usar
     * @return BCryptPasswordEncryptor instance
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncryptor passwordEncryptor(final PasswordEncoder passwordEncoder) {
        return new BCryptPasswordEncryptor(passwordEncoder);
    }
}