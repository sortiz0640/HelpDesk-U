package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.dl.template.IDao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Ticket.
 * Gestiona todas las operaciones CRUD de tickets en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class TicketDAO implements IDao<Ticket> {

    private UsuarioDAO usuarioDao;
    private DepartamentoDAO departamentoDao;

    public TicketDAO() {
        this.usuarioDao = new UsuarioDAO();
        this.departamentoDao = new DepartamentoDAO();
    }

    @Override
    public boolean insertar(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (id, titulo, descripcion, estado, correo_usuario, correo_departamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getId());
            stmt.setString(2, ticket.getAsunto());
            stmt.setString(3, ticket.getDescripcion());
            stmt.setString(4, ticket.getEstado().name());
            stmt.setString(5, ticket.getUsuario().getCorreo());
            stmt.setString(6, ticket.getDepartamento() != null ? ticket.getDepartamento().getCorreo() : null);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al insertar ticket: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizar(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET titulo = ?, descripcion = ?, estado = ?, " +
                "correo_usuario = ?, correo_departamento = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getAsunto());
            stmt.setString(2, ticket.getDescripcion());
            stmt.setString(3, ticket.getEstado().name());
            stmt.setString(4, ticket.getUsuario().getCorreo());
            stmt.setString(5, ticket.getDepartamento() != null ? ticket.getDepartamento().getCorreo() : null);
            stmt.setString(6, ticket.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar ticket: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminar(String id) throws SQLException {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar ticket: " + e.getMessage());
        }
    }

    @Override
    public Ticket buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirTicket(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new SQLException("Error al buscar ticket: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Ticket> listarTodos() throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets ORDER BY id";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tickets.add(construirTicket(rs));
            }

            return tickets;

        } catch (SQLException e) {
            throw new SQLException("Error al listar tickets: " + e.getMessage());
        }
    }

    @Override
    public boolean existe(String id) throws SQLException {
        return buscarPorId(id) != null;
    }

    /**
     * Actualiza solo el estado de un ticket.
     *
     * @param id ID del ticket
     * @param estado Nuevo estado
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la consulta
     */
    public boolean actualizarEstado(String id, EstadoTicket estado) throws SQLException {
        String sql = "UPDATE tickets SET estado = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.name());
            stmt.setString(2, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar estado del ticket: " + e.getMessage());
        }
    }

    /**
     * Lista todos los tickets de un departamento específico.
     *
     * @param correoDepartamento Correo del departamento
     * @return Lista de tickets del departamento
     * @throws SQLException si hay error en la consulta
     */
    public ArrayList<Ticket> listarPorDepartamento(String correoDepartamento) throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE correo_departamento = ? ORDER BY id";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correoDepartamento);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(construirTicket(rs));
                }
            }

            return tickets;

        } catch (SQLException e) {
            throw new SQLException("Error al listar tickets por departamento: " + e.getMessage());
        }
    }

    /**
     * Lista todos los tickets de un usuario específico.
     *
     * @param correoUsuario Correo del usuario
     * @return Lista de tickets del usuario
     * @throws SQLException si hay error en la consulta
     */
    public ArrayList<Ticket> listarPorUsuario(String correoUsuario) throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE correo_usuario = ? ORDER BY id";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correoUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(construirTicket(rs));
                }
            }

            return tickets;

        } catch (SQLException e) {
            throw new SQLException("Error al listar tickets por usuario: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para construir un objeto Ticket desde un ResultSet.
     * Carga las entidades relacionadas (Usuario y Departamento).
     *
     * @param rs ResultSet con los datos del ticket
     * @return Objeto Ticket construido
     * @throws SQLException si hay error al leer los datos
     */
    private Ticket construirTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getString("id"));
        ticket.setAsunto(rs.getString("titulo"));
        ticket.setDescripcion(rs.getString("descripcion"));
        ticket.setEstado(EstadoTicket.valueOf(rs.getString("estado")));

        // Cargar el usuario asociado
        String correoUsuario = rs.getString("correo_usuario");
        Usuario usuario = usuarioDao.buscarPorId(correoUsuario);
        ticket.setUsuario(usuario);

        // Cargar el departamento asociado (puede ser null)
        String correoDepartamento = rs.getString("correo_departamento");
        if (correoDepartamento != null) {
            Departamento departamento = departamentoDao.buscarPorId(correoDepartamento);
            ticket.setDepartamento(departamento);
        }

        return ticket;
    }
}