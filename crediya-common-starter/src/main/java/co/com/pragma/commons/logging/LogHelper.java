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
public final class LogHelper {

    private static final Logger logger = LogManager.getLogger(LogHelper.class);

    // Constantes de enmascaramiento
    private static final char MASK_CHAR = '*';
    private static final String MASK_3_CHARS = String.valueOf(MASK_CHAR).repeat(3);
    private static final String INVALID_EMAIL_FORMAT = MASK_3_CHARS;
    private static final String EMAIL_MASK_REPLACEMENT = "$1" + MASK_3_CHARS + "$3";
    // Constantes para enmascaramiento de documento
    private static final String INVALID_DOCUMENT_FORMAT = MASK_3_CHARS;
    private static final String MASK_4_CHARS = String.valueOf(MASK_CHAR).repeat(4);
    // Constantes para enmascaramiento de email
    private static final char AT_SIGN = '@';
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private static final Pattern EMAIL_MASK_PATTERN = Pattern.compile("(^.)(.*)(.@.*$)");
    private static final int MIN_DOCUMENT_LENGTH = 6;

    private LogHelper() {
        // Private constructor for utility class
    }

    /**
     * Registra un mensaje a nivel INFO.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void info(String message, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel WARN.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void warn(String message, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel DEBUG.
     *
     * @param message El mensaje a registrar, puede contener placeholders `{}`.
     * @param args    Los argumentos para los placeholders.
     */
    public static void debug(String message, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, args);
        }
    }

    /**
     * Registra un mensaje a nivel ERROR con una excepción.
     *
     * @param message   El mensaje de error.
     * @param throwable La excepción a registrar.
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Enmascara una dirección de correo electrónico para un logging seguro.
     *
     * @param email El correo electrónico a enmascarar.
     * @return El correo electrónico enmascarado o una máscara si el formato es inválido.
     */
    public static String maskEmail(String email) {
        return Optional.ofNullable(email)
                .filter(e -> EMAIL_PATTERN.matcher(e).matches())
                .map(LogHelper::performEmailMask)
                .orElse(INVALID_EMAIL_FORMAT);
    }

    private static String performEmailMask(String email) {
        int atIndex = email.indexOf(AT_SIGN);
        String localPart = email.substring(0, atIndex);

        if (localPart.length() <= 2) {
            return MASK_3_CHARS + email.substring(atIndex);
        }
        return EMAIL_MASK_PATTERN.matcher(email).replaceAll(EMAIL_MASK_REPLACEMENT);
    }

    /**
     * Enmascara un número de documento para un logging seguro.
     *
     * @param documentId El número de documento a enmascarar.
     * @return El documento enmascarado o una máscara si no cumple la longitud mínima.
     */
    public static String maskDocument(String documentId) {
        return Optional.ofNullable(documentId)
                .filter(doc -> doc.length() >= MIN_DOCUMENT_LENGTH)
                .map(LogHelper::performDocumentMask)
                .orElse(INVALID_DOCUMENT_FORMAT);
    }

    private static String performDocumentMask(String documentId) {
        if (documentId.length() == MIN_DOCUMENT_LENGTH) {
            return documentId.charAt(0) + MASK_4_CHARS + documentId.charAt(documentId.length() - 1);
        }
        return documentId.charAt(0) + MASK_4_CHARS + documentId.substring(documentId.length() - 4);
    }
}
