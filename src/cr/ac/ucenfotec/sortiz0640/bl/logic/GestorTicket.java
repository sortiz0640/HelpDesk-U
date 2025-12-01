package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.dl.DataTicket;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de tickets.
 * Maneja todas las operaciones relacionadas con tickets del sistema,
 * incluyendo creación, eliminación, actualización de estado, búsqueda y listado.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class GestorTicket {

    private DataTicket db;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     */

    public GestorTicket() {
        db = new DataTicket();
    }

    /**
     * Crea un nuevo ticket en el sistema.
     * El ticket se genera automáticamente con un ID único y estado NUEVO.
     *
     * @param asunto Asunto o título del ticket
     * @param descripcion Descripción detallada del problema o solicitud
     * @param usuario Usuario que crea el ticket
     * @param departamento Departamento al que se asigna el ticket
     * @return Mensaje indicando éxito con los datos del ticket o mensaje de error
     */

    public boolean agregar(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        Ticket nuevoTicket = new Ticket(asunto, descripcion, usuario, departamento);
        return db.agregar(nuevoTicket);
    }

    /**
     * Elimina un ticket del sistema por su ID.
     *
     * @param ticketId ID del ticket a eliminar
     * @return Mensaje indicando éxito o error en la operación
     */

    public String eliminarPorId(String ticketId) {
        boolean resultado = db.eliminarPorId(ticketId);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente";
        }

        return "[INFO] Ticket eliminado correctamente";
    }

    /**
     * Actualiza el estado de un ticket existente.
     *
     * @param ticketId ID del ticket a actualizar
     * @param nuevoEstado Nuevo estado a asignar (NUEVO, EN_PROGRESO, RESUELTO)
     * @return Mensaje indicando éxito o error en la operación
     */

    public String actualizarEstado(String ticketId, EstadoTicket nuevoEstado) {
        boolean resultado = db.actualizarEstado(ticketId, nuevoEstado);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente.";
        }

        return "[INFO] Ticket actualizado correctamente";
    }


    /**
     * Obtiene una lista con todos los tickets del sistema.
     *
     * @return ArrayList con la representación en texto de todos los tickets
     */

    public ArrayList<String> listarTodos() {
        return db.listarTodos();
    }

    /**
     * Obtiene una lista tipo Ticket con todos los tickets del sistema.
     *
     * @return ArrayList de objetos tipo Ticket
     */

    public ArrayList<Ticket> obtenerTickets() {
        return db.obtenerTickets();
    }


    /**
     * Obtiene una lista de tickets creados por un usuario específico.
     *
     * @param correoUsuario Correo del usuario
     * @return ArrayList con los tickets del usuario especificado
     */

    public ArrayList<String> listarPorUsuario(String correoUsuario) {
        return db.listarPorUsuario(correoUsuario);
    }

    /**
     * Busca y retorna un objeto Ticket por su ID.
     *
     * @param ticketId ID del ticket a buscar
     * @return Objeto Ticket si existe, null si no se encuentra
     */

    public Ticket buscarPorId(String ticketId) {
        return db.buscarPorId(ticketId);
    }

    /**
     * Verifica si existe un ticket con el ID especificado.
     *
     * @param ticketId ID del ticket a verificar
     * @return true si el ticket existe, false en caso contrario
     */

    public boolean existePorId(String ticketId) {
        return db.existePorId(ticketId);
    }

    /**
     * Verifica si existen tickets registrados en el sistema.
     *
     * @return true si hay al menos un ticket, false si está vacío
     */

    public boolean existenTickets() {
        return db.existenTickets();
    }

    /**
     * Elimina todos los tickets asociados a un departamento específico.
     * Utilizado cuando se elimina un departamento para mantener integridad referencial.
     *
     * @param correoDepartamento Correo del departamento cuyos tickets serán eliminados
     */

    public void eliminarPorCorreoDepartamento(String correoDepartamento) {
        db.eliminarPorCorreoDepartamento(correoDepartamento);
    }

    public ArrayList<Ticket> obtenerTiquetesPorUsuario(String correoUsuario) {
        ArrayList<Ticket> tiquetes = obtenerTickets();
        ArrayList<Ticket> filtrados = new ArrayList<>();

        for (Ticket ticket : tiquetes) {
            if (ticket.getUsuario().getCorreo().equals(correoUsuario)) {
                filtrados.add(ticket);
            }
        }

        return filtrados;
    }
}