package co.com.pragma.security.api.config;

import co.com.pragma.model.log.gateways.LoggerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AuthorizationRuleTypeResolverTest {

    @Mock
    private LoggerPort logger;

    private AuthorizationRuleTypeResolver resolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.resolver = new AuthorizationRuleTypeResolver(this.logger);
    }

    @Test
    void shouldBeAnnotatedWithComponent() {
        assertTrue(AuthorizationRuleTypeResolver.class.isAnnotationPresent(Component.class));
    }

    @Test
    void shouldHaveCorrectConstructor() {
        assertDoesNotThrow(() -> new AuthorizationRuleTypeResolver(this.logger));
    }

    @Test
    void shouldReturnManagerWhenManagerBeanNameIsPresent() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/test");
        rule.setManagerBeanName("testManager");

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.MANAGER, result);
        verify(this.logger, atLeastOnce()).debug(anyString(), anyString(), any(), anyString());
        verify(this.logger).debug("Rule for path {} is MANAGER.", "/api/test");
    }

    @Test
    void shouldReturnManagerWhenManagerBeanNameIsPresentEvenWithRoles() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/admin");
        rule.setManagerBeanName("adminManager");
        rule.setRoles(Arrays.asList("ADMIN", "USER"));

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.MANAGER, result);
        verify(this.logger).debug("Rule for path {} is MANAGER.", "/api/admin");
    }

    @Test
    void shouldReturnRoleWhenRolesArePresentAndNoManager() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/users");
        rule.setRoles(Arrays.asList("USER", "ADMIN"));

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.ROLE, result);
        verify(this.logger).debug("Rule for path {} is ROLE.", "/api/users");
    }

    @Test
    void shouldReturnRoleWhenSingleRoleIsPresent() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/single-role");
        rule.setRoles(Collections.singletonList("ADMIN"));

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.ROLE, result);
        verify(this.logger).debug("Rule for path {} is ROLE.", "/api/single-role");
    }

    @Test
    void shouldReturnDenyAllWhenNoManagerAndNoRoles() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/public");

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/public");
    }

    @Test
    void shouldReturnDenyAllWhenManagerBeanNameIsEmpty() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/empty-manager");
        rule.setManagerBeanName("");

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/empty-manager");
    }

    @Test
    void shouldReturnDenyAllWhenManagerBeanNameIsNull() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/null-manager");
        rule.setManagerBeanName(null);

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/null-manager");
    }

    @Test
    void shouldReturnDenyAllWhenRolesAreEmpty() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/empty-roles");
        rule.setRoles(new ArrayList<>());

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/empty-roles");
    }

    @Test
    void shouldReturnDenyAllWhenRolesAreNull() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/null-roles");
        rule.setRoles(null);

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/null-roles");
    }

    @Test
    void shouldReturnDenyAllWhenBothManagerAndRolesAreEmpty() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/both-empty");
        rule.setManagerBeanName("");
        rule.setRoles(new ArrayList<>());

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/both-empty");
    }

    @Test
    void shouldReturnDenyAllWhenBothManagerAndRolesAreNull() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/both-null");
        rule.setManagerBeanName(null);
        rule.setRoles(null);

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.DENY_ALL, result);
        verify(this.logger).debug("Rule for path {} is DENY_ALL (fallback).", "/api/both-null");
    }

    @Test
    void shouldLogInitialDebugMessage() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/debug-test");
        rule.setManagerBeanName("debugManager");

        // Act
        this.resolver.determineRuleType(rule);

        // Assert
        verify(this.logger).debug("Determining rule type for path: {} method: {} manager: {}",
                "/api/debug-test", null, "debugManager");
    }

    @Test
    void shouldLogManagerRuleDebugMessage() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/manager-debug");
        rule.setManagerBeanName("managerBean");

        // Act
        this.resolver.determineRuleType(rule);

        // Assert
        verify(this.logger).debug("isManagerRule for path {}: {}", "/api/manager-debug", true);
    }

    @Test
    void shouldLogRoleRuleDebugMessage() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/role-debug");
        rule.setRoles(List.of("ROLE1"));

        // Act
        this.resolver.determineRuleType(rule);

        // Assert
        verify(this.logger).debug("isManagerRule for path {}: {}", "/api/role-debug", false);
        verify(this.logger).debug("isManagerRule for path {}: {}", "/api/role-debug", true);
    }

    @Test
    void shouldHandleRuleWithNullPath() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath(null);
        rule.setManagerBeanName("testManager");

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.MANAGER, result);
        verify(this.logger).debug("Determining rule type for path: {} method: {} manager: {}",
                null, null, "testManager");
    }

    @Test
    void shouldPrioritizeManagerOverRoles() {
        // Test that MANAGER takes priority over ROLE when both are present
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/priority-test");
        rule.setManagerBeanName("priorityManager");
        rule.setRoles(Arrays.asList("ADMIN", "USER", "GUEST"));

        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        assertEquals(AuthorizationRuleType.MANAGER, result);
        verify(this.logger).debug("Rule for path {} is MANAGER.", "/api/priority-test");
        // Should not reach ROLE evaluation due to manager priority
        verify(this.logger, never()).debug("Rule for path {} is ROLE.", "/api/priority-test");
    }

    @Test
    void shouldHandleWhitespaceOnlyManagerBeanName() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/whitespace-manager");
        rule.setManagerBeanName("   ");

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        // Should return MANAGER because the code only checks null and empty, not whitespace
        assertEquals(AuthorizationRuleType.MANAGER, result);
        verify(this.logger).debug("Rule for path {} is MANAGER.", "/api/whitespace-manager");
    }

    @Test
    void shouldHandleMultipleRoles() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/multiple-roles");
        final List<String> multipleRoles = Arrays.asList("ADMIN", "USER", "MODERATOR", "GUEST", "VIEWER");
        rule.setRoles(multipleRoles);

        // Act
        final AuthorizationRuleType result = this.resolver.determineRuleType(rule);

        // Assert
        assertEquals(AuthorizationRuleType.ROLE, result);
        verify(this.logger).debug("Rule for path {} is ROLE.", "/api/multiple-roles");
    }

    @Test
    void shouldCallLoggerForEachConditionCheck() {
        // Arrange
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath("/api/logging-test");
        rule.setRoles(List.of("TEST_ROLE"));

        // Act
        this.resolver.determineRuleType(rule);

        // Assert - Verify all expected logging calls
        verify(this.logger).debug("Determining rule type for path: {} method: {} manager: {}",
                "/api/logging-test", null, null);
        verify(this.logger).debug("isManagerRule for path {}: {}", "/api/logging-test", false);
        verify(this.logger).debug("isManagerRule for path {}: {}", "/api/logging-test", true);
        verify(this.logger).debug("Rule for path {} is ROLE.", "/api/logging-test");
    }

    @Test
    void shouldHandleComplexScenarios() {
        // Test various complex combinations
        final AuthorizationRule[] rules = {
                this.createRule("/api/scenario1", "manager1", null),
                this.createRule("/api/scenario2", null, List.of("ROLE1")),
                this.createRule("/api/scenario3", "", Arrays.asList("ROLE2", "ROLE3")),
                this.createRule("/api/scenario4", "manager2", List.of("ROLE4")),
                this.createRule("/api/scenario5", null, new ArrayList<>()),
                this.createRule("/api/scenario6", "", new ArrayList<>())
        };

        final AuthorizationRuleType[] expectedResults = {
                AuthorizationRuleType.MANAGER,  // has manager
                AuthorizationRuleType.ROLE,     // has roles
                AuthorizationRuleType.ROLE,     // empty manager but has roles
                AuthorizationRuleType.MANAGER,  // has both, manager takes priority
                AuthorizationRuleType.DENY_ALL, // neither
                AuthorizationRuleType.DENY_ALL  // neither (empty is considered as no manager)
        };

        for (int i = 0; i < rules.length; i++) {
            final AuthorizationRuleType result = this.resolver.determineRuleType(rules[i]);
            assertEquals(expectedResults[i], result,
                    "Failed for scenario " + (i + 1) + " with path " + rules[i].getPath());
        }
    }

    private AuthorizationRule createRule(final String path, final String managerBeanName, final List<String> roles) {
        final AuthorizationRule rule = new AuthorizationRule();
        rule.setPath(path);
        rule.setManagerBeanName(managerBeanName);
        rule.setRoles(roles);
        return rule;
    }
}