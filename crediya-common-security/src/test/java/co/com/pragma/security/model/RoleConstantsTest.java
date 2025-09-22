package co.com.pragma.security.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class RoleConstantsTest {

    @Test
    void shouldHavePrivateConstructor() throws NoSuchMethodException {
        final Constructor<RoleConstants> constructor = RoleConstants.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void shouldBeAFinalClass() {
        assertTrue(java.lang.reflect.Modifier.isFinal(RoleConstants.class.getModifiers()));
    }

    @Test
    void shouldHaveCorrectRoleConstants() {
        assertEquals("ADMIN", RoleConstants.ADMIN);
        assertEquals("ADVISOR", RoleConstants.ADVISOR);
        assertEquals("CLIENT", RoleConstants.CLIENT);
        assertEquals("ROLE_ANONYMOUS", RoleConstants.ROLE_ANONYMOUS);
    }

    @Test
    void allRoleConstantsShouldNotBeEmpty() {
        assertFalse(RoleConstants.ADMIN.isEmpty());
        assertFalse(RoleConstants.ADVISOR.isEmpty());
        assertFalse(RoleConstants.CLIENT.isEmpty());
        assertFalse(RoleConstants.ROLE_ANONYMOUS.isEmpty());
    }

    @Test
    void roleConstantsShouldBeUpperCase() {
        assertEquals(RoleConstants.ADMIN.toUpperCase(), RoleConstants.ADMIN);
        assertEquals(RoleConstants.ADVISOR.toUpperCase(), RoleConstants.ADVISOR);
        assertEquals(RoleConstants.CLIENT.toUpperCase(), RoleConstants.CLIENT);
        assertEquals(RoleConstants.ROLE_ANONYMOUS.toUpperCase(), RoleConstants.ROLE_ANONYMOUS);
    }

    @Test
    void anonymousRoleShouldFollowSpringSecurityConvention() {
        assertTrue(RoleConstants.ROLE_ANONYMOUS.startsWith("ROLE_"));
    }

    @Test
    void roleConstantsShouldBeUnique() {
        assertNotEquals(RoleConstants.ADMIN, RoleConstants.ADVISOR);
        assertNotEquals(RoleConstants.ADMIN, RoleConstants.CLIENT);
        assertNotEquals(RoleConstants.ADMIN, RoleConstants.ROLE_ANONYMOUS);
        assertNotEquals(RoleConstants.ADVISOR, RoleConstants.CLIENT);
        assertNotEquals(RoleConstants.ADVISOR, RoleConstants.ROLE_ANONYMOUS);
        assertNotEquals(RoleConstants.CLIENT, RoleConstants.ROLE_ANONYMOUS);
    }

    @Test
    void shouldContainExpectedNumberOfRoles() {
        // Count public static final String fields
        final long roleCount = java.util.Arrays.stream(RoleConstants.class.getDeclaredFields())
                .filter(field -> java.lang.reflect.Modifier.isPublic(field.getModifiers()))
                .filter(field -> java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                .filter(field -> java.lang.reflect.Modifier.isFinal(field.getModifiers()))
                .filter(field -> field.getType().equals(String.class))
                .count();

        assertEquals(4, roleCount);
    }

    @Test
    void constructorShouldNotAllowInstantiation() throws Exception {
        final Constructor<RoleConstants> constructor = RoleConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Should be able to create instance but constructor should be private
        assertDoesNotThrow(() -> constructor.newInstance());
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }
}