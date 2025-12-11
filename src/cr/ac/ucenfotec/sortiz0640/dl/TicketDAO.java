package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;
import cr.ac.ucenfotec.sortiz0640.bl.entities.CategoriaTicket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.DataAccessObject;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Ticket.
 * Gestiona todas las operaciones CRUD de tickets en la base de datos,
 * incluyendo las categorías y palabras detonantes asociadas.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class TicketDAO extends DataAccessObject<Ticket> {

    private AccessDB DATA_ACCESS;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param driver Driver de la base de datos.
     * @param url URL de conexión.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @throws SQLException Si ocurre un error de conexión SQL.
     * @throws ClassNotFoundException Si no se encuentra el driver.
     */
    public TicketDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }

    /**
     * Agrega un nuevo ticket a la base de datos, incluyendo su información básica
     * y las palabras detonantes de las categorías técnica y emocional.
     *
     * @param ticket El objeto Ticket a agregar.
     * @return true si el ticket se agregó, false si ya existía el ID.
     * @throws SQLException Si ocurre un error al ejecutar las inserciones.
     */
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
                "'" + ticket.getTecnica().getNombre() + "', " +
                "'" + ticket.getEmocion().getNombre() + "')";

        DATA_ACCESS.ejecutar(query);

        // Guardar las palabras detonantes técnicas
        if (ticket.getTecnica() != null && ticket.getTecnica().getPalabrasDetonantes() != null) {
            for (String palabra : ticket.getTecnica().getPalabrasDetonantes()) {
                String queryPalabra = "INSERT INTO ticket_palabras_detonantes (ticketId, palabra, tipoCategoria) " +
                        "VALUES ('" + ticket.getId() + "', '" + palabra + "', 'TECNICA')";
                DATA_ACCESS.ejecutar(queryPalabra);
            }
        }

        // Guardar las palabras detonantes emocionales
        if (ticket.getEmocion() != null && ticket.getEmocion().getPalabrasDetonantes() != null) {
            for (String palabra : ticket.getEmocion().getPalabrasDetonantes()) {
                String queryPalabra = "INSERT INTO ticket_palabras_detonantes (ticketId, palabra, tipoCategoria) " +
                        "VALUES ('" + ticket.getId() + "', '" + palabra + "', 'EMOCIONAL')";
                DATA_ACCESS.ejecutar(queryPalabra);
            }
        }

        return true;
    }

    /**
     * Elimina un ticket de la base de datos por su ID.
     *
     * @param id ID del ticket a eliminar.
     * @return true si el ticket fue eliminado, false si no existía.
     * @throws SQLException Si ocurre un error al ejecutar la eliminación.
     */
    @Override
    public boolean eliminar(String id) throws SQLException {

        if (!existe(id)) {
            return false;
        }

        // NOTA: Se asume que la tabla ticket_palabras_detonantes tiene FK ON DELETE CASCADE
        // o que se maneja la eliminación de las palabras detonantes por separado (aunque no está aquí).
        String query = "DELETE FROM tiquetes WHERE id = '" + id + "'";
        DATA_ACCESS.ejecutar(query);

        return true;

    }

    /**
     * Busca y recupera un objeto Ticket por su ID, incluyendo la información de
     * Usuario, Departamento y las palabras detonantes asociadas.
     *
     * @param id ID del ticket a buscar.
     * @return El objeto Ticket completo si se encuentra, o null en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public Ticket buscar(String id) throws SQLException {

        // Query que trae toda la información del ticket, usuario, departamento y palabras detonantes
        String query = "SELECT " +
                "t.id, " +
                "t.asunto, " +
                "t.descripcion, " +
                "t.estado, " +
                "t.correoUsuario, " +
                "t.correoDepartamento, " +
                "t.categoriaTecnica, " +
                "t.categoriaEmocional, " +
                "u.correo AS usuarioCorreo, " +
                "u.nombre AS usuarioNombre, " +
                "u.apellidos AS usuarioApellidos, " +
                "u.password AS usuarioPassword, " +
                "u.rol AS usuarioRol, " +
                "d.correo AS departamentoCorreo, " +
                "d.nombre AS departamentoNombre, " +
                "d.descripcion AS departamentoDescripcion, " +
                "tpd.palabra AS palabraDetonante, " +
                "tpd.tipoCategoria AS tipoPalabra " +
                "FROM tiquetes t " +
                "INNER JOIN usuarios u ON t.correoUsuario = u.correo " +
                "INNER JOIN departamentos d ON t.correoDepartamento = d.correo " +
                "LEFT JOIN ticket_palabras_detonantes tpd ON t.id = tpd.ticketId " +
                "WHERE t.id = '" + id + "' " +
                "ORDER BY tpd.tipoCategoria";

        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        Ticket ticket = null;
        ArrayList<String> palabrasTecnicas = new ArrayList<>();
        ArrayList<String> palabrasEmocionales = new ArrayList<>();
        String categoriaTecnicaNombre = null;
        String categoriaEmocionalNombre = null;

        while (res.next()) {

            if (ticket == null) {
                ticket = new Ticket();
                ticket.setId(res.getString("id"));
                ticket.setAsunto(res.getString("asunto"));
                ticket.setDescripcion(res.getString("descripcion"));
                ticket.setEstado(EstadoTicket.valueOf(res.getString("estado")));

                categoriaTecnicaNombre = res.getString("categoriaTecnica");
                categoriaEmocionalNombre = res.getString("categoriaEmocional");

                Usuario usuario = new Usuario();
                usuario.setCorreo(res.getString("usuarioCorreo"));
                usuario.setNombre(res.getString("usuarioNombre"));
                usuario.setApellidos(res.getString("usuarioApellidos"));
                usuario.setPassword(res.getString("usuarioPassword"));
                usuario.setRol(ListaRoles.valueOf(res.getString("usuarioRol")));
                ticket.setUsuario(usuario);

                Departamento departamento = new Departamento();
                departamento.setCorreo(res.getString("departamentoCorreo"));
                departamento.setNombre(res.getString("departamentoNombre"));
                departamento.setDescripcion(res.getString("departamentoDescripcion"));
                ticket.setDepartamento(departamento);
            }

            String palabraDetonante = res.getString("palabraDetonante");
            String tipoPalabra = res.getString("tipoPalabra");

            if (palabraDetonante != null && tipoPalabra != null) {
                if (tipoPalabra.equals("TECNICA")) {
                    palabrasTecnicas.add(palabraDetonante);
                } else if (tipoPalabra.equals("EMOCIONAL")) {
                    palabrasEmocionales.add(palabraDetonante);
                }
            }
        }

        if (ticket != null) {
            CategoriaTicket categoriaTecnica = new CategoriaTicket(categoriaTecnicaNombre, palabrasTecnicas);
            categoriaTecnica.setTipo(TipoCategoria.TECNICA);
            ticket.setTecnica(categoriaTecnica);

            CategoriaTicket categoriaEmocional = new CategoriaTicket(categoriaEmocionalNombre, palabrasEmocionales);
            categoriaEmocional.setTipo(TipoCategoria.EMOCIONAL);
            ticket.setEmocion(categoriaEmocional);
        }

        return ticket;
    }

    /**
     * Obtiene una lista con todos los tickets registrados, realizando un join
     * con la información de Usuario, Departamento y las palabras detonantes.
     *
     * @return ArrayList de objetos Ticket completos.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ArrayList<Ticket> obtenerTodos() throws SQLException {

        ArrayList<Ticket> tickets = new ArrayList<>();

        String query = "SELECT " +
                "t.id, " +
                "t.asunto, " +
                "t.descripcion, " +
                "t.estado, " +
                "t.correoUsuario, " +
                "t.correoDepartamento, " +
                "t.categoriaTecnica, " +
                "t.categoriaEmocional, " +
                "u.correo AS usuarioCorreo, " +
                "u.nombre AS usuarioNombre, " +
                "u.apellidos AS usuarioApellidos, " +
                "u.password AS usuarioPassword, " +
                "u.rol AS usuarioRol, " +
                "d.correo AS departamentoCorreo, " +
                "d.nombre AS departamentoNombre, " +
                "d.descripcion AS departamentoDescripcion, " +
                "tpd.palabra AS palabraDetonante, " +
                "tpd.tipoCategoria AS tipoPalabra " +
                "FROM tiquetes t " +
                "INNER JOIN usuarios u ON t.correoUsuario = u.correo " +
                "INNER JOIN departamentos d ON t.correoDepartamento = d.correo " +
                "LEFT JOIN ticket_palabras_detonantes tpd ON t.id = tpd.ticketId " +
                "ORDER BY t.id, tpd.tipoCategoria";

        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        String ticketIdActual = null;
        Ticket ticketActual = null;
        ArrayList<String> palabrasTecnicas = new ArrayList<>();
        ArrayList<String> palabrasEmocionales = new ArrayList<>();
        String categoriaTecnicaNombre = null;
        String categoriaEmocionalNombre = null;

        while (res.next()) {
            String ticketId = res.getString("id");

            if (ticketIdActual != null && !ticketId.equals(ticketIdActual)) {

                CategoriaTicket categoriaTecnica = new CategoriaTicket(categoriaTecnicaNombre, palabrasTecnicas);
                categoriaTecnica.setTipo(TipoCategoria.TECNICA);
                ticketActual.setTecnica(categoriaTecnica);

                CategoriaTicket categoriaEmocional = new CategoriaTicket(categoriaEmocionalNombre, palabrasEmocionales);
                categoriaEmocional.setTipo(TipoCategoria.EMOCIONAL);
                ticketActual.setEmocion(categoriaEmocional);
                tickets.add(ticketActual);

                palabrasTecnicas = new ArrayList<>();
                palabrasEmocionales = new ArrayList<>();
            }

            if (!ticketId.equals(ticketIdActual)) {
                ticketActual = new Ticket();
                ticketActual.setId(ticketId);
                ticketActual.setAsunto(res.getString("asunto"));
                ticketActual.setDescripcion(res.getString("descripcion"));
                ticketActual.setEstado(EstadoTicket.valueOf(res.getString("estado")));

                categoriaTecnicaNombre = res.getString("categoriaTecnica");
                categoriaEmocionalNombre = res.getString("categoriaEmocional");

                Usuario usuario = new Usuario();
                usuario.setCorreo(res.getString("usuarioCorreo"));
                usuario.setNombre(res.getString("usuarioNombre"));
                usuario.setApellidos(res.getString("usuarioApellidos"));
                usuario.setPassword(res.getString("usuarioPassword"));
                usuario.setRol(ListaRoles.valueOf(res.getString("usuarioRol")));
                ticketActual.setUsuario(usuario);

                Departamento departamento = new Departamento();
                departamento.setCorreo(res.getString("departamentoCorreo"));
                departamento.setNombre(res.getString("departamentoNombre"));
                departamento.setDescripcion(res.getString("departamentoDescripcion"));
                ticketActual.setDepartamento(departamento);

                ticketIdActual = ticketId;
            }

            String palabraDetonante = res.getString("palabraDetonante");
            String tipoPalabra = res.getString("tipoPalabra");

            if (palabraDetonante != null && tipoPalabra != null) {
                if (tipoPalabra.equals("TECNICA")) {
                    palabrasTecnicas.add(palabraDetonante);
                } else if (tipoPalabra.equals("EMOCIONAL")) {
                    palabrasEmocionales.add(palabraDetonante);
                }
            }
        }

        if (ticketActual != null) {
            CategoriaTicket categoriaTecnica = new CategoriaTicket(categoriaTecnicaNombre, palabrasTecnicas);
            categoriaTecnica.setTipo(TipoCategoria.TECNICA);
            ticketActual.setTecnica(categoriaTecnica);

            CategoriaTicket categoriaEmocional = new CategoriaTicket(categoriaEmocionalNombre, palabrasEmocionales);
            categoriaEmocional.setTipo(TipoCategoria.EMOCIONAL);
            ticketActual.setEmocion(categoriaEmocional);

            tickets.add(ticketActual);
        }

        return tickets;
    }

    /**
     * Verifica la existencia de un ticket por su ID.
     *
     * @param id ID del ticket a verificar.
     * @return true si el ticket existe, false si no.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public boolean existe(String id) throws SQLException {
        String query = "SELECT 1 FROM tiquetes WHERE id = '" + id + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }

    /**
     * Actualiza el campo de estado de un ticket específico.
     *
     * @param ticketId ID del ticket a actualizar.
     * @param nuevoEstado El nuevo estado (e.g., EN_PROCESO, RESUELTO).
     * @return true si el estado fue actualizado, false si el ticket no existe.
     * @throws SQLException Si ocurre un error al ejecutar la actualización.
     */
    public boolean actualizarEstado(String ticketId, EstadoTicket nuevoEstado) throws SQLException {
        if (!existe(ticketId)) {
            return false;
        }

        String query = "UPDATE tiquetes SET estado = '" + nuevoEstado.name() + "' " + "WHERE id = '" + ticketId + "'";
        DATA_ACCESS.ejecutar(query);
        return true;
    }

    /**
     * Obtiene una lista de las palabras detonantes de un tipo de categoría específico
     * asociadas a un ticket.
     *
     * @param ticketId ID del ticket.
     * @param tipoCategoria Tipo de categoría a buscar (TECNICA o EMOCIONAL).
     * @return Lista de Strings con las palabras detonantes.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public ArrayList<String> obtenerPalabrasDetonantes(String ticketId, TipoCategoria tipoCategoria) throws SQLException {
        ArrayList<String> palabras = new ArrayList<>();
        String query = "SELECT palabra FROM ticket_palabras_detonantes " + "WHERE ticketId = '" + ticketId + "' " +  "AND tipoCategoria = '" + tipoCategoria.toString() + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {
            palabras.add(res.getString("palabra"));
        }

        return palabras;
    }
}