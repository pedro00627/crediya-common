package co.com.pragma.security.util;

/**
 * Utilidad para el matching de paths en configuraciones de seguridad.
 * Centraliza la lógica de comparación de rutas para evitar duplicación.
 */
public enum PathMatcher {
    ;

    /**
     * Verifica si un path coincide con un patrón dado.
     * Soporta patrones con wildcards como "/api/v1/**"
     *
     * @param path    El path a verificar
     * @param pattern El patrón a comparar
     * @return true si el path coincide con el patrón
     */
    public static boolean matches(final String path, final String pattern) {
        if (null == path || null == pattern) {
            return false;
        }

        if (pattern.endsWith("/**")) {
            final String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }

        return path.equals(pattern);
    }

    /**
     * Verifica si un path está cubierto por alguno de los patrones en una lista.
     *
     * @param path     El path a verificar
     * @param patterns Lista de patrones a comparar
     * @return true si el path coincide con algún patrón
     */
    public static boolean matchesAny(final String path, final java.util.List<String> patterns) {
        if (null == path || null == patterns || patterns.isEmpty()) {
            return false;
        }

        return patterns.stream()
                .anyMatch(pattern -> PathMatcher.matches(path, pattern));
    }
}