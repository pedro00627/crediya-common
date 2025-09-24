package co.com.pragma.starter.exception.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ErrorResponseWrapperTest {

    @Test
    void shouldBeARecord() {
        assertTrue(ErrorResponseWrapper.class.isRecord());
    }

    @Test
    void shouldCreateWrapperWithErrorBody() {
        final ErrorBody errorBody = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Validation failed");
        final ErrorResponseWrapper wrapper = new ErrorResponseWrapper(errorBody);

        assertEquals(errorBody, wrapper.error());
    }

    @Test
    void shouldWrapErrorBodyUsingStaticMethod() {
        final ErrorBody errorBody = new ErrorBody(404, "NOT_FOUND", "Resource not found", null);
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.wrap(errorBody);

        assertEquals(errorBody, wrapper.error());
        assertEquals(404, wrapper.error().statusCode());
        assertEquals("NOT_FOUND", wrapper.error().error());
        assertEquals("Resource not found", wrapper.error().message());
        assertNull(wrapper.error().details());
    }

    @Test
    void shouldCreateWrapperWithMinimalInformation() {
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.create(401, "UNAUTHORIZED", "Authentication required");

        assertNotNull(wrapper.error());
        assertEquals(401, wrapper.error().statusCode());
        assertEquals("UNAUTHORIZED", wrapper.error().error());
        assertEquals("Authentication required", wrapper.error().message());
        assertNull(wrapper.error().details());
    }

    @Test
    void shouldCreateWrapperWithCompleteInformation() {
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.create(
                500,
                "INTERNAL_SERVER_ERROR",
                "Server error",
                "Database connection failed"
        );

        assertNotNull(wrapper.error());
        assertEquals(500, wrapper.error().statusCode());
        assertEquals("INTERNAL_SERVER_ERROR", wrapper.error().error());
        assertEquals("Server error", wrapper.error().message());
        assertEquals("Database connection failed", wrapper.error().details());
    }

    @Test
    void shouldHandleNullErrorBody() {
        final ErrorResponseWrapper wrapper = new ErrorResponseWrapper(null);
        assertNull(wrapper.error());
    }

    @Test
    void shouldSupportEquality() {
        final ErrorBody errorBody1 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody2 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody3 = new ErrorBody(404, "NOT_FOUND", "Resource not found", null);

        final ErrorResponseWrapper wrapper1 = new ErrorResponseWrapper(errorBody1);
        final ErrorResponseWrapper wrapper2 = new ErrorResponseWrapper(errorBody2);
        final ErrorResponseWrapper wrapper3 = new ErrorResponseWrapper(errorBody3);

        assertEquals(wrapper1, wrapper2);
        assertNotEquals(wrapper1, wrapper3);
    }

    @Test
    void shouldSupportHashCode() {
        final ErrorBody errorBody1 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorBody errorBody2 = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");

        final ErrorResponseWrapper wrapper1 = new ErrorResponseWrapper(errorBody1);
        final ErrorResponseWrapper wrapper2 = new ErrorResponseWrapper(errorBody2);

        assertEquals(wrapper1.hashCode(), wrapper2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        final ErrorBody errorBody = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorResponseWrapper wrapper = new ErrorResponseWrapper(errorBody);
        final String toString = wrapper.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("ErrorResponseWrapper"));
    }

    @Test
    void staticCreateMethodsShouldProduceEquivalentResults() {
        // Create using ErrorBody directly
        final ErrorBody errorBody = ErrorBody.create(403, "FORBIDDEN", "Access denied");
        final ErrorResponseWrapper wrapper1 = ErrorResponseWrapper.wrap(errorBody);

        // Create using static method
        final ErrorResponseWrapper wrapper2 = ErrorResponseWrapper.create(403, "FORBIDDEN", "Access denied");

        assertEquals(wrapper1, wrapper2);
        assertEquals(wrapper1.error(), wrapper2.error());
    }

    @Test
    void shouldHandleVariousStatusCodes() {
        final ErrorResponseWrapper badRequest = ErrorResponseWrapper.create(400, "BAD_REQUEST", "Bad request");
        final ErrorResponseWrapper unauthorized = ErrorResponseWrapper.create(401, "UNAUTHORIZED", "Unauthorized");
        final ErrorResponseWrapper forbidden = ErrorResponseWrapper.create(403, "FORBIDDEN", "Forbidden");
        final ErrorResponseWrapper notFound = ErrorResponseWrapper.create(404, "NOT_FOUND", "Not found");
        final ErrorResponseWrapper serverError = ErrorResponseWrapper.create(500, "INTERNAL_SERVER_ERROR", "Server error");

        assertEquals(400, badRequest.error().statusCode());
        assertEquals(401, unauthorized.error().statusCode());
        assertEquals(403, forbidden.error().statusCode());
        assertEquals(404, notFound.error().statusCode());
        assertEquals(500, serverError.error().statusCode());
    }

    @Test
    void shouldHandleNullValues() {
        final ErrorResponseWrapper wrapper1 = ErrorResponseWrapper.create(400, null, null);
        final ErrorResponseWrapper wrapper2 = ErrorResponseWrapper.create(400, null, null, null);

        assertNull(wrapper1.error().error());
        assertNull(wrapper1.error().message());
        assertNull(wrapper1.error().details());

        assertNull(wrapper2.error().error());
        assertNull(wrapper2.error().message());
        assertNull(wrapper2.error().details());
    }

    @Test
    void shouldHandleEmptyStrings() {
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.create(400, "", "", "");

        assertEquals("", wrapper.error().error());
        assertEquals("", wrapper.error().message());
        assertEquals("", wrapper.error().details());
    }

    @Test
    void shouldBeImmutable() {
        final ErrorBody originalErrorBody = new ErrorBody(400, "BAD_REQUEST", "Invalid input", "Details");
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.wrap(originalErrorBody);

        // Verify the wrapper contains the expected error body
        assertEquals(originalErrorBody, wrapper.error());

        // Creating a new wrapper should not affect the original
        final ErrorResponseWrapper newWrapper = ErrorResponseWrapper.create(500, "SERVER_ERROR", "Server error");
        assertEquals(originalErrorBody, wrapper.error()); // Original should remain unchanged
    }

    @Test
    void shouldProvideConsistentStructure() {
        final ErrorResponseWrapper wrapper = ErrorResponseWrapper.create(
                422,
                "VALIDATION_ERROR",
                "Validation failed",
                "Email format is invalid"
        );

        // Verify structure consistency
        assertNotNull(wrapper.error());
        assertTrue(0 < wrapper.error().statusCode());
        assertNotNull(wrapper.error().error());
        assertNotNull(wrapper.error().message());
        assertNotNull(wrapper.error().details());
    }
}