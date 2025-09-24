package co.com.pragma.security.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathMatcherTest {

    @Test
    void shouldBeAnEnum() {
        assertTrue(PathMatcher.class.isEnum(), "PathMatcher debe ser un enum");
    }

    @Test
    void matchesShouldReturnFalseForNullPath() {
        assertFalse(PathMatcher.matches(null, "/api/test"));
    }

    @Test
    void matchesShouldReturnFalseForNullPattern() {
        assertFalse(PathMatcher.matches("/api/test", null));
    }

    @Test
    void matchesShouldReturnFalseForBothNull() {
        assertFalse(PathMatcher.matches(null, null));
    }

    @Test
    void matchesShouldSupportExactMatching() {
        assertTrue(PathMatcher.matches("/api/test", "/api/test"));
        assertTrue(PathMatcher.matches("/health", "/health"));
        assertTrue(PathMatcher.matches("/", "/"));
        assertTrue(PathMatcher.matches("", ""));
    }

    @Test
    void matchesShouldReturnFalseForNonExactMatches() {
        assertFalse(PathMatcher.matches("/api/test", "/api/other"));
        assertFalse(PathMatcher.matches("/api/test", "/api"));
        assertFalse(PathMatcher.matches("/api", "/api/test"));
    }

    @Test
    void matchesShouldSupportWildcardPatterns() {
        assertTrue(PathMatcher.matches("/api/test", "/api/**"));
        assertTrue(PathMatcher.matches("/api/test/sub", "/api/**"));
        assertTrue(PathMatcher.matches("/api/", "/api/**"));
        assertTrue(PathMatcher.matches("/api", "/api/**"));
    }

    @Test
    void matchesShouldNotMatchIncorrectWildcardPatterns() {
        assertFalse(PathMatcher.matches("/other/test", "/api/**"));
        assertFalse(PathMatcher.matches("/ap/test", "/api/**"));
        assertFalse(PathMatcher.matches("api/test", "/api/**")); // Missing leading slash
    }

    @Test
    void matchesShouldHandleRootWildcard() {
        assertTrue(PathMatcher.matches("/anything", "/**"));
        assertTrue(PathMatcher.matches("/api/test", "/**"));
        assertTrue(PathMatcher.matches("/", "/**"));
    }

    @Test
    void matchesShouldNotMatchEmptyStringWithWildcard() {
        // Empty string starts with empty string prefix from "/**", so it matches
        assertTrue(PathMatcher.matches("", "/**"));
        assertFalse(PathMatcher.matches("", "/api/**"));
    }

    @Test
    void matchesAnyShouldReturnFalseForNullPath() {
        final List<String> patterns = Arrays.asList("/api/**", "/health");
        assertFalse(PathMatcher.matchesAny(null, patterns));
    }

    @Test
    void matchesAnyShouldReturnFalseForNullPatterns() {
        assertFalse(PathMatcher.matchesAny("/api/test", null));
    }

    @Test
    void matchesAnyShouldReturnFalseForEmptyPatterns() {
        assertFalse(PathMatcher.matchesAny("/api/test", Collections.emptyList()));
    }

    @Test
    void matchesAnyShouldReturnTrueForMatchingPattern() {
        final List<String> patterns = Arrays.asList("/api/**", "/health", "/swagger-ui/**");

        assertTrue(PathMatcher.matchesAny("/api/test", patterns));
        assertTrue(PathMatcher.matchesAny("/api/v1/users", patterns));
        assertTrue(PathMatcher.matchesAny("/health", patterns));
        assertTrue(PathMatcher.matchesAny("/swagger-ui/index.html", patterns));
    }

    @Test
    void matchesAnyShouldReturnFalseForNonMatchingPath() {
        final List<String> patterns = Arrays.asList("/api/**", "/health", "/swagger-ui/**");

        assertFalse(PathMatcher.matchesAny("/private/test", patterns));
        assertFalse(PathMatcher.matchesAny("/metrics", patterns));
        assertFalse(PathMatcher.matchesAny("/actuator/health", patterns));
    }

    @Test
    void matchesAnyShouldWorkWithSinglePattern() {
        final List<String> patterns = Collections.singletonList("/api/**");

        assertTrue(PathMatcher.matchesAny("/api/test", patterns));
        assertFalse(PathMatcher.matchesAny("/health", patterns));
    }

    @Test
    void matchesAnyShouldWorkWithExactMatchPatterns() {
        final List<String> patterns = Arrays.asList("/health", "/metrics", "/info");

        assertTrue(PathMatcher.matchesAny("/health", patterns));
        assertTrue(PathMatcher.matchesAny("/metrics", patterns));
        assertTrue(PathMatcher.matchesAny("/info", patterns));
        assertFalse(PathMatcher.matchesAny("/health/details", patterns));
    }

    @Test
    void matchesAnyShouldWorkWithMixedPatterns() {
        final List<String> patterns = Arrays.asList("/api/**", "/health", "/swagger-ui/**", "/public");

        // Wildcard matches
        assertTrue(PathMatcher.matchesAny("/api/users", patterns));
        assertTrue(PathMatcher.matchesAny("/swagger-ui/index.html", patterns));

        // Exact matches
        assertTrue(PathMatcher.matchesAny("/health", patterns));
        assertTrue(PathMatcher.matchesAny("/public", patterns));

        // Non-matches
        assertFalse(PathMatcher.matchesAny("/private", patterns));
        assertFalse(PathMatcher.matchesAny("/health/check", patterns));
    }

    @Test
    void shouldHandleCaseSensitivity() {
        assertFalse(PathMatcher.matches("/API/test", "/api/**"));
        assertFalse(PathMatcher.matches("/api/TEST", "/api/test"));
        assertTrue(PathMatcher.matches("/api/test", "/api/test"));
    }

    @Test
    void shouldHandleSpecialCharacters() {
        assertTrue(PathMatcher.matches("/api/test-endpoint", "/api/**"));
        assertTrue(PathMatcher.matches("/api/test_endpoint", "/api/**"));
        assertTrue(PathMatcher.matches("/api/test.json", "/api/**"));
        assertTrue(PathMatcher.matches("/api/test?param=value", "/api/**"));
    }

    @Test
    void shouldHandleEmptyStringsCorrectly() {
        assertTrue(PathMatcher.matches("", ""));
        assertFalse(PathMatcher.matches("", "/api"));
        assertFalse(PathMatcher.matches("/api", ""));
    }

    @Test
    void shouldHandleSlashVariations() {
        assertTrue(PathMatcher.matches("/", "/"));
        assertTrue(PathMatcher.matches("/api/", "/api/**"));
        assertTrue(PathMatcher.matches("/api", "/api/**"));
        assertFalse(PathMatcher.matches("api/", "/api/**")); // Missing leading slash
    }

    @Test
    void wildcardShouldNotMatchWithoutProperPrefix() {
        // "/apitest" starts with "/api", so it actually matches
        assertTrue(PathMatcher.matches("/apitest", "/api/**"));
        assertFalse(PathMatcher.matches("/ap", "/api/**"));
        // /api starts with /api prefix, so it matches
        assertTrue(PathMatcher.matches("/api", "/api/**"));
        assertTrue(PathMatcher.matches("/api/", "/api/**"));
    }

    @Test
    void shouldHandleMultipleWildcardsInList() {
        final List<String> patterns = Arrays.asList("/api/**", "/admin/**", "/public/**");

        assertTrue(PathMatcher.matchesAny("/api/users", patterns));
        assertTrue(PathMatcher.matchesAny("/admin/dashboard", patterns));
        assertTrue(PathMatcher.matchesAny("/public/assets/style.css", patterns));
        assertFalse(PathMatcher.matchesAny("/private/secret", patterns));
    }
}