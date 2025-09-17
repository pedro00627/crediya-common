package co.com.pragma.model.log.gateways;

/**
 * Interfaz que define las operaciones de logging y enmascaramiento de datos sensibles.
 * Proporciona métodos para registrar mensajes en diferentes niveles (info, warn, debug, error)
 * y para enmascarar información como correos electrónicos y números de documento
 * para logging seguro.
 */
public interface LoggerPort {
    /**
     * Registra un mensaje informativo.
     *
     * @param message El mensaje a registrar. Puede contener placeholders para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    void info(String message, Object... args);

    /**
     * Registra un mensaje de advertencia.
     *
     * @param message El mensaje de advertencia a registrar. Puede contener placeholders para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    void warn(String message, Object... args);

    /**
     * Registra un mensaje de depuración.
     *
     * @param message El mensaje de depuración a registrar. Puede contener placeholders para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    void debug(String message, Object... args);

    /**
     * Registra un mensaje de error junto con una excepción.
     *
     * @param message El mensaje de error a registrar.
     * @param throwable La excepción asociada al error.
     */
    void error(String message, Throwable throwable);

    /**
     * Enmascara una dirección de correo electrónico para logging seguro.
     * Por ejemplo, "test.user@example.com" podría ser enmascarado a "t***r@example.com".
     *
     * @param email El correo electrónico a enmascarar.
     * @return El correo electrónico enmascarado o un indicador de formato inválido si no es un correo válido.
     */
    String maskEmail(String email);

    /**
     * Enmascara un número de documento de identidad para logging seguro.
     * Por ejemplo, "1234567890" podría ser enmascarado a "1****7890".
     *
     * @param documentId El número de documento a enmascarar.
     * @return El documento enmascarado o un indicador de formato inválido si no cumple con la longitud mínima.
     */
    String maskDocument(String documentId);
}
