package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.DataAccessObject;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.bl.util.PasswordEncrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Usuario.
 * Gestiona todas las operaciones CRUD de usuarios en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class UsuarioDAO extends DataAccessObject<Usuario> {

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
    public UsuarioDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }

    /**
     * Agrega un nuevo usuario a la base de datos, encriptando su contraseña previamente.
     *
     * @param usuario El objeto Usuario a agregar.
     * @return true si el usuario se agregó, false si el correo ya existía.
     * @throws SQLException Si ocurre un error al ejecutar la inserción.
     * @throws NoSuchAlgorithmException Si el algoritmo de encriptación no está disponible.
     */
    @Override
    public boolean agregar(Usuario usuario) throws SQLException, NoSuchAlgorithmException {

        String query = "INSERT INTO usuarios (nombre, apellidos, correo, password, rol) " +
                "VALUES ('" + usuario.getNombre() + "', " +
                "'" + usuario.getApellidos() + "', " +
                "'" + usuario.getCorreo() + "', " +
                "'" + PasswordEncrypt.encrypt(usuario.getPassword()) + "', " +
                "'" + usuario.getRol().name() + "')";

        if (existe(usuario.getCorreo())) {
            return false;
        }

        DATA_ACCESS.ejecutar(query);
        return true;

    }

    /**
     * Elimina un usuario de la base de datos por su correo.
     *
     * @param correo Correo del usuario a eliminar.
     * @return true si el usuario fue eliminado, false si no existía.
     * @throws SQLException Si ocurre un error al ejecutar la eliminación.
     */
    @Override
    public boolean eliminar(String correo) throws SQLException {

        if (!existe(correo)) {
            return false;
        }

        String query = "DELETE FROM usuarios WHERE correo = '" + correo + "'";
        DATA_ACCESS.ejecutar(query);

        return true;
    }

    /**
     * Busca y recupera un objeto Usuario por su correo.
     *
     * @param correo Correo del usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public Usuario buscar(String correo) throws SQLException {

        Usuario tmp = new Usuario();

        String query = "SELECT * FROM usuarios WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        if (res.next()) {
            tmp.setNombre(res.getString("nombre"));
            tmp.setApellidos(res.getString("apellidos"));
            tmp.setCorreo(res.getString("correo"));
            tmp.setPassword(res.getString("password"));
            tmp.setRol(ListaRoles.valueOf(res.getString("rol")));

            return tmp;
        }

        return null;
    }

    /**
     * Obtiene una lista con todos los objetos Usuario registrados.
     *
     * @return ArrayList de objetos Usuario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ArrayList<Usuario> obtenerTodos() throws SQLException {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        String query = "SELECT * FROM usuarios";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {
            Usuario tmp = new Usuario();
            tmp.setNombre(res.getString("nombre"));
            tmp.setApellidos(res.getString("apellidos"));
            tmp.setCorreo(res.getString("correo"));
            tmp.setPassword(res.getString("password"));
            tmp.setRol(ListaRoles.valueOf(res.getString("rol")));

            usuarios.add(tmp);
        }

        return usuarios;
    }

    /**
     * Verifica la existencia de un usuario por su correo.
     *
     * @param correo Correo del usuario a verificar.
     * @return true si el usuario existe, false si no.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public boolean existe(String correo) throws SQLException {
        String query = "SELECT 1 FROM usuarios WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }
}