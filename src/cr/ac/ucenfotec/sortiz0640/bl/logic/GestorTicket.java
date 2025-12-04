package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.analytics.CategorizarTiquete;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.dl.DataTicket;

import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de tickets.
 * Trabaja exclusivamente con la entidad Ticket.
 * No tiene dependencias de otros gestores.
 * Recibe Usuario y Departamento como parámetros cuando los necesita.
 *
 * @author Sebastian Ortiz
 * @version 2.0
 * @since 2025
 */

public class GestorTicket {

    private DataTicket db;
    private CategorizarTiquete categorizador;

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
     * @param usuario Usuario que crea el ticket (pasado como parámetro)
     * @param departamento Departamento al que se asigna el ticket (pasado como parámetro)
     * @return true si el ticket se agregó correctamente, false en caso contrario
     */

    public boolean agregar(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        Ticket nuevoTicket = new Ticket(asunto, descripcion, usuario, departamento);

        categorizador = new CategorizarTiquete();
        nuevoTicket.setCategorias(categorizador.obtenerCategoriaTecnica(descripcion), categorizador.obtenerCategoriaEmocional(descripcion));

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

    /**
     * Obtiene los tickets de un usuario específico como lista de objetos Ticket.
     *
     * @param correoUsuario Correo del usuario
     * @return ArrayList con los tickets del usuario
     */

    public ArrayList<Ticket> obtenerTicketsPorUsuario(String correoUsuario) {
        ArrayList<Ticket> tickets = obtenerTickets();
        ArrayList<Ticket> filtrados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getUsuario().getCorreo().equals(correoUsuario)) {
                filtrados.add(ticket);
            }
        }

        return filtrados;
    }

    /**
     * Obtiene los detalles de un ticket específico en formato de array.
     *
     * @param ticketId ID del ticket
     * @return Array con los detalles del ticket o null si no existe
     */

    public String[] obtenerDetallesTicket(String ticketId) {
        Ticket t = buscarPorId(ticketId);

        if (t == null) {
            return null;
        }

        return new String[]{
                t.getId(),
                t.getAsunto(),
                t.getDescripcion(),
                t.getDepartamento().getCorreo(),
                t.getUsuario().getCorreo(),
                t.getCategoriaTecnica(),
                t.getCategoriaEmocional(),
                t.getEstado().toString(),
                t.getEstado().toString()
        };
    }

    /**
     * Convierte la lista de tickets a formato de array para tablas.
     *
     * @return ArrayList de arrays con los datos de cada ticket
     */

    public ArrayList<String[]> obtenerTodosTicketsFormato() {
        ArrayList<Ticket> tickets = obtenerTickets();
        ArrayList<String[]> resultado = new ArrayList<>();

        if (tickets == null || tickets.isEmpty()) {
            return resultado;
        }

        for (Ticket ticket : tickets) {
            String[] datos = {
                    ticket.getId(),
                    ticket.getAsunto(),
                    ticket.getDepartamento().getCorreo(),
                    ticket.getUsuario().getCorreo(),
                    ticket.getCategoriaTecnica(),
                    ticket.getCategoriaEmocional(),
                    ticket.getEstado().toString(),
                    ticket.getDescripcion()
            };
            resultado.add(datos);
        }

        return resultado;
    }

    /**
     * Obtiene los tickets de un usuario en formato de array para tablas.
     *
     * @param correoUsuario Correo del usuario
     * @return ArrayList de arrays con los datos de los tickets
     */

    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) {
        ArrayList<Ticket> tickets = obtenerTicketsPorUsuario(correoUsuario);
        ArrayList<String[]> resultado = new ArrayList<>();

        if (tickets == null || tickets.isEmpty()) {
            return resultado;
        }

        for (Ticket ticket : tickets) {
            String[] datos = {
                    ticket.getId(),
                    ticket.getAsunto(),
                    ticket.getDepartamento().getCorreo(),
                    ticket.getUsuario().getCorreo(),
                    ticket.getCategoriaTecnica(),
                    ticket.getCategoriaEmocional(),
                    ticket.getEstado().toString(),
                    ticket.getDescripcion()
            };
            resultado.add(datos);
        }

        return resultado;
    }
}