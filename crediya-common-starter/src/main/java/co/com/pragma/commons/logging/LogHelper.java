package co.com.pragma.commons.logging;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Clase de utilidad para operaciones relacionadas con logging seguro.
 * Proporciona métodos estáticos para logging y enmascarar información sensible
 * como correos electrónicos y números de documento antes de ser registrados.
 */
public final class LogHelper {
    /**
     * Patrón de expresión regular para validar direcciones de correo electrónico.
     * Utilizado internamente para determinar si un String es un correo electrónico válido
     * antes de intentar enmascararlo.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private static final Logger logger = Logger.getLogger(LogHelper.class.getName());

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private LogHelper() {
        // Private constructor for utility class
    }

    /**
     * Registra un mensaje informativo.
     *
     * @param message El mensaje a registrar. Puede contener placeholders ({}) para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    public static void info(String message, Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(formatMessage(message, args));
        }
    }

    /**
     * Registra un mensaje de advertencia.
     *
     * @param message El mensaje de advertencia a registrar. Puede contener placeholders ({}) para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    public static void warn(String message, Object... args) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.warning(formatMessage(message, args));
        }
    }

    /**
     * Registra un mensaje de depuración.
     *
     * @param message El mensaje de depuración a registrar. Puede contener placeholders ({}) para los argumentos.
     * @param args Argumentos opcionales que se usarán para formatear el mensaje.
     */
    public static void debug(String message, Object... args) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(formatMessage(message, args));
        }
    }

    /**
     * Registra un mensaje de error junto con una excepción.
     *
     * @param message El mensaje de error a registrar.
     * @param throwable La excepción asociada al error.
     */
    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Formatea un mensaje reemplazando placeholders {} con los argumentos proporcionados.
     * Método interno para dar formato a los mensajes de logging.
     *
     * @param message El mensaje con placeholders
     * @param args Los argumentos a insertar
     * @return El mensaje formateado
     */
    private static String formatMessage(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }

        String result = message;
        for (Object arg : args) {
            result = result.replaceFirst("\\{\\}", arg != null ? arg.toString() : "null");
        }
        return result;
    }

    /**
     * Enmascara una dirección de correo electrónico para logging seguro.
     * Si el correo electrónico es nulo o no tiene un formato válido, retorna "invalid-email-format".
     * De lo contrario, enmascara la parte local del correo, mostrando el primer y el último carácter,
     * y reemplazando los caracteres intermedios con asteriscos.
     * Ejemplo: "test.user@pragma.com.co" -> "t***r@pragma.com.co"
     *
     * @param email El correo electrónico a enmascarar. Puede ser nulo.
     * @return El correo electrónico enmascarado si es válido, o "invalid-email-format" en caso contrario.
     */
    public static String maskEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return "invalid-email-format";
        }
        int atIndex = email.indexOf('@');
        String localPart = email.substring(0, atIndex);
        if (localPart.length() <= 2) {
            return "***" + email.substring(atIndex);
        }
        // Muestra el primer y último caracter de la parte local para mayor seguridad, como indica el comentario.
        return localPart.charAt(0) + "***" + localPart.charAt(localPart.length() - 1) + email.substring(atIndex);
    }

    /**
     * Enmascara un número de documento de identidad para logging seguro.
     * Si el número de documento es nulo o tiene menos de 6 caracteres, retorna "***".
     * De lo contrario, muestra el primer dígito y el último dígito,
     * reemplazando los dígitos intermedios con asteriscos.
     * Para documentos de más de 6 caracteres, muestra los últimos 4 dígitos.
     * Ejemplo: "123456" -> "1****6", "1234567890" -> "1****7890"
     *
     * @param documentId El número de documento a enmascarar. Puede ser nulo.
     * @return El documento enmascarado si cumple con la longitud mínima, o "***" en caso contrario.
     */
    public static String maskDocument(String documentId) {
        if (documentId == null || documentId.length() < 6) {
            return "***";
        }

        // Para documentos de exactamente 6 caracteres, mostrar primer y último dígito
        if (documentId.length() == 6) {
            return documentId.charAt(0) + "****" + documentId.charAt(documentId.length() - 1);
        }

        // Para documentos más largos, mostrar primer dígito y últimos 4 dígitos
        return documentId.charAt(0) + "****" + documentId.substring(documentId.length() - 4);
    }
}