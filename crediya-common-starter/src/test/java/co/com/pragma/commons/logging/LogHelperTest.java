package co.com.pragma.commons.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogHelperTest {

    @Test
    void testMaskEmail_validEmail() {
        assertEquals("t***r@pragma.com.co", LogHelper.maskEmail("test.user@pragma.com.co"));
        assertEquals("j***e@example.com", LogHelper.maskEmail("john.doe@example.com"));
        assertEquals("a***z@domain.net", LogHelper.maskEmail("a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z@domain.net"));
    }

    @Test
    void testMaskEmail_shortLocalPart() {
        assertEquals("***@example.com", LogHelper.maskEmail("a@example.com"));
        assertEquals("***@example.com", LogHelper.maskEmail("ab@example.com"));
        assertEquals("a***c@example.com", LogHelper.maskEmail("abc@example.com"));
    }

    @Test
    void testMaskEmail_nullInput() {
        assertEquals("***", LogHelper.maskEmail(null));
    }

    @Test
    void testMaskEmail_invalidFormat() {
        assertEquals("***", LogHelper.maskEmail("invalid-email"));
        assertEquals("***", LogHelper.maskEmail("test@.com"));
        assertEquals("***", LogHelper.maskEmail("test@com"));
        assertEquals("***", LogHelper.maskEmail("test@example"));
        assertEquals("***", LogHelper.maskEmail("test@example.c")); // TLD too short
    }

    @Test
    void testMaskDocument_validDocumentId() {
        assertEquals("1****7890", LogHelper.maskDocument("1234567890"));
        assertEquals("9****1234", LogHelper.maskDocument("98765432101234"));
        assertEquals("1****6789", LogHelper.maskDocument("123456789")); // Exactly 9 chars
    }

    @Test
    void testMaskDocument_shortDocumentId() {
        assertEquals("***", LogHelper.maskDocument("12345")); // Less than 6 chars
        assertEquals("***", LogHelper.maskDocument("123"));
        assertEquals("***", LogHelper.maskDocument(""));
    }

    @Test
    void testMaskDocument_exactSixChars() {
        assertEquals("1****6", LogHelper.maskDocument("123456"));
    }

    @Test
    void testMaskDocument_nullInput() {
        assertEquals("***", LogHelper.maskDocument(null));
    }
}
