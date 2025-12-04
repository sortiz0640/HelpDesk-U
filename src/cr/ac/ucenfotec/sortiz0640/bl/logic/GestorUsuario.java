package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.dl.DataUsuario;
import cr.ac.ucenfotec.sortiz0640.dl.UsuarioDAO;

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

    public String agregar(String nombre, String apellidos, String correo, String password, int rolEntrada) throws SQLException {
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

    public String eliminarPorCorreo(String correo) throws SQLException {
        boolean resultado = db.eliminar(correo);

        if (!resultado) {
            return "[ERR] El usuario especificado no existe";
        }

        return "[INFO] Usuario eliminado correctamente";
    }

    public Usuario buscarPorCorreo(String correo) throws SQLException {
        return db.buscar(correo);
    }


    public ArrayList<Usuario> obtenerUsuarios() throws SQLException {
        return db.obtenerTodos();
    }

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