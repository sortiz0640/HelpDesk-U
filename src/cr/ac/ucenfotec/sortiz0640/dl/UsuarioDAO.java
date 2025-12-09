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

    public UsuarioDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }

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

    @Override
    public boolean eliminar(String correo) throws SQLException {

        if (!existe(correo)) {
            return false;
        }

        String query = "DELETE FROM usuarios WHERE correo = '" + correo + "'";
        DATA_ACCESS.ejecutar(query);

        return true;
    }

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

    @Override
    public boolean existe(String correo) throws SQLException {
        String query = "SELECT 1 FROM usuarios WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }
}