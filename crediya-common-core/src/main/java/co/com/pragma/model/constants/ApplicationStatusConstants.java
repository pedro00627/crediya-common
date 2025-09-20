package co.com.pragma.model.constants;

import java.util.List;

/**
 * Constantes para los estados de las solicitudes de crédito.
 * Define los estados válidos y sus transiciones según las reglas de negocio.
 */
public final class ApplicationStatusConstants {

    // Status IDs (corresponden a los IDs en la base de datos)
    public static final Integer PENDING_STATUS_ID = 1;
    public static final Integer APPROVED_STATUS_ID = 2;
    public static final Integer REJECTED_STATUS_ID = 3;
    public static final Integer CANCELLED_STATUS_ID = 4;
    public static final Integer MANUAL_REVIEW_STATUS_ID = 5;

    // Status Names
    public static final String PENDING_STATUS_NAME = "PENDIENTE";
    public static final String APPROVED_STATUS_NAME = "APROBADO";
    public static final String REJECTED_STATUS_NAME = "RECHAZADO";
    public static final String CANCELLED_STATUS_NAME = "CANCELADO";
    public static final String MANUAL_REVIEW_STATUS_NAME = "REVISION_MANUAL";

    // Status Descriptions (business-friendly)
    public static final String PENDING_STATUS_DESCRIPTION = "Pendiente de revisión";
    public static final String APPROVED_STATUS_DESCRIPTION = "Aprobada";
    public static final String REJECTED_STATUS_DESCRIPTION = "Rechazada";
    public static final String CANCELLED_STATUS_DESCRIPTION = "Cancelada";
    public static final String MANUAL_REVIEW_STATUS_DESCRIPTION = "Revisión manual";

    // Status Lists for business logic
    public static final List<Integer> TERMINAL_STATUS_IDS = List.of(
            APPROVED_STATUS_ID,
            REJECTED_STATUS_ID,
            CANCELLED_STATUS_ID
    );

    public static final List<Integer> PENDING_REVIEW_STATUS_IDS = List.of(
            PENDING_STATUS_ID,
            REJECTED_STATUS_ID,
            MANUAL_REVIEW_STATUS_ID
    );

    public static final List<String> ADVISOR_REVIEWABLE_STATUSES = List.of(
            PENDING_STATUS_DESCRIPTION,
            REJECTED_STATUS_DESCRIPTION,
            MANUAL_REVIEW_STATUS_DESCRIPTION
    );

    private ApplicationStatusConstants() {
        // Utility class - prevent instantiation
    }
}