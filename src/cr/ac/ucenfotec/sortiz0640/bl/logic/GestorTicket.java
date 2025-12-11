package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.analytics.CategorizarTiquete;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;
import cr.ac.ucenfotec.sortiz0640.dl.TicketDAO;

import java.sql.SQLException;
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

    private TicketDAO db;
    private CategorizarTiquete categorizador;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     *
     * @throws SQLException Si ocurre un error de conexión.
     * @throws ClassNotFoundException Si no se encuentra el driver.
     */
    public GestorTicket() throws SQLException, ClassNotFoundException {
        ConfigPropertiesReader config = new ConfigPropertiesReader();
        db = new TicketDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    /**
     * Crea y guarda un nuevo ticket, aplicando categorización automática.
     *
     * @param asunto Asunto del ticket.
     * @param descripcion Descripción del problema.
     * @param usuario Usuario creador del ticket.
     * @param departamento Departamento asignado.
     * @return true si se guardó correctamente.
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws ClassNotFoundException Si ocurre un error en la categorización.
     */
    public boolean agregar(String asunto, String descripcion, Usuario usuario, Departamento departamento) throws SQLException, ClassNotFoundException {
        Ticket nuevoTicket = new Ticket(asunto, descripcion, usuario, departamento);

        categorizador = new CategorizarTiquete();
        nuevoTicket.setTecnica(categorizador.obtenerCategoriaTecnica(descripcion));
        nuevoTicket.setEmocion(categorizador.obtenerCategoriaEmocional(descripcion));

        return db.agregar(nuevoTicket);
    }

    /**
     * Elimina un ticket por su identificador único.
     *
     * @param ticketId ID del ticket a eliminar.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String eliminarPorId(String ticketId) throws SQLException {
        boolean resultado = db.eliminar(ticketId);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente";
        }

        return "[INFO] Ticket eliminado correctamente";
    }

    /**
     * Actualiza el estado de un ticket.
     *
     * @param ticketId ID del ticket.
     * @param nuevoEstado Nuevo estado a asignar.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String actualizarEstado(String ticketId, EstadoTicket nuevoEstado) throws SQLException {
        boolean resultado = db.actualizarEstado(ticketId, nuevoEstado);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente.";
        }

        return "[INFO] Ticket actualizado correctamente";
    }

    /**
     * Obtiene la lista completa de todos los tickets.
     *
     * @return ArrayList de tickets.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<Ticket> obtenerTickets() throws SQLException {
        return db.obtenerTodos();
    }

    /**
     * Obtiene los tickets filtrados por el correo del usuario creador.
     *
     * @param correoUsuario Correo del usuario.
     * @return Lista de tickets pertenecientes al usuario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<Ticket> obtenerTicketsPorUsuario(String correoUsuario) throws SQLException {
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
     * Obtiene todos los tickets en formato de arreglo de Strings para visualización.
     *
     * @return Lista de arreglos con datos de los tickets.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTodosTicketsFormato() throws SQLException {
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
                    ticket.getTecnica().getNombre(),
                    ticket.getEmocion().getNombre(),
                    ticket.getEstado().toString(),
                    ticket.getDescripcion()
            };
            resultado.add(datos);
        }

        return resultado;
    }

    /**
     * Obtiene los tickets de un usuario específico en formato de arreglo de Strings.
     *
     * @param correoUsuario Correo del usuario.
     * @return Lista de arreglos con datos de los tickets del usuario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) throws SQLException {
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
                    ticket.getTecnica().getNombre(),
                    ticket.getEmocion().getNombre(),
                    ticket.getEstado().toString(),
                    ticket.getDescripcion()
            };
            resultado.add(datos);
        }

        return resultado;
    }

    /**
     * Obtiene las palabras detonantes (técnicas o emocionales) de un ticket específico.
     *
     * @param ticketId ID del ticket.
     * @param categoria Tipo de categoría (TECNICA o EMOCIONAL).
     * @return Lista de palabras identificadas.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String> obtenerPalabrasDetonantes(String ticketId, TipoCategoria categoria) throws SQLException {
        return db.obtenerPalabrasDetonantes(ticketId, categoria);
    }
}