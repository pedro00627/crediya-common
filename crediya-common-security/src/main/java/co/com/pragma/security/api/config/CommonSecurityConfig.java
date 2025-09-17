package co.com.pragma.security.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración principal para el módulo de seguridad común.
 * Habilita el escaneo de componentes dentro del paquete 'co.com.pragma.security'.
 * Esta clase debe ser importada en la aplicación principal de cada microservicio.
 * <p>
 * IMPORTANTE: Esta configuración NO maneja propiedades específicas.
 * Cada microservicio debe definir sus propias SecurityProperties y SecurityRulesProperties.
 */
@Configuration
@ComponentScan("co.com.pragma.security")
public class CommonSecurityConfig {

    /**
     * Constructor por defecto para la clase de configuración.
     */
    public CommonSecurityConfig() {
        // Constructor por defecto
    }
}
