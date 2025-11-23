package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la gestión de tickets.
 * Administra el almacenamiento en memoria de los tickets del sistema mediante ArrayList.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para tickets,
 * incluyendo filtrado por departamento y usuario.
 *
 * @author Sebastain Ortiz
 * @version 1.0
 * @since 2025
 */

public class DataTicket {

    private ArrayList<Ticket> listaTickets;

    /**
     * Constructor que inicializa la lista de tickets vacía.
     */

    public DataTicket() {
        this.listaTickets = new ArrayList<>();
    }

    /**
     * Agrega un nuevo ticket al almacenamiento.
     * Valida que el ticket no sea nulo antes de agregarlo.
     *
     * @param ticket Objeto Ticket a agregar
     * @return true si el ticket fue agregado exitosamente,
     *         false si el ticket es nulo
     */

    public boolean agregar(Ticket ticket) {
        if (ticket == null) {
            return false;
        }

        listaTickets.add(ticket);
        return true;
    }

    /**
     * Elimina un ticket del almacenamiento por su ID.
     *
     * @param ticketId ID del ticket a eliminar
     * @return true si el ticket fue eliminado exitosamente,
     *         false si no se encontró un ticket con ese ID
     */

    public boolean eliminarPorId(String ticketId) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                listaTickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza el estado de un ticket existente.
     *
     * @param ticketId ID del ticket a actualizar
     * @param nuevoEstado Nuevo estado a asignar al ticket
     * @return true si el estado fue actualizado exitosamente,
     *         false si no se encontró el ticket
     */

    public boolean actualizarEstado(String ticketId, EstadoTicket nuevoEstado) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                ticket.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }

    /**
     * Busca y retorna un objeto Ticket por su ID.
     *
     * @param ticketId ID del ticket a buscar
     * @return Objeto Ticket si se encuentra, null en caso contrario
     */

    public Ticket buscarPorId(String ticketId) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Verifica si existe un ticket con el ID especificado.
     *
     * @param ticketId ID del ticket a verificar
     * @return true si existe un ticket con ese ID, false en caso contrario
     */

    public boolean existePorId(String ticketId) {
        return buscarPorId(ticketId) != null;
    }

    /**
     * Obtiene una lista con la representación en texto de todos los tickets.
     *
     * @return ArrayList con el toString() de cada ticket almacenado
     */

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            lista.add(ticket.toString());
        }

        return lista;
    }

    /**
     * Obtiene una lista de tickets asignados a un departamento específico.
     * Filtra los tickets por el correo del departamento.
     *
     * @param correoDepartamento Correo del departamento a filtrar
     * @return ArrayList con la representación en texto de los tickets del departamento
     */

    public ArrayList<String> listarPorDepartamento(String correoDepartamento) {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            if (ticket.getDepartamento() != null &&
                    ticket.getDepartamento().getCorreo().equals(correoDepartamento)) {
                lista.add(ticket.toString());
            }
        }

        return lista;
    }

    /**
     * Obtiene una lista de tickets creados por un usuario específico.
     * Filtra los tickets por el correo del usuario creador.
     *
     * @param correoUsuario Correo del usuario a filtrar
     * @return ArrayList con la representación en texto de los tickets del usuario
     */

    public ArrayList<String> listarPorUsuario(String correoUsuario) {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            if (ticket.getUsuario() != null &&
                    ticket.getUsuario().getCorreo().equals(correoUsuario)) {
                lista.add(ticket.toString());
            }
        }

        return lista;
    }

    /**
     * Elimina todos los tickets asociados a un departamento específico.
     * Utilizado para mantener integridad referencial cuando se elimina un departamento.
     *
     * @param correoDepartamento Correo del departamento cuyos tickets serán eliminados
     */

    public void eliminarPorCorreoDepartamento(String correoDepartamento) {
        listaTickets.removeIf(t -> t.getDepartamento().getCorreo().equals(correoDepartamento));
    }

    /**
     * Obtiene la lista completa de objetos Ticket almacenados.
     *
     * @return ArrayList con todos los tickets del sistema
     */

    public ArrayList<Ticket> obtenerTickets() {
        return listaTickets;
    }

    /**
     * Verifica si existen tickets registrados en el almacenamiento.
     *
     * @return true si hay al menos un ticket, false si la lista está vacía
     */

    public boolean existenTickets() {
        return !listaTickets.isEmpty();
    }
}