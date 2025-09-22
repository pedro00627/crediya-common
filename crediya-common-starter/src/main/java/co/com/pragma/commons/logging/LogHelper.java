package co.com.pragma.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Clase de utilidad para operaciones relacionadas con logging seguro.
 * Proporciona métodos estáticos para logging y enmascarar información sensible
 * como correos electrónicos y números de documento antes de ser registrados.
 */
public enum LogHelper {
    ;

    private static final Logger logger = LogManager.getLogger(LogHelper.class);

    // Constantes de enmascaramiento
    private static final char MASK_CHAR = '*';
    private static final String MASK_3_CHARS = String.valueOf(LogHelper.MASK_CHAR).repeat(3);
    private static final String INVALID_EMAIL_FORMAT = LogHelper.MASK_3_CHARS;
    private static final String EMAIL_MASK_REPLACEMENT = "$1" + LogHelper.MASK_3_CHARS + "$3";
    // Constantes para enmascaramiento de documento
    private static final String INVALID_DOCUMENT_FORMAT = LogHelper.MASK_3_CHARS;
    private static final String MASK_4_CHARS = String.valueOf(LogHelper.MASK_CHAR).repeat(4);
    // Constantes para enmascaramiento de email
    private static final char AT_SIGN = '@';
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private static final Pattern EMAIL_MASK_PATTERN = Pattern.compile("(^.)(.*)(.@.*$)");
    private static final int MIN_DOCUMENT_LENGTH = 6;

    /**
     * Registra un mensaje a nivel INFO.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void info(final String message, final Object... args) {
        if (LogHelper.logger.isInfoEnabled()) {
            LogHelper.logger.info(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel WARN.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void warn(final String message, final Object... args) {
        if (LogHelper.logger.isWarnEnabled()) {
            LogHelper.logger.warn(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel DEBUG.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void debug(final String message, final Object... args) {
        if (LogHelper.logger.isDebugEnabled()) {
            LogHelper.logger.debug(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel ERROR con una excepción.
     *
     * @param message   El mensaje de error.
     * @param throwable La excepción a registrar.
     */
    public static void error(final String message, final Throwable throwable) {
        LogHelper.logger.error(message, throwable);
    }

    /**
     * Enmascara una dirección de correo electrónico para un logging seguro.
     *
     * @param email El correo electrónico a enmascarar.
     * @return El correo electrónico enmascarado o una máscara si el formato es inválido.
     */
    public static String maskEmail(final String email) {
        return Optional.ofNullable(email)
                .filter(e -> LogHelper.EMAIL_PATTERN.matcher(e).matches())
                .map(LogHelper::performEmailMask)
                .orElse(LogHelper.INVALID_EMAIL_FORMAT);
    }

    private static String performEmailMask(final String email) {
        final int atIndex = email.indexOf(LogHelper.AT_SIGN);
        final String localPart = email.substring(0, atIndex);

        if (2 >= localPart.length()) {
            return LogHelper.MASK_3_CHARS + email.substring(atIndex);
        }
        return LogHelper.EMAIL_MASK_PATTERN.matcher(email).replaceAll(LogHelper.EMAIL_MASK_REPLACEMENT);
    }

    /**
     * Enmascara un número de documento para un logging seguro.
     *
     * @param documentId El número de documento a enmascarar.
     * @return El documento enmascarado o una máscara si no cumple la longitud mínima.
     */
    public static String maskDocument(final String documentId) {
        return Optional.ofNullable(documentId)
                .filter(doc -> MIN_DOCUMENT_LENGTH <= doc.length())
                .map(LogHelper::performDocumentMask)
                .orElse(LogHelper.INVALID_DOCUMENT_FORMAT);
    }

    private static String performDocumentMask(final String documentId) {
        if (MIN_DOCUMENT_LENGTH == documentId.length()) {
            return documentId.charAt(0) + LogHelper.MASK_4_CHARS + documentId.charAt(documentId.length() - 1);
        }
        return documentId.charAt(0) + LogHelper.MASK_4_CHARS + documentId.substring(documentId.length() - 4);
    }
}
