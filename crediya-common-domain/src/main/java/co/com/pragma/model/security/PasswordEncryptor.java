package co.com.pragma.model.security;

/**
 * Puerto para encriptación de contraseñas.
 * Abstrae la lógica de encriptación para mantener el dominio limpio.
 * Utilizado por todos los microservicios para operaciones de seguridad.
 */
public interface PasswordEncryptor {

    /**
     * Encripta una contraseña en texto plano.
     *
     * @param rawPassword La contraseña en texto plano
     * @return La contraseña encriptada
     */
    String encode(String rawPassword);

    /**
     * Verifica si una contraseña en texto plano coincide con la encriptada.
     *
     * @param rawPassword La contraseña en texto plano
     * @param encodedPassword La contraseña encriptada
     * @return true si las contraseñas coinciden, false en caso contrario
     */
    boolean matches(String rawPassword, String encodedPassword);
}