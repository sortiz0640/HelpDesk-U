package cr.ac.ucenfotec.sortiz0640.bl.util;

/**
 * Enumeración que representa los posibles estados de un ticket en el sistema HelpDesk.
 * Un ticket puede transitar entre estos estados durante su ciclo de vida.
 *
 * @author Santiago Ortiz
 * @version 1.0
 * @since 2025
 */

public enum EstadoTicket {
    NUEVO,
    EN_PROGRESO,
    RESUELTO
}