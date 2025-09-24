package co.com.pragma.model.constants;

import java.util.List;

/**
 * Constantes para los estados de las solicitudes de crédito.
 * Define los estados válidos y sus transiciones según las reglas de negocio.
 */
public enum ApplicationStatusConstants {
    ;

    /**
     * ID del estado pendiente de revisión (corresponde al ID en la base de datos).
     */
    public static final Integer PENDING_STATUS_ID = 1;

    /**
     * ID del estado aprobado (corresponde al ID en la base de datos).
     */
    public static final Integer APPROVED_STATUS_ID = 2;

    /**
     * ID del estado rechazado (corresponde al ID en la base de datos).
     */
    public static final Integer REJECTED_STATUS_ID = 3;

    /**
     * ID del estado cancelado (corresponde al ID en la base de datos).
     */
    public static final Integer CANCELLED_STATUS_ID = 4;

    /**
     * ID del estado de revisión manual (corresponde al ID en la base de datos).
     */
    public static final Integer MANUAL_REVIEW_STATUS_ID = 5;

    /**
     * Nombre técnico del estado pendiente.
     */
    public static final String PENDING_STATUS_NAME = "PENDIENTE";

    /**
     * Nombre técnico del estado aprobado.
     */
    public static final String APPROVED_STATUS_NAME = "APROBADO";

    /**
     * Nombre técnico del estado rechazado.
     */
    public static final String REJECTED_STATUS_NAME = "RECHAZADO";

    /**
     * Nombre técnico del estado cancelado.
     */
    public static final String CANCELLED_STATUS_NAME = "CANCELADO";

    /**
     * Nombre técnico del estado de revisión manual.
     */
    public static final String MANUAL_REVIEW_STATUS_NAME = "REVISION_MANUAL";

    /**
     * Descripción amigable para el estado pendiente de revisión.
     */
    public static final String PENDING_STATUS_DESCRIPTION = "Pendiente de revisión";

    /**
     * Descripción amigable para el estado aprobado.
     */
    public static final String APPROVED_STATUS_DESCRIPTION = "Aprobada";

    /**
     * Descripción amigable para el estado rechazado.
     */
    public static final String REJECTED_STATUS_DESCRIPTION = "Rechazada";

    /**
     * Descripción amigable para el estado cancelado.
     */
    public static final String CANCELLED_STATUS_DESCRIPTION = "Cancelada";

    /**
     * Descripción amigable para el estado de revisión manual.
     */
    public static final String MANUAL_REVIEW_STATUS_DESCRIPTION = "Revisión manual";

    /**
     * Estados terminales que no permiten más cambios en la solicitud.
     * Una vez que la solicitud alcanza uno de estos estados, no puede ser modificada.
     */
    public static final List<Integer> TERMINAL_STATUS_IDS = List.of(
            ApplicationStatusConstants.APPROVED_STATUS_ID,
            ApplicationStatusConstants.REJECTED_STATUS_ID,
            ApplicationStatusConstants.CANCELLED_STATUS_ID
    );

    /**
     * Estados que requieren revisión por parte del sistema o un asesor.
     * Incluye estados pendientes y que requieren intervención manual.
     */
    public static final List<Integer> PENDING_REVIEW_STATUS_IDS = List.of(
            ApplicationStatusConstants.PENDING_STATUS_ID,
            ApplicationStatusConstants.REJECTED_STATUS_ID,
            ApplicationStatusConstants.MANUAL_REVIEW_STATUS_ID
    );

    /**
     * Estados que pueden ser revisados por un asesor.
     * Define qué solicitudes aparecen en la bandeja de trabajo del asesor.
     */
    public static final List<String> ADVISOR_REVIEWABLE_STATUSES = List.of(
            ApplicationStatusConstants.PENDING_STATUS_DESCRIPTION,
            ApplicationStatusConstants.REJECTED_STATUS_DESCRIPTION,
            ApplicationStatusConstants.MANUAL_REVIEW_STATUS_DESCRIPTION
    );

}