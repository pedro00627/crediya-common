package co.com.pragma.commons.config;

import co.com.pragma.commons.logging.LogHelperAdapter;
import co.com.pragma.model.log.gateways.LoggerPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración automática para los componentes comunes de CrediYa.
 * Esta clase define beans que se cargarán automáticamente en el contexto de Spring
 * cuando se utilice el starter 'crediya-common-starter'.
 */
@Configuration
public class PragmaCommonsAutoConfiguration {

    /**
     * Constructor por defecto para PragmaCommonsAutoConfiguration.
     */
    public PragmaCommonsAutoConfiguration() {
        // Constructor por defecto
    }

    /**
     * Proporciona una implementación de {@link LoggerPort} si no existe ya una en el contexto de Spring.
     * Utiliza {@link LogHelperAdapter} como implementación por defecto para el manejo de logs.
     *
     * @return Una instancia de {@link LoggerPort}.
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggerPort loggerPort() {
        return new LogHelperAdapter();
    }

    // Aquí puedes añadir más beans comunes en el futuro (ej: JWTUtil, etc.)
}
