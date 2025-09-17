package co.com.pragma.security.api.config;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una regla de autorización para un endpoint específico.
 * Contiene el método HTTP, el patrón de la ruta,
 * y el nombre del bean de un gestor de autorización personalizado.
 */
public class AuthorizationRule {

    private HttpMethod method;
    private String path;
    private String managerBeanName;
    private List<String> roles = new ArrayList<>();

    /**
     * Constructor por defecto requerido por Spring Boot para el binding de propiedades.
     */
    public AuthorizationRule() {
        // Constructor por defecto requerido por Spring
    }

    /**
     * Constructor para reglas de autorización basadas en managers.
     *
     * @param method El método HTTP
     * @param path La ruta a proteger
     * @param managerBeanName El nombre del bean del authorization manager
     */
    public AuthorizationRule(HttpMethod method, String path, String managerBeanName) {
        this.method = method;
        this.path = path;
        this.managerBeanName = managerBeanName;
        this.roles = new ArrayList<>();
    }

    /**
     * Constructor completo para reglas de autorización.
     *
     * @param method El método HTTP
     * @param path La ruta a proteger
     * @param managerBeanName El nombre del bean del authorization manager
     * @param roles Los roles permitidos
     */
    public AuthorizationRule(HttpMethod method, String path, String managerBeanName, List<String> roles) {
        this.method = method;
        this.path = path;
        this.managerBeanName = managerBeanName;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    // Getters y setters
    /**
     * Obtiene el método HTTP de la regla de autorización.
     *
     * @return El método HTTP de la regla
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Establece el método HTTP para la regla de autorización.
     *
     * @param method El método HTTP a establecer
     */
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * Obtiene la ruta de la regla de autorización.
     *
     * @return La ruta de la regla
     */
    public String getPath() {
        return path;
    }

    /**
     * Establece la ruta para la regla de autorización.
     *
     * @param path La ruta a establecer
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Obtiene el nombre del bean del authorization manager.
     *
     * @return El nombre del bean del authorization manager
     */
    public String getManagerBeanName() {
        return managerBeanName;
    }

    /**
     * Establece el nombre del bean del authorization manager.
     *
     * @param managerBeanName El nombre del bean del authorization manager a establecer
     */
    public void setManagerBeanName(String managerBeanName) {
        this.managerBeanName = managerBeanName;
    }

    /**
     * Obtiene la lista de roles permitidos para esta regla.
     *
     * @return La lista de roles permitidos
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Establece la lista de roles permitidos para esta regla.
     *
     * @param roles La lista de roles permitidos a establecer
     */
    public void setRoles(List<String> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    // Métodos de acceso compatibles con el record anterior (mantener solo para retrocompatibilidad)
    /**
     * @return El método HTTP (compatibilidad con record)
     * @deprecated Usar {@link #getMethod()} en su lugar
     */
    @Deprecated(since = "0.0.53")
    public HttpMethod method() {
        return method;
    }

    /**
     * @return La ruta (compatibilidad con record)
     * @deprecated Usar {@link #getPath()} en su lugar
     */
    @Deprecated(since = "0.0.53")
    public String path() {
        return path;
    }

    /**
     * @return El nombre del bean del authorization manager (compatibilidad con record)
     * @deprecated Usar {@link #getManagerBeanName()} en su lugar
     */
    @Deprecated(since = "0.0.53")
    public String managerBeanName() {
        return managerBeanName;
    }

    /**
     * @return La lista de roles permitidos (compatibilidad con record)
     * @deprecated Usar {@link #getRoles()} en su lugar
     */
    @Deprecated(since = "0.0.53")
    public List<String> roles() {
        return roles;
    }
}
