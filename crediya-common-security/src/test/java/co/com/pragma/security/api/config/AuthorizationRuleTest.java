package co.com.pragma.security.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorizationRuleTest {

    @Test
    void shouldHaveDefaultConstructor() {
        assertDoesNotThrow(() -> new AuthorizationRule());
    }

    @Test
    void defaultConstructorShouldInitializeWithDefaults() {
        final AuthorizationRule rule = new AuthorizationRule();

        assertNull(rule.getMethod());
        assertNull(rule.getPath());
        assertNull(rule.getManagerBeanName());
        assertNotNull(rule.getRoles());
        assertTrue(rule.getRoles().isEmpty());
    }

    @Test
    void constructorWithMethodPathAndManagerShouldInitializeCorrectly() {
        final HttpMethod method = HttpMethod.GET;
        final String path = "/api/test";
        final String managerBeanName = "testManager";

        final AuthorizationRule rule = new AuthorizationRule(method, path, managerBeanName);

        assertEquals(method, rule.getMethod());
        assertEquals(path, rule.getPath());
        assertEquals(managerBeanName, rule.getManagerBeanName());
        assertNotNull(rule.getRoles());
        assertTrue(rule.getRoles().isEmpty());
    }

    @Test
    void fullConstructorShouldInitializeCorrectly() {
        final HttpMethod method = HttpMethod.POST;
        final String path = "/api/admin";
        final String managerBeanName = "adminManager";
        final List<String> roles = Arrays.asList("ADMIN", "USER");

        final AuthorizationRule rule = new AuthorizationRule(method, path, managerBeanName, roles);

        assertEquals(method, rule.getMethod());
        assertEquals(path, rule.getPath());
        assertEquals(managerBeanName, rule.getManagerBeanName());
        assertEquals(roles, rule.getRoles());
        assertEquals(2, rule.getRoles().size());
        assertTrue(rule.getRoles().contains("ADMIN"));
        assertTrue(rule.getRoles().contains("USER"));
    }

    @Test
    void fullConstructorWithNullRolesShouldInitializeEmptyList() {
        final HttpMethod method = HttpMethod.DELETE;
        final String path = "/api/delete";
        final String managerBeanName = "deleteManager";

        final AuthorizationRule rule = new AuthorizationRule(method, path, managerBeanName, null);

        assertEquals(method, rule.getMethod());
        assertEquals(path, rule.getPath());
        assertEquals(managerBeanName, rule.getManagerBeanName());
        assertNotNull(rule.getRoles());
        assertTrue(rule.getRoles().isEmpty());
    }

    @Test
    void setMethodShouldUpdateMethod() {
        final AuthorizationRule rule = new AuthorizationRule();
        final HttpMethod method = HttpMethod.PUT;

        rule.setMethod(method);

        assertEquals(method, rule.getMethod());
    }

    @Test
    void setMethodWithNullShouldAcceptNull() {
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setMethod(HttpMethod.GET);

        rule.setMethod(null);

        assertNull(rule.getMethod());
    }

    @Test
    void setPathShouldUpdatePath() {
        final AuthorizationRule rule = new AuthorizationRule();
        final String path = "/api/update";

        rule.setPath(path);

        assertEquals(path, rule.getPath());
    }

    @Test
    void setPathWithNullShouldAcceptNull() {
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/initial/path");

        rule.setPath(null);

        assertNull(rule.getPath());
    }

    @Test
    void setManagerBeanNameShouldUpdateManagerBeanName() {
        final AuthorizationRule rule = new AuthorizationRule();
        final String managerBeanName = "newManager";

        rule.setManagerBeanName(managerBeanName);

        assertEquals(managerBeanName, rule.getManagerBeanName());
    }

    @Test
    void setManagerBeanNameWithNullShouldAcceptNull() {
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setManagerBeanName("initialManager");

        rule.setManagerBeanName(null);

        assertNull(rule.getManagerBeanName());
    }

    @Test
    void setRolesShouldUpdateRoles() {
        final AuthorizationRule rule = new AuthorizationRule();
        final List<String> roles = Arrays.asList("ROLE1", "ROLE2", "ROLE3");

        rule.setRoles(roles);

        assertEquals(roles, rule.getRoles());
        assertEquals(3, rule.getRoles().size());
        assertTrue(rule.getRoles().contains("ROLE1"));
        assertTrue(rule.getRoles().contains("ROLE2"));
        assertTrue(rule.getRoles().contains("ROLE3"));
    }

    @Test
    void setRolesWithNullShouldInitializeEmptyList() {
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setRoles(List.of("INITIAL_ROLE"));

        rule.setRoles(null);

        assertNotNull(rule.getRoles());
        assertTrue(rule.getRoles().isEmpty());
    }

    @Test
    void setRolesWithEmptyListShouldAcceptEmptyList() {
        final AuthorizationRule rule = new AuthorizationRule();
        final List<String> emptyRoles = new ArrayList<>();

        rule.setRoles(emptyRoles);

        assertEquals(emptyRoles, rule.getRoles());
        assertTrue(rule.getRoles().isEmpty());
    }

    @Test
    void getRolesShouldReturnMutableList() {
        final AuthorizationRule rule = new AuthorizationRule();
        final List<String> initialRoles = List.of("ROLE1");
        rule.setRoles(new ArrayList<>(initialRoles));

        final List<String> retrievedRoles = rule.getRoles();
        retrievedRoles.add("ROLE2");

        assertEquals(2, rule.getRoles().size());
        assertTrue(rule.getRoles().contains("ROLE1"));
        assertTrue(rule.getRoles().contains("ROLE2"));
    }

    @Test
    void methodDeprecatedMethodShouldReturnSameAsGetMethod() {
        final AuthorizationRule rule = new AuthorizationRule();
        final HttpMethod method = HttpMethod.PATCH;
        rule.setMethod(method);

        @SuppressWarnings("deprecation") final HttpMethod deprecatedResult = rule.method();

        assertEquals(method, deprecatedResult);
        assertEquals(rule.getMethod(), deprecatedResult);
    }

    @Test
    void pathDeprecatedMethodShouldReturnSameAsGetPath() {
        final AuthorizationRule rule = new AuthorizationRule();
        final String path = "/api/deprecated";
        rule.setPath(path);

        @SuppressWarnings("deprecation") final String deprecatedResult = rule.path();

        assertEquals(path, deprecatedResult);
        assertEquals(rule.getPath(), deprecatedResult);
    }

    @Test
    void managerBeanNameDeprecatedMethodShouldReturnSameAsGetManagerBeanName() {
        final AuthorizationRule rule = new AuthorizationRule();
        final String managerBeanName = "deprecatedManager";
        rule.setManagerBeanName(managerBeanName);

        @SuppressWarnings("deprecation") final String deprecatedResult = rule.managerBeanName();

        assertEquals(managerBeanName, deprecatedResult);
        assertEquals(rule.getManagerBeanName(), deprecatedResult);
    }

    @Test
    void rolesDeprecatedMethodShouldReturnSameAsGetRoles() {
        final AuthorizationRule rule = new AuthorizationRule();
        final List<String> roles = List.of("DEPRECATED_ROLE");
        rule.setRoles(roles);

        @SuppressWarnings("deprecation") final List<String> deprecatedResult = rule.roles();

        assertEquals(roles, deprecatedResult);
        assertEquals(rule.getRoles(), deprecatedResult);
    }

    @Test
    void shouldHandleAllHttpMethods() {
        final HttpMethod[] methods = {
                HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,
                HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.HEAD,
                HttpMethod.OPTIONS, HttpMethod.TRACE
        };

        for (final HttpMethod method : methods) {
            final AuthorizationRule rule = new AuthorizationRule();
            rule.setMethod(method);

            assertEquals(method, rule.getMethod());
            @SuppressWarnings("deprecation") final HttpMethod deprecatedMethod = rule.method();
            assertEquals(method, deprecatedMethod);
        }
    }

    @Test
    void shouldHandleSpecialCharactersInPath() {
        final String[] specialPaths = {
                "/api/path-with-dashes",
                "/api/path_with_underscores",
                "/api/path.with.dots",
                "/api/path/with/multiple/segments",
                "/api/path/{id}",
                "/api/path/**",
                "/api/path?query=param"
        };

        for (final String path : specialPaths) {
            final AuthorizationRule rule = new AuthorizationRule();
            rule.setPath(path);

            assertEquals(path, rule.getPath());
            @SuppressWarnings("deprecation") final String deprecatedPath = rule.path();
            assertEquals(path, deprecatedPath);
        }
    }

    @Test
    void shouldHandleSpecialCharactersInManagerBeanName() {
        final String[] specialNames = {
                "manager-with-dashes",
                "manager_with_underscores",
                "manager.with.dots",
                "ManagerWithCamelCase",
                "managerWithMixedCase123"
        };

        for (final String name : specialNames) {
            final AuthorizationRule rule = new AuthorizationRule();
            rule.setManagerBeanName(name);

            assertEquals(name, rule.getManagerBeanName());
            @SuppressWarnings("deprecation") final String deprecatedName = rule.managerBeanName();
            assertEquals(name, deprecatedName);
        }
    }

    @Test
    void shouldHandleSpecialCharactersInRoles() {
        final List<String> specialRoles = Arrays.asList(
                "ROLE_WITH_UNDERSCORES",
                "ROLE-WITH-DASHES",
                "ROLE.WITH.DOTS",
                "RoleWithCamelCase",
                "role_with_mixed_CASE_123"
        );

        final AuthorizationRule rule = new AuthorizationRule();
        rule.setRoles(specialRoles);

        assertEquals(specialRoles, rule.getRoles());
        assertEquals(5, rule.getRoles().size());
        for (final String role : specialRoles) {
            assertTrue(rule.getRoles().contains(role));
        }

        @SuppressWarnings("deprecation") final List<String> deprecatedRoles = rule.roles();
        assertEquals(specialRoles, deprecatedRoles);
    }

    @Test
    void shouldCreateComplexRule() {
        final HttpMethod method = HttpMethod.POST;
        final String path = "/api/admin/users/{id}/permissions";
        final String managerBeanName = "adminUserPermissionManager";
        final List<String> roles = Arrays.asList("SUPER_ADMIN", "USER_MANAGER", "PERMISSION_EDITOR");

        final AuthorizationRule rule = new AuthorizationRule(method, path, managerBeanName, roles);

        assertEquals(method, rule.getMethod());
        assertEquals(path, rule.getPath());
        assertEquals(managerBeanName, rule.getManagerBeanName());
        assertEquals(roles, rule.getRoles());
        assertEquals(3, rule.getRoles().size());

        // Verify all deprecated methods return the same values
        @SuppressWarnings("deprecation") final HttpMethod deprecatedMethod = rule.method();
        @SuppressWarnings("deprecation") final String deprecatedPath = rule.path();
        @SuppressWarnings("deprecation") final String deprecatedManagerBeanName = rule.managerBeanName();
        @SuppressWarnings("deprecation") final List<String> deprecatedRoles = rule.roles();

        assertEquals(method, deprecatedMethod);
        assertEquals(path, deprecatedPath);
        assertEquals(managerBeanName, deprecatedManagerBeanName);
        assertEquals(roles, deprecatedRoles);
    }

    @Test
    void shouldSupportBeanPropertyPattern() {
        // Test that the class can be used for Spring Boot configuration binding
        final AuthorizationRule rule = new AuthorizationRule();

        // Simulate property binding
        rule.setMethod(HttpMethod.GET);
        rule.setPath("/api/config/test");
        rule.setManagerBeanName("configTestManager");
        rule.setRoles(Arrays.asList("CONFIG_ADMIN", "CONFIG_USER"));

        // Verify all properties are accessible
        assertNotNull(rule.getMethod());
        assertNotNull(rule.getPath());
        assertNotNull(rule.getManagerBeanName());
        assertNotNull(rule.getRoles());
        assertFalse(rule.getRoles().isEmpty());
    }
}