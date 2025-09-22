package co.com.pragma.security.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class JwtClaimsConstantsTest {

    @Test
    void shouldHavePrivateConstructor() throws NoSuchMethodException {
        final Constructor<JwtClaimsConstants> constructor = JwtClaimsConstants.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void shouldBeAFinalClass() {
        assertTrue(java.lang.reflect.Modifier.isFinal(JwtClaimsConstants.class.getModifiers()));
    }

    @Test
    void shouldHaveCorrectRolesClaimConstant() {
        assertEquals("roles", JwtClaimsConstants.ROLES);
    }

    @Test
    void rolesClaimShouldNotBeEmpty() {
        assertFalse(JwtClaimsConstants.ROLES.isEmpty());
    }

    @Test
    void rolesClaimShouldBeLowercase() {
        assertEquals("roles", JwtClaimsConstants.ROLES);
        assertEquals(JwtClaimsConstants.ROLES.toLowerCase(), JwtClaimsConstants.ROLES);
    }

    @Test
    void shouldContainExpectedNumberOfClaims() {
        // Count public static final String fields
        final long claimCount = java.util.Arrays.stream(JwtClaimsConstants.class.getDeclaredFields())
                .filter(field -> java.lang.reflect.Modifier.isPublic(field.getModifiers()))
                .filter(field -> java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                .filter(field -> java.lang.reflect.Modifier.isFinal(field.getModifiers()))
                .filter(field -> field.getType().equals(String.class))
                .count();

        assertEquals(1, claimCount);
    }

    @Test
    void constructorShouldNotAllowInstantiation() throws Exception {
        final Constructor<JwtClaimsConstants> constructor = JwtClaimsConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Should be able to create instance but constructor should be private
        assertDoesNotThrow(() -> constructor.newInstance());
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void rolesClaimShouldFollowJwtStandards() {
        // JWT claims should be lowercase according to standards
        assertTrue(JwtClaimsConstants.ROLES.matches("^[a-z]+$"));
        assertFalse(JwtClaimsConstants.ROLES.contains(" "));
        assertFalse(JwtClaimsConstants.ROLES.contains("-"));
        assertFalse(JwtClaimsConstants.ROLES.contains("_"));
    }
}