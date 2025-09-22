package co.com.pragma.starter.exception.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorBodyTest {

    @Test
    void shouldBeARecord() {
        assertTrue(ErrorBody.class.isRecord());
    }

    @Test
    void shouldCreateErrorBodyWithAllParameters() {
        final int statusCode = 400;
        final String error = "BAD_REQUEST";
        final String message = "Invalid input";
        final String details = "Field 'email' is required";

        final ErrorBody errorBody = new ErrorBody(statusCode, error, message, details);

        assertEquals(statusCode, errorBody.statusCode());
        assertEquals(error, errorBody.error());
        assertEquals(message, errorBody.message());
        assertEquals(details, errorBody.details());
    }

    @Test
    void shouldCreateErrorBodyWithMinimalInformation() {
        final int statusCode = 404;
        final String error = "NOT_FOUND";
        final String message = "Resource not found";

        final ErrorBody errorBody = ErrorBody.create(statusCode, error, message);

        assertEquals(statusCode, errorBody.statusCode());
        assertEquals(error, errorBody.error());
        assertEquals(message, errorBody.message());
        assertNull(errorBody.details());
    }

    @Test
    void shouldCreateErrorBodyWithCompleteInformation() {
        final int statusCode = 500;
        final String error = "INTERNAL_SERVER_ERROR";
        final String message = "An unexpected error occurred";
        final String details = "Database connection failed";

        final ErrorBody errorBody = ErrorBody.create(statusCode, error, message, details);

        assertEquals(statusCode, errorBody.statusCode());
        assertEquals(error, errorBody.error());
        assertEquals(message, errorBody.message());
        assertEquals(details, errorBody.details());
    }

    @Test
    void shouldHandleNullValues() {
        final ErrorBody errorBody = new ErrorBody(400, null, null, null);

        assertEquals(400, errorBody.statusCode());
        assertNull(errorBody.error());
        assertNull(errorBody.message());
        assertNull(errorBody.details());
    }

    @Test
    void shouldHandleEmptyStrings() {
        final ErrorBody errorBody = new ErrorBody(400, "", "", "");

        assertEquals(400, errorBody.statusCode());
        assertEquals("", errorBody.error());
        assertEquals("", errorBody.message());
        assertEquals("", errorBody.details());
    }

    @Test
    void shouldSupportEquality() {
        final ErrorBody errorBody1 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody2 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody3 = new ErrorBody(404, "NOT_FOUND", "Resource not found", null);

        assertEquals(errorBody1, errorBody2);
        assertNotEquals(errorBody1, errorBody3);
    }

    @Test
    void shouldSupportHashCode() {
        final ErrorBody errorBody1 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody2 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");

        assertEquals(errorBody1.hashCode(), errorBody2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        final ErrorBody errorBody = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final String toString = errorBody.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("400"));
        assertTrue(toString.contains("BAD_REQUEST"));
        assertTrue(toString.contains("Invalid input"));
        assertTrue(toString.contains("Details"));
    }

    @Test
    void createMethodsShouldWorkWithTypicalHttpStatusCodes() {
        // 400 Bad Request
        final ErrorBody badRequest = ErrorBody.create(400, "BAD_REQUEST", "Invalid request");
        assertEquals(400, badRequest.statusCode());

        // 401 Unauthorized
        final ErrorBody unauthorized = ErrorBody.create(401, "UNAUTHORIZED", "Authentication required");
        assertEquals(401, unauthorized.statusCode());

        // 403 Forbidden
        final ErrorBody forbidden = ErrorBody.create(403, "FORBIDDEN", "Access denied");
        assertEquals(403, forbidden.statusCode());

        // 404 Not Found
        final ErrorBody notFound = ErrorBody.create(404, "NOT_FOUND", "Resource not found");
        assertEquals(404, notFound.statusCode());

        // 500 Internal Server Error
        final ErrorBody serverError = ErrorBody.create(500, "INTERNAL_SERVER_ERROR", "Server error", "Database error");
        assertEquals(500, serverError.statusCode());
    }

    @Test
    void shouldHandleNegativeStatusCodes() {
        final ErrorBody errorBody = ErrorBody.create(-1, "INVALID", "Negative status");
        assertEquals(-1, errorBody.statusCode());
    }

    @Test
    void shouldHandleLargeStatusCodes() {
        final ErrorBody errorBody = ErrorBody.create(999, "CUSTOM", "Custom status");
        assertEquals(999, errorBody.statusCode());
    }

    @Test
    void createWithDetailsMethodShouldOverrideNullDetails() {
        final ErrorBody errorBody = ErrorBody.create(400, "BAD_REQUEST", "Invalid input", "Validation failed");
        assertNotNull(errorBody.details());
        assertEquals("Validation failed", errorBody.details());
    }

    @Test
    void createWithoutDetailsMethodShouldHaveNullDetails() {
        final ErrorBody errorBody = ErrorBody.create(400, "BAD_REQUEST", "Invalid input");
        assertNull(errorBody.details());
    }

    @Test
    void shouldBeImmutable() {
        final ErrorBody errorBody = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");

        // Records are immutable by default, but let's verify the fields are final
        assertEquals(400, errorBody.statusCode());
        assertEquals("BAD_REQUEST", errorBody.error());
        assertEquals("Invalid input", errorBody.message());
        assertEquals("Details", errorBody.details());

        // Creating a new instance should not affect the original
        final ErrorBody newErrorBody = ErrorBody.create(500, "SERVER_ERROR", "Server error");
        assertEquals(400, errorBody.statusCode()); // Original should remain unchanged
    }
}