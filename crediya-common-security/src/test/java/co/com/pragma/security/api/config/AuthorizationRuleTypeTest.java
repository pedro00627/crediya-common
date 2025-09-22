package co.com.pragma.security.api.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationRuleTypeTest {

    @Test
    void shouldBeAnEnum() {
        assertTrue(AuthorizationRuleType.class.isEnum());
    }

    @Test
    void shouldHaveExactlyFourValues() {
        final AuthorizationRuleType[] values = AuthorizationRuleType.values();
        assertEquals(4, values.length);
    }

    @Test
    void shouldContainExpectedValues() {
        final AuthorizationRuleType[] values = AuthorizationRuleType.values();

        assertTrue(this.containsValue(values, AuthorizationRuleType.ROLE));
        assertTrue(this.containsValue(values, AuthorizationRuleType.MANAGER));
        assertTrue(this.containsValue(values, AuthorizationRuleType.PERMIT_ALL));
        assertTrue(this.containsValue(values, AuthorizationRuleType.DENY_ALL));
    }

    @Test
    void shouldHaveCorrectValueNames() {
        assertEquals("ROLE", AuthorizationRuleType.ROLE.name());
        assertEquals("MANAGER", AuthorizationRuleType.MANAGER.name());
        assertEquals("PERMIT_ALL", AuthorizationRuleType.PERMIT_ALL.name());
        assertEquals("DENY_ALL", AuthorizationRuleType.DENY_ALL.name());
    }

    @Test
    void shouldSupportValueOfMethod() {
        assertEquals(AuthorizationRuleType.ROLE, AuthorizationRuleType.valueOf("ROLE"));
        assertEquals(AuthorizationRuleType.MANAGER, AuthorizationRuleType.valueOf("MANAGER"));
        assertEquals(AuthorizationRuleType.PERMIT_ALL, AuthorizationRuleType.valueOf("PERMIT_ALL"));
        assertEquals(AuthorizationRuleType.DENY_ALL, AuthorizationRuleType.valueOf("DENY_ALL"));
    }

    @Test
    void shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            AuthorizationRuleType.valueOf("INVALID");
        });
    }

    @Test
    void shouldMaintainEnumOrdering() {
        final AuthorizationRuleType[] values = AuthorizationRuleType.values();

        assertEquals(AuthorizationRuleType.ROLE, values[0]);
        assertEquals(AuthorizationRuleType.MANAGER, values[1]);
        assertEquals(AuthorizationRuleType.PERMIT_ALL, values[2]);
        assertEquals(AuthorizationRuleType.DENY_ALL, values[3]);
    }

    @Test
    void shouldSupportOrdinalValues() {
        assertEquals(0, AuthorizationRuleType.ROLE.ordinal());
        assertEquals(1, AuthorizationRuleType.MANAGER.ordinal());
        assertEquals(2, AuthorizationRuleType.PERMIT_ALL.ordinal());
        assertEquals(3, AuthorizationRuleType.DENY_ALL.ordinal());
    }

    @Test
    void shouldSupportEquality() {
        assertEquals(AuthorizationRuleType.ROLE, AuthorizationRuleType.ROLE);
        assertNotEquals(AuthorizationRuleType.ROLE, AuthorizationRuleType.MANAGER);

        // Test with valueOf
        assertEquals(AuthorizationRuleType.PERMIT_ALL, AuthorizationRuleType.valueOf("PERMIT_ALL"));
    }

    @Test
    void shouldSupportHashCode() {
        assertEquals(AuthorizationRuleType.ROLE.hashCode(), AuthorizationRuleType.ROLE.hashCode());
        assertNotEquals(AuthorizationRuleType.ROLE.hashCode(), AuthorizationRuleType.MANAGER.hashCode());
    }

    @Test
    void shouldSupportToString() {
        assertEquals("ROLE", AuthorizationRuleType.ROLE.toString());
        assertEquals("MANAGER", AuthorizationRuleType.MANAGER.toString());
        assertEquals("PERMIT_ALL", AuthorizationRuleType.PERMIT_ALL.toString());
        assertEquals("DENY_ALL", AuthorizationRuleType.DENY_ALL.toString());
    }

    @Test
    void shouldHaveDistinctValues() {
        final AuthorizationRuleType[] values = AuthorizationRuleType.values();

        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals(values[i], values[j],
                    "Values at index " + i + " and " + j + " should be different");
            }
        }
    }

    @Test
    void shouldFollowNamingConvention() {
        for (final AuthorizationRuleType type : AuthorizationRuleType.values()) {
            final String name = type.name();
            assertEquals(name.toUpperCase(), name, "Enum name should be uppercase: " + name);
            assertTrue(name.matches("^[A-Z_]+$"), "Enum name should only contain uppercase letters and underscores: " + name);
        }
    }

    // Helper method
    private boolean containsValue(final AuthorizationRuleType[] values, final AuthorizationRuleType target) {
        for (final AuthorizationRuleType value : values) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }
}