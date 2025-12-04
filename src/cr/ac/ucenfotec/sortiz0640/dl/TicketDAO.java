package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.DataAccessObject;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Ticket.
 * Gestiona todas las operaciones CRUD de tickets en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class TicketDAO extends DataAccessObject<Ticket> {

    private AccessDB DATA_ACCESS;
    private UsuarioDAO USUARIO_DAO;
    private DepartamentoDAO DEPARTAMENTO_DAO;

    public TicketDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
        this.USUARIO_DAO = new UsuarioDAO(driver, url, username, password);
        this.DEPARTAMENTO_DAO = new DepartamentoDAO(driver, url, username, password);
    }

    @Override
    public boolean agregar(Ticket ticket) throws SQLException {

        if (existe(ticket.getId())) {
            return false;
        }

        String query = "INSERT INTO tiquetes (id, asunto, descripcion, estado, correoUsuario, correoDepartamento, categoriaTecnica, categoriaEmocional) " +
                "VALUES ('" + ticket.getId() + "', " +
                "'" + ticket.getAsunto() + "', " +
                "'" + ticket.getDescripcion() + "', " +
                "'" + ticket.getEstado() + "', " +
                "'" + ticket.getUsuario().getCorreo() + "', " +
                "'" + ticket.getDepartamento().getCorreo() + "', " +
                "'" + ticket.getCategoriaTecnica() + "', " +
                "'" + ticket.getCategoriaEmocional() + "')";

        DATA_ACCESS.ejecutar(query);
        return true;
    }

    @Override
    public boolean eliminar(String id) throws SQLException {

        if (!existe(id)) {
            return false;
        }

        String query = "DELETE FROM tiquetes WHERE id = '" + id + "'";
        DATA_ACCESS.ejecutar(query);

        return true;

    }

    @Override
    public Ticket buscar(String id) throws SQLException {

        String query = "SELECT * FROM tiquetes WHERE id = '" + id + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        if (res.next()) {
            Ticket ticket = new Ticket();
            ticket.setId(res.getString("id"));
            ticket.setAsunto(res.getString("asunto"));
            ticket.setDescripcion(res.getString("descripcion"));
            ticket.setEstado(EstadoTicket.valueOf(res.getString("estado")));
            ticket.setUsuario(USUARIO_DAO.buscar(res.getString("correoUsuario")));
            ticket.setDepartamento(DEPARTAMENTO_DAO.buscar(res.getString("correoDepartamento")));
            ticket.setCategorias(res.getString("categoriaTecnica"), res.getString("categoriaEmocional"));

            return ticket;
        }

        return null;

    }

    @Override
    public ArrayList<Ticket> obtenerTodos() throws SQLException {

        ArrayList<Ticket> tickets = new ArrayList<>();
        String query = "select \n" +
                "id,\n" +
                "asunto,\n" +
                "t.descripcion,\n" +
                "estado,\n" +
                "correoUsuario,\n" +
                "correoDepartamento,\n" +
                "categoriaTecnica,\n" +
                "categoriaEmocional,\n" +
                "u.correo AS usuarioCorreo,\n" +
                "u.nombre AS usuarioNombre,\n" +
                "u.apellidos AS usuarioApellidos,\n" +
                "u.password as usuarioPassword,\n" +
                "u.rol as usuarioRol,\n" +
                "d.correo as departamentoCorreo,\n" +
                "d.nombre as departamentoNombre,\n" +
                "d.descripcion as departamentoDescripcion\n" +
                "from tiquetes t INNER JOIN usuarios u on t.correoUsuario = u.correo INNER JOIN departamentos d on t.correoDepartamento = d.correo";

        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {
            // Crear el ticket
            Ticket ticket = new Ticket();
            ticket.setId(res.getString("id"));
            ticket.setAsunto(res.getString("asunto"));
            ticket.setDescripcion(res.getString("descripcion"));
            ticket.setEstado(EstadoTicket.valueOf(res.getString("estado")));
            ticket.setCategorias(res.getString("categoriaTecnica"), res.getString("categoriaEmocional"));

            // Crear el usuario desde el ResultSet
            Usuario usuario = new Usuario();
            usuario.setCorreo(res.getString("usuarioCorreo"));
            usuario.setNombre(res.getString("usuarioNombre"));
            usuario.setApellidos(res.getString("usuarioApellidos"));
            usuario.setPassword(res.getString("usuarioPassword"));
            usuario.setRol(ListaRoles.valueOf(res.getString("usuarioRol")));
            ticket.setUsuario(usuario);

            // Crear el departamento desde el ResultSet
            Departamento departamento = new Departamento();
            departamento.setCorreo(res.getString("departamentoCorreo"));
            departamento.setNombre(res.getString("departamentoNombre"));
            departamento.setDescripcion(res.getString("departamentoDescripcion"));
            ticket.setDepartamento(departamento);

            tickets.add(ticket);
        }


        return tickets;
    }

    @Override
    public boolean existe(String id) throws SQLException {
        String query = "SELECT 1 FROM tiquetes WHERE id = '" + id + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }

    public boolean actualizarEstado(String ticketId, EstadoTicket nuevoEstado) throws SQLException {
        if (!existe(ticketId)) {
            return false;
        }

        String query = "UPDATE tiquetes SET estado = '" + nuevoEstado.name() + "' " +
                "WHERE id = '" + ticketId + "'";
        DATA_ACCESS.ejecutar(query);
        return true;
    }
}