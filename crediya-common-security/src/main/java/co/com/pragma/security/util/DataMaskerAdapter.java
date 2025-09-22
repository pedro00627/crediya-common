package co.com.pragma.security.util;

import co.com.pragma.commons.logging.LogHelper;
import co.com.pragma.security.model.gateways.DataMaskerPort;
import org.springframework.stereotype.Component;

/**
 * Adaptador para el enmascaramiento de datos sensibles, implementando {@link DataMaskerPort}.
 * Utiliza {@link LogHelper} para realizar las operaciones de enmascaramiento.
 */
@Component
public class DataMaskerAdapter implements DataMaskerPort {

    /**
     * Constructor por defecto para DataMaskerAdapter.
     */
    public DataMaskerAdapter() {
        // Constructor por defecto
    }

    /**
     * {@inheritDoc}
     * <p>
     * Esta implementación delega la operación de enmascaramiento a {@link LogHelper}.
     */
    @Override
    public String maskEmail(final String email) {
        return LogHelper.maskEmail(email);
    }
}