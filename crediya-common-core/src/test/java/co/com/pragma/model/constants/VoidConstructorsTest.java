package co.com.pragma.model.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;


class VoidConstructorsTest {
    // Proveer las clases a testear
    static Stream<Class<?>> clasesConstantsProvider() {
        return Stream.of(
                ApiConstants.class,
                ApplicationStatusConstants.class,
                BusinessConstants.class,
                DatabaseConstants.class,
                ErrorMessages.class,
                HttpConstants.class,
                QueryParameterConstants.class
        );
    }

    @ParameterizedTest
    @MethodSource("clasesConstantsProvider")
    void testVoidConstructor(final Class<?> clazz) throws Exception {
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final Object instance = constructor.newInstance();
        Assertions.assertNotNull(instance, "La instancia no debe ser null");
    }
}

