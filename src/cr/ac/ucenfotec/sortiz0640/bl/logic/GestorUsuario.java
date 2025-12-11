package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.dl.UsuarioDAO;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de usuarios.
 * Trabaja exclusivamente con la entidad Usuario.
 * No tiene dependencias de otros gestores.
 *
 * @author Sebastian Ortiz
 * @version 2.0
 * @since 2025
 */
public class GestorUsuario {

    private UsuarioDAO db;
    ConfigPropertiesReader config;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     *
     * @throws SQLException Si ocurre un error de conexión.
     * @throws ClassNotFoundException Si no se encuentra el driver.
     */
    public GestorUsuario() throws SQLException, ClassNotFoundException {
        config = new ConfigPropertiesReader();
        db = new UsuarioDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    /**
     * Agrega un nuevo usuario validando el rol y existencia previa.
     *
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param correo Correo electrónico.
     * @param password Contraseña.
     * @param rolEntrada Entero que representa el rol (1: ADMIN, 2: ESTUDIANTE, 3: FUNCIONARIO).
     * @return Mensaje informativo sobre el resultado de la operación.
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws NoSuchAlgorithmException Si falla la encriptación de la contraseña.
     */
    public String agregar(String nombre, String apellidos, String correo, String password, int rolEntrada) throws SQLException, NoSuchAlgorithmException {
        ListaRoles rol = switch (rolEntrada) {
            case 1 -> ListaRoles.ADMIN;
            case 2 -> ListaRoles.ESTUDIANTE;
            case 3 -> ListaRoles.FUNCIONARIO;
            default -> null;
        };

        if (rol == null) {
            return "[ERR] Rol inválido";
        }

        Usuario tmpUsuario = new Usuario(nombre, apellidos, correo, password, rol);
        boolean resultado = db.agregar(tmpUsuario);

        if (!resultado) {
            return "[ERR] El correo del usuario ya se encuentra registrado!";
        }

        return "[INFO] Usuario agregado correctamente";
    }

    /**
     * Elimina un usuario existente por su correo electrónico.
     *
     * @param correo Correo del usuario a eliminar.
     * @return Mensaje informativo sobre el resultado.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String eliminarPorCorreo(String correo) throws SQLException {
        boolean resultado = db.eliminar(correo);

        if (!resultado) {
            return "[ERR] El usuario especificado no existe";
        }

        return "[INFO] Usuario eliminado correctamente";
    }

    /**
     * Busca un objeto Usuario completo por su correo.
     *
     * @param correo Correo del usuario a buscar.
     * @return Objeto Usuario si existe, o null si no se encuentra.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public Usuario buscarPorCorreo(String correo) throws SQLException {
        return db.buscar(correo);
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @return ArrayList con todos los objetos Usuario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<Usuario> obtenerUsuarios() throws SQLException {
        return db.obtenerTodos();
    }

    /**
     * Obtiene los detalles básicos de un usuario en formato de arreglo de Strings.
     *
     * @param correoUsuario Correo del usuario.
     * @return Arreglo con [Nombre, Apellidos, Correo, Rol], o null si no existe.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String[] obtenerDetallesUsuario(String correoUsuario) throws SQLException {
        Usuario u = buscarPorCorreo(correoUsuario);

        if (u == null) {
            return null;
        }

        return new String[]{
                u.getNombre(),
                u.getApellidos(),
                u.getCorreo(),
                u.getRol().toString(),
        };
    }

    /**
     * Obtiene todos los usuarios formateados para mostrar en tablas o interfaces.
     *
     * @return Lista de arreglos de String con los datos de cada usuario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */

    public ArrayList<String[]> obtenerTodosUsuariosFormato() throws SQLException {
        ArrayList<Usuario> usuarios = obtenerUsuarios();
        ArrayList<String[]> resultado = new ArrayList<>();

        if (usuarios == null || usuarios.isEmpty()) {
            return resultado;
        }

        for (Usuario u : usuarios) {
            String[] datos = {
                    u.getNombre(),
                    u.getApellidos(),
                    u.getCorreo(),
                    u.getRol().toString(),
            };
            resultado.add(datos);
        }

        return resultado;
    }
}