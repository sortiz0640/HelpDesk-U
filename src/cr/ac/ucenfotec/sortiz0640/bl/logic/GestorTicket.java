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

    public boolean agregar(String asunto, String descripcion, Usuario usuario, Departamento departamento) throws SQLException, ClassNotFoundException {
        Ticket nuevoTicket = new Ticket(asunto, descripcion, usuario, departamento);

        categorizador = new CategorizarTiquete();
        nuevoTicket.setTecnica(categorizador.obtenerCategoriaTecnica(descripcion));
        nuevoTicket.setEmocion(categorizador.obtenerCategoriaEmocional(descripcion));

        return db.agregar(nuevoTicket);
    }

    public String eliminarPorId(String ticketId) throws SQLException {
        boolean resultado = db.eliminar(ticketId);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente";
        }

        return "[INFO] Ticket eliminado correctamente";
    }

    public String actualizarEstado(String ticketId, EstadoTicket nuevoEstado) throws SQLException {
        boolean resultado = db.actualizarEstado(ticketId, nuevoEstado);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente.";
        }

        return "[INFO] Ticket actualizado correctamente";
    }

    public ArrayList<Ticket> obtenerTickets() throws SQLException {
        return db.obtenerTodos();
    }


    public void eliminarPorCorreoDepartamento(String correoDepartamento) throws SQLException {
        db.eliminar(correoDepartamento);
    }

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

    public ArrayList<String> obtenerPalabrasDetonantes(String ticketId, TipoCategoria categoria) throws SQLException {
        return db.obtenerPalabrasDetonantes(ticketId, categoria);
    }
}